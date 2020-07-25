package org.dev.framework.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Timestamp;

@Data
@ApiModel(value = "响应信息")
public class ResponseResult<T> {

    private static Logger logger = LoggerFactory.getLogger(ResponseResult.class);

    private static String SUCCESS = "操作成功";

    private static String ERROR = "操作失败";

    public static int OK = 0;

    public static int FAILL = 1;


    @ApiModelProperty("响应码，表示请求的状态。200表示成功，其他表示失败")
    private int code; // 响应码：200表示成功，其他表示失败

    @ApiModelProperty("响应描述")
    private String msg; // 响应描述

    @ApiModelProperty("返回数据")
    private T data; // 返回数据

    @ApiModelProperty(value = "时间戳", name = "timestamp")
    private Timestamp timestamp;

    public static ResponseResult<String> success() {
        return success(OK, SUCCESS, null);
    }

    public static <T> ResponseResult<T> success(T data) {
        return success(OK, SUCCESS, data);
    }

    public static <T> ResponseResult<T> success(int code, String msg) {
        return new ResponseResult<T>()
                .code(code)
                .msg(msg)
                .data(null)
                .timestamp();
    }

    public static <T> ResponseResult<T> success(int code, String msg, T data) {
        return new ResponseResult<T>()
                .code(code)
                .msg(msg)
                .data(data)
                .timestamp();
    }



    public static ResponseResult<String> error() {
        return error(FAILL, ERROR, null);
    }

    public static <T> ResponseResult<T> error(String msg) {
        return error(FAILL, msg, null);
    }

    public static <T> ResponseResult<T> error(int code, String msg, T data) {
        return new ResponseResult<T>()
                .code(code)
                .msg(msg)
                .data(data)
                .timestamp();
    }


    private ResponseResult<T> timestamp() {
        this.timestamp = new Timestamp(System.currentTimeMillis());
        return this;
    }

    private ResponseResult<T> data(T data) {
        this.data = data;
        return this;
    }

    private ResponseResult<T> msg(String msg) {
        this.msg = msg;
        return this;
    }

    private ResponseResult<T> code(int code) {
        this.code = code;
        return this;
    }

}
