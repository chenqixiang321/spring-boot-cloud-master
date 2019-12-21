package com.opay.invite.backstage.service.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ApiModel(value = "返回结果")
@Getter
@Setter
@ToString
public class OpayApiResultResponse<T> {
    @ApiModelProperty(value = "错误码")
    private String code;
    @ApiModelProperty(value = "结果描述,异常信息")
    private String message;
    @ApiModelProperty(value = "返回数据")
    private T data;

    public OpayApiResultResponse() {
    }

    public OpayApiResultResponse(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public OpayApiResultResponse(String code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }
}
