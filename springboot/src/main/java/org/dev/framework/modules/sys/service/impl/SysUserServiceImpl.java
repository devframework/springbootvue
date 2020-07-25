package org.dev.framework.modules.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.dev.framework.modules.sys.entity.SysRole;
import org.dev.framework.modules.sys.entity.SysRoleUser;
import org.dev.framework.modules.sys.entity.SysUser;
import org.dev.framework.modules.sys.service.SysRoleService;
import org.dev.framework.modules.sys.entity.SysRole;
import org.dev.framework.modules.sys.entity.SysRoleUser;
import org.dev.framework.modules.sys.entity.SysUser;
import org.dev.framework.modules.sys.mapper.SysUserMapper;
import org.dev.framework.modules.sys.service.SysRoleService;
import org.dev.framework.modules.sys.service.SysRoleUserService;
import org.dev.framework.modules.sys.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 用户基本信息 服务实现类
 * </p>
 *
 * @author hlt
 * @since 2020-06-24
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    @Autowired
    SysRoleService sysRoleService;

    @Autowired
    SysRoleUserService sysRoleUserService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUser sysUser = new SysUser();
        sysUser.setLoginName(username);
        QueryWrapper entityWrapper = new QueryWrapper(sysUser);
        List<SysUser> sysUsers = baseMapper.selectList(entityWrapper);
        if (sysUsers != null && sysUsers.size() > 0) {
            sysUser = sysUsers.get(0);
            //获取用户权限
            QueryWrapper roleUserWrapper = new QueryWrapper();
            roleUserWrapper.eq("user_id", sysUser.getId());
            List<SysRoleUser> sysRoleUsers = sysRoleUserService.list(roleUserWrapper);
            //
            List<Long> idarray = new ArrayList<>();
            idarray = sysRoleUsers.stream().map(SysRoleUser::getRoleId).collect(Collectors.toList());
            QueryWrapper roleWrapper = new QueryWrapper();
            if (idarray != null && idarray.size() > 0) {
                roleWrapper.in("id", idarray);
                List<SysRole> roles = sysRoleService.list(roleWrapper);
                sysUser.setRoles(roles);
            }
            return sysUser;
        }
        return null;
    }
}
