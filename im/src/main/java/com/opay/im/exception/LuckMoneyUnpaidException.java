package com.opay.im.exception;

public class LuckMoneyUnpaidException extends Exception {

    public LuckMoneyUnpaidException(String message) {
        super(message);
    }

    public LuckMoneyUnpaidException() {
        super();
    }

    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }
}
