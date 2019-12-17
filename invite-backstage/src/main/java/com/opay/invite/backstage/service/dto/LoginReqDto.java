package com.opay.invite.backstage.service.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 用户登陆请求参数
 *
 * @author liuzhihang
 * @date 2019/12/17 14:41
 */
@Data
@ApiModel(value = "操作员登陆", description = "操作员请求登陆接口")
public class LoginReqDto {

    @ApiModelProperty(value = "登陆操作员id")
    private String operatorId;

    @ApiModelProperty(value = "操作员密码")
    private String loginPwd;


}
