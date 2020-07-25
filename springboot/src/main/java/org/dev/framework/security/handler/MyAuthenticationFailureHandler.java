package org.dev.framework.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.dev.framework.common.ResponseResult;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/***
 * 认证失败处理类
 */
@Component
public class MyAuthenticationFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest,
                                        HttpServletResponse httpServletResponse,
                                        AuthenticationException e)
            throws IOException, ServletException {
        httpServletResponse.setContentType("application/json;charset=utf-8");
        //拿到witer
        PrintWriter out = httpServletResponse.getWriter();
        ResponseResult<String> responseResult = new ResponseResult<>();
        responseResult.setCode(403);
        if (e instanceof LockedException) {
            responseResult.setMsg("账户被锁定，登录失败");
        } else if (e instanceof BadCredentialsException) {
            responseResult.setMsg("用户名或密码输入错误，登录失败");
        } else if (e instanceof DisabledException) {
            responseResult.setMsg("账户被禁用，登录失败");
        } else if (e instanceof AccountExpiredException) {
            responseResult.setMsg("账户过期，登录失败");
        } else if (e instanceof CredentialsExpiredException) {
            responseResult.setMsg("密码过期，登录失败");
        } else {
            responseResult.setMsg("登录失败");
        }

        //把map弄成json字符串写出去
        out.write(new ObjectMapper().writeValueAsString(responseResult));
        out.flush();
        out.close();
    }
}
