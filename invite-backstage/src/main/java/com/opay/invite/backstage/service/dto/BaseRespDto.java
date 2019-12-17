package com.opay.invite.backstage.service.dto;

import com.opay.invite.backstage.exception.BackstageException;
import com.opay.invite.backstage.exception.BackstageExceptionEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 *
 * 所有返回结果的父类
 *
 * @author liuzhihang
 * @date 2019/12/17 14:38
 */
@Data
@ApiModel(value = "返回结果")
public class BaseRespDto implements Serializable {
    private static final long serialVersionUID = 8637333101163212351L;

    @ApiModelProperty(value = "返回码 200 成功， 其他失败")
    private int code;

    @ApiModelProperty(value = "返回错误信息")
    private String message;


    public void buildSuccess() {
        this.code = BackstageExceptionEnum.SUCCESS.getCode();
        this.message = BackstageExceptionEnum.SUCCESS.getMessage();
    }

    public void buildError(BackstageException e) {
        this.code = e.getCode();
        this.message = e.getMessage();
    }

    public void buildFail() {
        this.code = BackstageExceptionEnum.FAIL.getCode();
        this.message = BackstageExceptionEnum.FAIL.getMessage();

    }
}
