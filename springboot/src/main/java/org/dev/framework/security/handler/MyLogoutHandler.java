package org.dev.framework.security.handler;

import org.dev.framework.security.jwt.JwtTokenUtil;
import org.dev.framework.modules.sys.entity.SysUser;
import org.dev.framework.security.jwt.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 注销操作
 */
public class MyLogoutHandler implements LogoutHandler {
    @Autowired
    RedisTemplate<String, String> redisTemplate;

    @Override
    public void logout(HttpServletRequest httpServletRequest,
                       HttpServletResponse httpServletResponse,
                       Authentication authentication) {
        String tokenHeader = httpServletRequest.getHeader(JwtTokenUtil.TOKEN_HEADER);
        if (tokenHeader != null && tokenHeader.startsWith("Bearer ")) {
            tokenHeader = tokenHeader.replace(JwtTokenUtil.TOKEN_PREFIX, "").replace("Bearer ","");
        }
        //SysUser jwtUser = (SysUser) authentication.getPrincipal();
        //System.out.println("jwtUser:" + jwtUser.toString());
        redisTemplate.opsForHash().getOperations().delete(tokenHeader);
    }
}
