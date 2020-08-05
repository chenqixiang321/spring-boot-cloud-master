package com.opay.invite.backstage.service.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 操作员登陆返回信息
 *
 * @author liuzhihang
 * @date 2019/12/17 14:55
 */
@Data
@ApiModel(value = "操作员登陆返回信息")
public class LoginRespDto extends BaseRespDto {

    @ApiModelProperty(value = "操作员名称")
    private String operateName;

}
