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
@ApiModel(value = "邀请码", description = "邀请码")
public class OpayInviteCode{
    @ApiModelProperty(value = "注册用户/填入邀请码")
    private String opayId;

    @ApiModelProperty(value = "注册用户人手机号")
    private String phone;

    @ApiModelProperty(value = "邀请码")
    private String inviteCode;
}
