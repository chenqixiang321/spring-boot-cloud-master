package com.opay.im.exception;

public class LuckMoneyLimitException extends Exception {

    public LuckMoneyLimitException(String message) {
        super(message);
    }

    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }
}
