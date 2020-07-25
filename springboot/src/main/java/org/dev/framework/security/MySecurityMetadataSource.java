package org.dev.framework.security;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.dev.framework.modules.sys.entity.SysFunc;
import org.dev.framework.modules.sys.entity.SysRole;
import org.dev.framework.modules.sys.entity.SysRoleFunc;
import org.dev.framework.modules.sys.service.SysRoleService;
import org.dev.framework.modules.sys.entity.SysFunc;
import org.dev.framework.modules.sys.entity.SysRole;
import org.dev.framework.modules.sys.entity.SysRoleFunc;
import org.dev.framework.modules.sys.service.SysFuncService;
import org.dev.framework.modules.sys.service.SysRoleFuncService;
import org.dev.framework.modules.sys.service.SysRoleService;
import org.dev.framework.security.util.UrlMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/***
 * 权限资源 SecurityMetadataSource
 */
@Component
public class MySecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

    AntPathMatcher antPathMatcher = new AntPathMatcher();
    private static final Logger logger = LoggerFactory.getLogger(MySecurityMetadataSource.class);
    //存放资源配置对象
    private static Map<String, Collection<ConfigAttribute>> resourceMap = null;

    @Autowired
    SysRoleService sysRoleService;
    @Autowired
    SysFuncService sysFuncService;
    @Autowired
    SysRoleFuncService sysRoleFuncService;
    @Autowired
    UrlMatcher urlMatcher;

    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        String requestUrl = ((FilterInvocation) object).getRequestUrl();
        if (resourceMap == null) {
            loadResourceDefine();
        } else {
            String url = ((FilterInvocation) object).getRequestUrl();
            logger.info("请求 url ：" + url);
            int firstQuestionMarkIndex = url.indexOf("?");
            if (firstQuestionMarkIndex != -1) {
                url = url.substring(0, firstQuestionMarkIndex);
            }
            //循环已有的角色配置对象 进行url匹配
            Iterator<String> ite = resourceMap.keySet().iterator();
            while (ite.hasNext()) {
                String resURL = ite.next().trim();
                if (urlMatcher.pathMatchesUrl(resURL, url)) {     // 路径支持Ant风格的通配符 /spitters/**
                    return resourceMap.get(resURL);
                }
           /* if (url.equals(resURL)) {   // 路径不支持Ant风格的通配符
                //返回当前 url  所需要的权限
                return resourceMap.get(resURL);
            }*/
            }
            return SecurityConfig.createList("ROLE_LOGIN");
//            int firstQuestionMarkIndex = requestUrl.indexOf("?");
//            if (firstQuestionMarkIndex != -1) {
//                requestUrl = requestUrl.substring(0, firstQuestionMarkIndex);
//            }
//            Collection<ConfigAttribute> attributes = resourceMap.get(requestUrl);
//            if(attributes!=null) {
//                List<String> roles = attributes.stream().map(x -> x.getAttribute()).collect(Collectors.toList());
//                String[] str = new String[roles.size()];
//                for (int i = 0; i < roles.size(); i++) {
//                    str[i] = roles.get(i);
//                }
//                return SecurityConfig.createList(str);
//            }
        }
        return SecurityConfig.createList("ROLE_LOGIN");
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return FilterInvocation.class.isAssignableFrom(aClass);
    }

    /**
     * 初始化资源 ,提取系统中的所有权限，加载所有url和权限（或角色）的对应关系，  web容器启动就会执行
     * 如果启动@PostConstruct 注解   则web容器启动就会执行
     */
    @PostConstruct
    public void loadResourceDefine() {
        //应当是资源为key， 权限为value。 资源通常为url， 权限就是那些以ROLE_为前缀的角色。 一个资源可以由多个权限来访问。
        resourceMap = new ConcurrentHashMap<>();
        //获取所有角色
        List<SysRole> roleList = this.sysRoleService.list(new QueryWrapper<>());
        //容器启动时,获取全部系统菜单资源信息
        List<SysFunc> funcList = this.sysFuncService.list(new QueryWrapper<>());
        //获取方法和角色的关系表
        List<SysRoleFunc> sysRoleFuncs = this.sysRoleFuncService.list(new QueryWrapper<>());

        if (!CollectionUtils.isEmpty(funcList)) {
            for (SysFunc func : funcList) {
                //获取方法已经分配的角色
                List<SysRoleFunc> sysRoleFuncs1 = sysRoleFuncs.stream().filter(x -> x.getFuncId().equals(func.getId())).collect(Collectors.toList());
                List<Long> funcIds = sysRoleFuncs1.stream().map(SysRoleFunc::getRoleId).collect(Collectors.toList());
                //获取角色
                List<SysRole> sysRoles = roleList.stream().filter(x -> funcIds.contains(x.getId())).collect(Collectors.toList());
                //配置的方法url都给默认增加一个administrator
                SysRole sysRole = new SysRole();
                sysRole.setRoleCode("administrator");
                sysRole.setRoleName("系统管理员");
                sysRole.setMemo("给每一个URL都增加一个超级管理员角色,避免配置方法url但未配置权限导致任何人都可以访问");
                if (sysRoles == null) {
                    sysRoles = new ArrayList<>();
                }
                sysRoles.add(sysRole);
                //请求url
                String url = func.getFuncUrl();
                // 判断资源文件和权限的对应关系，如果已经存在相关的资源url，则要通过该url为key提取出权限集合，将权限增加到权限集合中。
                if (resourceMap.containsKey(url)) {
                    Collection<ConfigAttribute> value = resourceMap.get(url);
                    for (SysRole role : sysRoles) {
                        //授权标识
                        ConfigAttribute configAttributes = new SecurityConfig(role.getRoleCode());
                        value.add(configAttributes);
                    }
                    resourceMap.put(url, value);
                } else {
                    Collection<ConfigAttribute> atts = new ArrayList<ConfigAttribute>();
                    for (SysRole role : sysRoles) {
                        //授权标识
                        ConfigAttribute configAttributes = new SecurityConfig(role.getRoleCode());
                        atts.add(configAttributes);
                    }
                    resourceMap.put(url, atts);
                }

            }
        }

    }

    /**
     * 清空资源
     */
    public void clearResourceDefine() {
        resourceMap = null;
    }
}
