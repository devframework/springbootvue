package org.dev.framework.core.aop;

import javax.servlet.http.HttpServletRequest;

/**
 * 获取请求的Ip地址
 *
 * @author zz
 * @since 2020/6/16
 */
public class IPUtils {

    private static final String X_FORWARDED_FOR = "x-forwarded-for";

    public static String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader(X_FORWARDED_FOR);
        if (ip == null||ip.length()==0||"unknown".equalsIgnoreCase(ip)){
            ip = request.getHeader("Proxy-client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return "0:0:0:0:0:0:0:1".equals(ip) ? "127.0.0.1" : ip;

    }
}
