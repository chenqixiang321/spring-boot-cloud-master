package com.opay.im.common;

import lombok.Getter;

public enum SystemCode {
    SYS_API_SUCCESS("00000", "success"),
    SYS_ERROR("00003", "system error"),
    SYS_ARG_NOT_VALID("00004", "Method Argument Not Valid"),
    IM_ERROR("10001", "Im server error"),

    LUCKY_MONEY_EXPIRED("20001", "The lucky money does not exist or has expired"),
    LUCKY_MONEY_GONE_ERROR("20002", "The lucky money has been robbed"),
    LUCKY_MONEY_HAS_ROBBED("20003", "You have robbed this lucky money"),
    LUCKY_MONEY_UNPAID("20004", "The lucky money unpaid"),
    LUCKY_MONEY_LIMIT("20005", "Send the lucky money reached the upper limit"),
    LUCKY_MONEY_DOES_NOT_EXIST("20006", "The lucky money does not exist"),

    IM_USER_DOES_NOT_EXIST("30001", "The opay user does not exist"),
    IM_NOT_BELONG_GROUP("30002", "You do not belong to this group"),


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
