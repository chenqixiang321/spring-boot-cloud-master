package com.opay.im.constant;

public enum LuckyMoneyStatus {
    NOT_GRABBED("0", "未抢"),
    GRABBED("1", "已抢"),
    EXPIRED("2", "过期");

    private String code;

    private String name;

    LuckyMoneyStatus(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}
