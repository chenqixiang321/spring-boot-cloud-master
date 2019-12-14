package com.opay.im.exception;

public class LuckMoneyGoneException extends Exception {

    public LuckMoneyGoneException(String message) {
        super(message);
    }

    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }
}
