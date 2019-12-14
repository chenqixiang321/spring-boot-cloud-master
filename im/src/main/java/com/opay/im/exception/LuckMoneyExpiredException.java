package com.opay.im.exception;

public class LuckMoneyExpiredException extends Exception {

    public LuckMoneyExpiredException(String message) {
        super(message);
    }

    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }
}
