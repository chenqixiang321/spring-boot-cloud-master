package com.opay.invite.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "注册码", description = "注册码")
public class NotifyInvite {

    @ApiModelProperty(value = "注册用户/填入邀请码")
    private String opayId;

    @ApiModelProperty(value = "注册用户人手机号")
    private String phone;



    @ApiModelProperty(value = "注册时间：格式：2019-01-22 21:23:32")
    private String createTime;

    @ApiModelProperty(value = "邀请码")
    private String inviteCode;

    @ApiModelProperty(value = "签名")
    private String sign;
}
