package com.opay.im.exception;

public class ImException extends Exception {
    private String code;

    public ImException(String code, String message) {
        super(message);
        setCode(code);
    }

    public ImException(int code, String message) {
        super(message);
        setCode(String.valueOf(code));
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }
}
