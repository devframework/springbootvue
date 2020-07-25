package org.dev.framework.utils;

import org.dev.framework.modules.sys.entity.SysUser;
import org.dev.framework.modules.sys.entity.SysUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SpringSecurityUtils {

    /***
     * 获取当前用户信息
     * @return
     */
    public static SysUser CurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        SysUser sysUser = (SysUser) authentication.getPrincipal();
        return sysUser;
    }
}
