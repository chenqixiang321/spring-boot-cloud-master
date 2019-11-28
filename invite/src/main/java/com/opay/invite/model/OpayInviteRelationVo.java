package com.opay.invite.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "邀请关系列表", description = "邀请关系列表")
public class OpayInviteRelationVo {

    @ApiModelProperty(value = "邀请人")
    private String masterId;

    @ApiModelProperty(value = "被要人")
    private String pupilId;

    @ApiModelProperty(value = "邀请时间")
    private Date createAt;

    @ApiModelProperty(value = "邀请人得到奖励,及被邀人贡献")
    private Long masterReward;

}