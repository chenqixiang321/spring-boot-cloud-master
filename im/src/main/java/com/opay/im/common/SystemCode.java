package com.opay.im.common;

import lombok.Getter;

public enum SystemCode {
    SYS_API_SUCCESS("00000", "success"),
    SYS_ERROR("00003", "system error"),
    SYS_ARG_NOT_VALID("00004", "Method Argument Not Valid"),
    IM_ERROR("10001", "Im server error"),
    LUCKY_MONEY_ERROR("20001", "The lucky money has been robbed"),
    LUCKY_MONEY_LIMIT_ERROR("20002", "Today's red envelope amount has reached the limit"),
    LUCKY_MONEY_EXPIRED_ERROR("20003", "The lucky money does not exist or has expired"),
    LUCKY_MONEY_GONE_ERROR("20004", "The lucky money has been robbed"),
    ;


    SystemCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    @Getter
    private String code;
    @Getter
    private String message;
}
