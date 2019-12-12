package com.opay.invite.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ApiModel(value = "com-opay-invite-model-LuckDrawInfo")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class LuckDrawInfoModel {
    @ApiModelProperty(value = "null")
    private Long id;

    /**
     * 发送者的opay_id
     */
    @ApiModelProperty(value = "中奖者的opay_id")
    private String opayId;

    /**
     * 发送者的opay名字
     */
    @ApiModelProperty(value = "中奖者的opay名字")
    private String opayName;

    /**
     * 发送者的手机号
     */
    @ApiModelProperty(value = "中奖者的手机号")
    private String opayPhone;

    /**
     * 奖品
     */
    @ApiModelProperty(value = "奖品")
    private String prize;

    /**
     * 奖品级别 数越小越值钱
     */
    @ApiModelProperty(value = "奖品级别 数越小越值钱")
    private Integer prizeLevel;

    @ApiModelProperty(value = "奖池号")
    private Integer prizePool;
    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private Date createTime;
    @ApiModelProperty(value = "requestId", hidden = true)
    private String requestId;
    @ApiModelProperty(value = "reference", hidden = true)
    private String reference;
}