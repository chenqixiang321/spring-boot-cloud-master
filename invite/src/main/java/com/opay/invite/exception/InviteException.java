package com.opay.invite.exception;

public class InviteException extends Exception {

    public InviteException(String message) {
        super(message);
    }

    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }
}
