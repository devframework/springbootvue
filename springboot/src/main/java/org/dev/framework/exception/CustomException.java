package org.dev.framework.exception;

public class CustomException extends Exception {
    public CustomException(String Message) {
        this.setMessage(Message);
    }

    public CustomException(int code, String Message) {
        this.setCode(code);
        this.setMessage(Message);
    }

    int code;//状态代码
    String message;//异常提示信息

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

