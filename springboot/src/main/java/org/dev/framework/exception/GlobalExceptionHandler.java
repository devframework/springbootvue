package org.dev.framework.exception;

import org.dev.framework.common.ResponseResult;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalExceptionHandler {
    /**
     * 处理业务发现问题主动抛出的异常
     *
     * @param request
     * @param e
     * @return
     * @throws Exception
     */
    @ExceptionHandler(value = CustomException.class)
    @ResponseBody
    public ResponseEntity<CustomException> baseErrorHandler(HttpServletRequest request, CustomException e) throws Exception {
        //把错误输出到日志
        return new ResponseEntity<CustomException>(e, HttpStatus.valueOf(e.getCode()));
    }

    /**
     * 系统抛出的没有处理过的异常
     *
     * @param request
     * @param e
     * @return
     * @throws Exception
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ResponseResult<Exception> defaultErrorHandler(HttpServletRequest request, Exception e) throws Exception {
        //把错误输出到日志
        return ResponseResult.error(HttpStatus.INTERNAL_SERVER_ERROR.value(), "服务器异常" + e.getMessage(), null);
    }

    /**
     * 系统抛出的没有处理过的异常
     *
     * @param request
     * @param e
     * @return
     * @throws Exception
     */
    @ExceptionHandler(value = NullPointerException.class)
    @ResponseBody
    public ResponseResult<Exception> notfundErrorHandler(HttpServletRequest request, Exception e) throws Exception {
        //把错误输出到日志
        return ResponseResult.error(HttpStatus.NOT_FOUND.value(), "服务器异常" + e.getMessage(), null);
    }
}
