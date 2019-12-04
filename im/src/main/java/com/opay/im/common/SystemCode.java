package com.opay.im.common;

import lombok.Getter;

public enum SystemCode {
    SYS_API_SUCCESS("00000", "success"),
    SYS_ERROR("00003", "system error"),
    SYS_ARG_NOT_VALID("00004", "Method Argument Not Valid"),
    IM_ERROR("10001", "Im server error"),
    LUCKY_MONEY_ERROR("20001", "The lucky money has been robbed"),
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
