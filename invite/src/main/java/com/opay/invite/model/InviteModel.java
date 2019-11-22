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
@ApiModel(value = "邀请关系", description = "邀请关系")
public class InviteModel {

    @ApiModelProperty(value = "邀请人")
    private String masterId;

    @ApiModelProperty(value = "被要人")
    private String pupilId;

    @ApiModelProperty(value = "邀请时间")
    private Long dateline;

    @ApiModelProperty(value = "邀请人的邀请人")
    private String masterParentId;

    @ApiModelProperty(value = "0:普通新用户，1:老用户 2:代理")
    private int markType;



}
