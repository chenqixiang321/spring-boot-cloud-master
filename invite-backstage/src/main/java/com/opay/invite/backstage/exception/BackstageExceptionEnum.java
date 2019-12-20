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

    // 操作员相关异常
    PARAMETER_EMPTY(1001, "Request parameter is empty"),
    OPERATOR_ID_ERROR(1002, "Please enter the correct operatorId"),
    OPERATOR_PWD_ERROR(1003, "Please enter the correct operatorPWD"),
    OPERATOR_NOT_EXIST(1004, "Operator does not exist"),
    PWD_ERROR(1005, "The password is incorrect"),

    // 抽奖相关异常
    DRAW_RECORD_ID_EMPTY(2001, "Draw record id empty"),
    REDEEM_STATUS_ERROR(2002, "RedeemStatus error"),

    // 提现相关异常
    OPAY_ID_EMPTY(3001, "opayId is empty"),
    WITHDRAW_RECORD_NOT_EXIST(3002, "Withdraw record is not exist, please check yours reference"),
    WITHDRAW_OPERATE_STATUS_ERROR(3003, "Withdraw operate status error"),
    WITHDRAW_OPERATE_TYPE_ERROR(3004, "Withdraw operate type error"),
    WITHDRAW_OPERATE_STATUS_UPDATE_ERROR(3005, "Withdraw operate status update error"),
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
