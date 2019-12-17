package com.opay.invite.backstage.exception;

/**
 * 异常枚举类型
 *
 *
 * @author liuzhihang
 * @date 2019/12/17 14:47
 */
public enum BackstageExceptionEnum {


    SUCCESS(200, "success"),
    FAIL(201, "failure"),

    PARAMETER_EMPTY(1001,"Request parameter is empty"),
    OPERATOR_ID_ERROR(1002,"Please enter the correct operatorId"),
    OPERATOR_PWD_ERROR(1003,"Please enter the correct operatorPWD"),
    OPERATOR_NOT_EXIST(1004,"Operator does not exist"),
    PWD_ERROR(1005,"The password is incorrect"),
    ;
    private int code;

    private String message;

    BackstageExceptionEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
