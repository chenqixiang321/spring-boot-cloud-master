package com.opay.im.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ResultResponse<T> {
    private int code;
    private String message;
    private T data;

    public ResultResponse(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
