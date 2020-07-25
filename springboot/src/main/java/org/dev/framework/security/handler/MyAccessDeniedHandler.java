package org.dev.framework.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.dev.framework.common.ResponseResult;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/***
 * 权限不足处理类
 *
 */
@Component
public class MyAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest httpServletRequest,
                       HttpServletResponse httpServletResponse,
                       AccessDeniedException e) throws IOException, ServletException {
        //设置响应的数据类型
        httpServletResponse.setContentType("application/json;charset=utf-8");
        //拿到witer
        PrintWriter out = httpServletResponse.getWriter();
        ResponseResult<String> responseResult = ResponseResult.error("权限不足");
        responseResult.setCode(403);
        out.write(new ObjectMapper().writeValueAsString(responseResult));
        out.flush();
        out.close();
    }
}
