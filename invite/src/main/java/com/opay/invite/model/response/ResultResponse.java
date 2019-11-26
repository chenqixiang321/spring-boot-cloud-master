package com.opay.invite.model.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResultResponse<T> {
    private int code;
    private String message;
    private T data;

    public ResultResponse() {
        this.code = 200;
        this.message = "success";
    }

    public ResultResponse(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public ResultResponse(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public ResultResponse(T data) {
        this.code = 200;
        this.message = "success";
        this.data = data;
    }
}
