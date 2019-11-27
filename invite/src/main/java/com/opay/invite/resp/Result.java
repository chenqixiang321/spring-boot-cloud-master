package com.opay.invite.resp;

import java.io.Serializable;

public class Result<T> implements Serializable {

    private static final long serialVersionUID = -5809782578272943999L;

    private String message;
    private int code;
    private T data;

    private Result(T data) {
        this.code = CodeMsg.SUCCESS.getCode();
        this.message = CodeMsg.SUCCESS.getMessage();
        this.data = data;
    }

    private Result(CodeMsg cm) {
        if (cm == null) {
            return;
        }
        this.code = cm.getCode();
        this.message = cm.getMessage();
    }


    /**
     * 成功时候的调用
     *
     * @return
     */
    public static <T> Result<T> success(T data) {
        return new Result<T>(data);
    }

    /**
     * 成功，不需要传入参数
     *
     * @return
     */
    public static <T> Result<T> success() {
        return (Result<T>) success(null);
    }

    /**
     * 失败时候的调用
     *
     * @return
     */
    public static <T> Result<T> error(CodeMsg cm) {
        return new Result<T>(cm);
    }

    /**
     * 失败时候的调用,扩展消息参数
     *
     * @param cm
     * @param msg
     * @return
     */
    public static <T> Result<T> error(CodeMsg cm, String msg) {
        cm.setMessage(cm.getMessage() + "--" + msg);
        return new Result<T>(cm);
    }

    public T getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }

    public int getCode() {
        return code;
    }
}