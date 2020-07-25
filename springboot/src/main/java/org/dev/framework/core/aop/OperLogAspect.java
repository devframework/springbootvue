package org.dev.framework.core.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.dev.framework.modules.sys.entity.SysLog;
import org.dev.framework.modules.sys.entity.SysUser;
import org.dev.framework.modules.sys.service.SysLogService;
import org.dev.framework.security.jwt.JwtUtil;
import org.dev.framework.modules.sys.entity.SysLog;
import org.dev.framework.modules.sys.entity.SysUser;
import org.dev.framework.modules.sys.service.SysLogService;
import org.dev.framework.security.jwt.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.Enumeration;
import java.util.UUID;

@Aspect
@Component
@Slf4j
public class OperLogAspect {
    /**
     * 设置操作日志切入点 记录操作日志
     */
    @Autowired
    private SysLogService sysLogService;

    /**
     * 在注解的位置切入代码
     * 表示匹配org.dev.framework.modules*.controller 包下的所有方法
     */

    @Pointcut("execution(* org.dev.framework.modules.*.controller.*.*(..))")
    public void privilege() {

    }

    @Around("privilege()")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        // 获取类名
        String className = pjp.getTarget().getClass().getName();
        // 获取执行的方法名称
        String methodName = pjp.getSignature().getName();
        // 获取参数名称
        String[] parameterNamesArgs = ((MethodSignature) pjp.getSignature()).getParameterNames();
        // 定义返回参数
        Object result = null;
        // 获取方法参数
        Object[] args = pjp.getArgs();
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        // 请求的URL
        String requestURL = request.getRequestURL().toString();
        String ip = getIpAddr(request);

        StringBuffer paramsBuf = new StringBuffer();
        // 获取请求参数集合并进行遍历拼接
        for (int i = 0; i < args.length; i++) {
            if (paramsBuf.length() > 0) {
                paramsBuf.append("|");
            }
            paramsBuf.append(parameterNamesArgs[i]).append(" = ").append(args[i]);
        }
        StringBuffer headerBuf = new StringBuffer();
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String key = (String) headerNames.nextElement();
            String value = request.getHeader(key);
            if (headerBuf.length() > 0) {
                headerBuf.append("|");
            }
            headerBuf.append(key).append("=").append(value);
        }

        // 打印请求参数参数
        long start = System.currentTimeMillis();// 记录开始时间
        log.info("请求| ip:{} | 请求接口:{} | 请求类:{} | 方法 :{} | 参数:{} | 请求header:{}|请求时间 :{}", ip, requestURL, className, methodName, paramsBuf.toString(), headerBuf.toString(), start);
        result = pjp.proceed();// 执行目标方法
        // 获取执行完的时间 打印返回报文
        log.info("返回| 请求接口:{}| 方法 :{} | 请求时间:{} | 处理时间:{} 毫秒 | 返回结果 :{}", requestURL, methodName, start, (System.currentTimeMillis() - start), result);
        return result;
    }

    /**
     * @Description: 获取ip
     */
    public String getIpAddr(HttpServletRequest request) {
        String ipAddress = null;
        ipAddress = request.getHeader("x-forwarded-for");
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getRemoteAddr();
        }
        // 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
        if (ipAddress != null && ipAddress.length() > 15) { // "***.***.***.***".length()
            // = 15
            if (ipAddress.indexOf(",") > 0) {
                ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
            }
        }
        // 或者这样也行,对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
        return ipAddress;
    }

    /**
     * 正常返回通知，拦截用户操作日志，连接点正常执行完成后执行， 如果连接点抛出异常，则不会执行
     *
     * @param joinPoint 切入点
     * @param keys      返回结果
     */
    @AfterReturning(value = "privilege()", returning = "keys")
    public void saveOperLog(JoinPoint joinPoint, Object keys) {
        long beginTime = System.currentTimeMillis();
        // 获取RequestAttributes
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        // 从获取RequestAttributes中获取HttpServletRequest的信息
        HttpServletRequest request = (HttpServletRequest) requestAttributes
                .resolveReference(RequestAttributes.REFERENCE_REQUEST);

        SysLog sysLog = new SysLog();
        try {
            // 从切面织入点处通过反射机制获取织入点处的方法
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            // 获取切入点所在的方法
            Method method = signature.getMethod();
            // 获取操作
            OperLog opLog = method.getAnnotation(OperLog.class);
            if (opLog != null) {
                String value = opLog.description();
                sysLog.setDescription(value);
            }
            // 获取请求的类名
            String className = joinPoint.getTarget().getClass().getName();
            // 获取请求的方法名
            String methodName = method.getName();
            methodName = className + "." + methodName + "()";

            // 请求方法
            sysLog.setMethod(methodName);
            String params = getParams(joinPoint, method);

            sysLog.setParams(params);
            // 请求用户名称
            SysUser sysUser = JwtUtil.CurrentUser();
            if (sysUser != null) {
                sysLog.setUserId(sysUser.getId());
                sysLog.setUserName(sysUser.getUsername());
            }
            // 访问的url
            sysLog.setUrl(request.getRequestURL().toString());
            // 请求IP
            sysLog.setIp(IPUtils.getIpAddr(request));
            // 创建时间
            sysLog.setCreateTime(new Date());
            //

            // 保存日志
            sysLogService.save(sysLog);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取请求的参数名称
     *
     * @param joinPoint
     * @param method
     * @return
     */
    private String getParams(JoinPoint joinPoint, Method method) {
        // 请求的方法参数值
        Object[] args = joinPoint.getArgs();
        // 请求的方法参数名称
        LocalVariableTableParameterNameDiscoverer u = new LocalVariableTableParameterNameDiscoverer();
        String[] paramNames = u.getParameterNames(method);
        String params = null;
        if (args != null && paramNames != null) {
            for (int i = 0; i < args.length; i++) {
                params += "  " + paramNames[i] + ": " + args[i];
            }
        }
        return params;
    }
}