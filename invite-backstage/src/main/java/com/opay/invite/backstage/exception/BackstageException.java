package com.opay.invite.backstage.exception;

/**
 * @author liuzhihang
 * @date 2019/12/17 14:45
 */
public class BackstageException extends Exception {

    private int code;

    private String msg;


    public BackstageException(BackstageExceptionEnum backstageExceptionEnum) {
        super(backstageExceptionEnum.getMessage());
        this.code = backstageExceptionEnum.getCode();
        this.msg = backstageExceptionEnum.getMessage();
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
