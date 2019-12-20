package com.opay.im.exception;

public class LuckyMoneyException extends Exception {
    private String code;

    public LuckyMoneyException(String code, String message) {
        super(message);
        setCode(code);
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
