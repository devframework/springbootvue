package org.dev.framework.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.dev.framework.common.ResponseResult;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class MyLogoutSuccessHandler implements LogoutSuccessHandler {
    @Override
    public void onLogoutSuccess(HttpServletRequest httpServletRequest,
                                HttpServletResponse httpServletResponse,
                                Authentication authentication)
            throws IOException, ServletException {
        //清空上下文
        SecurityContextHolder.clearContext();
        // 从session中移除
        httpServletRequest.getSession().removeAttribute("SPRING_SECURITY_CONTEXT");
        //设置响应的数据类型
        httpServletResponse.setContentType("application/json;charset=utf-8");
        //拿到witer
        PrintWriter out = httpServletResponse.getWriter();
        ResponseResult<String> responseResult = ResponseResult.success("注销成功");
        out.write(new ObjectMapper().writeValueAsString(responseResult));
        out.flush();
        out.close();
    }
}
