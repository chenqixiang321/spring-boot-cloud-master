package com.opay.invite.backstage.service.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 所有请求报文的基类
 *
 *
 * @author liuzhihang
 * @date 2019/12/17 14:38
 */
@Data
@ApiModel(value = "所有请求基类")
public class BaseReqDto implements Serializable {

    private String version;

    @ApiModelProperty(value = "登陆操作员id")
    private String operatorId;

}
