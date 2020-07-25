package org.dev.framework.security.jwt;

import org.dev.framework.modules.sys.entity.SysUser;
import org.dev.framework.modules.sys.entity.SysUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class JwtUtil {

    /**
     * 获取用户ID
     *
     * @return
     */
    public static Long CurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        SysUser sysUser = (SysUser) authentication.getPrincipal();
        return sysUser.getId();
    }

    /**
     * 获取用户名
     */
    public static String CurrentUserName() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        SysUser sysUser = (SysUser) authentication.getPrincipal();
        return sysUser.getUsername();
    }

    /**
     * 获取用户
     */
    public static SysUser CurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        SysUser sysUser = (SysUser) authentication.getPrincipal();
        return sysUser;
    }
}
