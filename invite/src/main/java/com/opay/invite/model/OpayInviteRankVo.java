package com.opay.invite.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "邀请排行列表", description = "邀请排行列表")
public class OpayInviteRankVo {

    @ApiModelProperty(value = "邀请人")
    private String masterId;

    @ApiModelProperty(value = "邀请人名称")
    private String name;

    @ApiModelProperty(value = "邀请人得到奖励")
    private BigDecimal totalReward;

    @ApiModelProperty(value = "邀请人的总人数")
    private int totalNum;

    @ApiModelProperty(value = "")
    private String masterPhone;

}
