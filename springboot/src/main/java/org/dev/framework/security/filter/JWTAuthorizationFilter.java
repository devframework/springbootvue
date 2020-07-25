package org.dev.framework.security.filter;


import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import org.dev.framework.security.jwt.JwtTokenUtil;
import org.dev.framework.common.ResponseResult;
import org.dev.framework.modules.sys.service.SysUserService;
import org.dev.framework.security.jwt.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by echisan on 2018/6/23
 */
@Component
public class JWTAuthorizationFilter extends OncePerRequestFilter {

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @Autowired
    SysUserService sysUserService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws IOException, ServletException {

        String tokenHeader = request.getHeader(JwtTokenUtil.TOKEN_HEADER);
        // 如果请求头中没有Authorization信息则直接放行了
        if (tokenHeader == null || !tokenHeader.startsWith(JwtTokenUtil.TOKEN_PREFIX)) {
            chain.doFilter(request, response);
            return;
        }
        // 如果请求头中有token，则进行解析，并且设置认证信息

        String username = null;
        String jwtToken = null;
        // JWT Token is in the form "Bearer token". Remove Bearer word and get
        // only the Token
        if (tokenHeader != null && tokenHeader.startsWith("Bearer ")) {
            jwtToken = tokenHeader.replace(JwtTokenUtil.TOKEN_PREFIX, "");
            try {
                username = jwtTokenUtil.getUsernameFromToken(jwtToken);
            } catch (IllegalArgumentException e) {
                System.out.println("Unable to get JWT Token");
                response.setContentType("application/json;charset=utf-8");
                PrintWriter out = response.getWriter();
                ResponseResult<String> responseResult = ResponseResult.error("Token 解析异常");
                responseResult.setCode(401);
                out.write(new ObjectMapper().writeValueAsString(responseResult));
                out.flush();
                out.close();
                return;
            } catch (ExpiredJwtException e) {
                System.out.println("JWT Token has expired");
                response.setContentType("application/json;charset=utf-8");
                PrintWriter out = response.getWriter();
                ResponseResult<String> responseResult = ResponseResult.error("Token已过期,请重新登录");
                responseResult.setCode(401);
                out.write(new ObjectMapper().writeValueAsString(responseResult));
                out.flush();
                out.close();
                return;
            }
        } else {
            logger.warn("JWT Token does not begin with Bearer String");
            response.setContentType("application/json;charset=utf-8");
            PrintWriter out = response.getWriter();
            ResponseResult<String> responseResult = ResponseResult.error("Token不正确,请重新登录");
            responseResult.setCode(401);
            out.write(new ObjectMapper().writeValueAsString(responseResult));
            out.flush();
            out.close();
            return;
        }

        // Once we get the token validate it.
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            UserDetails userDetails = this.sysUserService.loadUserByUsername(username);

            // if token is valid configure Spring Security to manually set
            // authentication
            if (jwtTokenUtil.validateToken(jwtToken, userDetails)) {

                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken
                        .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                // After setting the Authentication in the context, we specify
                // that the current user is authenticated. So it passes the
                // Spring Security Configurations successfully.
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
        chain.doFilter(request, response);
    }
}
