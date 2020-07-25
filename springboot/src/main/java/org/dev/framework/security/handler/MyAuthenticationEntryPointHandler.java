package org.dev.framework.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.dev.framework.common.ResponseResult;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/***
 * 未登录访问
 *
 */
@Component
public class MyAuthenticationEntryPointHandler implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest httpServletRequest,
                         HttpServletResponse httpServletResponse,
                         AuthenticationException e)
            throws IOException, ServletException {
        httpServletResponse.setContentType("application/json;charset=utf-8");
        //拿到witer
        PrintWriter out = httpServletResponse.getWriter();
        ResponseResult<String> responseResult = new ResponseResult<>();
        responseResult.setCode(401);
        responseResult.setMsg("尚未登录,请先登录");
        //把map弄成json字符串写出去
        out.write(new ObjectMapper().writeValueAsString(responseResult));
        out.flush();
        out.close();
    }
}
