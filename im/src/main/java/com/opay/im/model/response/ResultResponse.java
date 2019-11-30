package com.opay.im.model.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel(value = "返回结果")
@Getter
@Setter
public class ResultResponse<T> {
    @ApiModelProperty(value = "错误码")
    private int code;
    @ApiModelProperty(value = "结果描述,异常信息")
    private String message;
    @ApiModelProperty(value = "返回数据")
    private T data;

    public ResultResponse() {
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
