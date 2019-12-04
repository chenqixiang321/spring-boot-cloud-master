package com.opay.im.model.response;

import com.opay.im.common.SystemCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel(value = "返回结果")
@Getter
@Setter
public class ResultResponse<T> {
    @ApiModelProperty(value = "错误码")
    private String code;
    @ApiModelProperty(value = "结果描述,异常信息")
    private String message;
    @ApiModelProperty(value = "返回数据")
    private T data;

    public ResultResponse() {
    }

    public ResultResponse(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public ResultResponse(String code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public ResultResponse(T data) {
        this.code = SystemCode.SYS_API_SUCCESS.getCode();
        this.message = SystemCode.SYS_API_SUCCESS.getMessage();
        this.data = data;
    }
}
