package com.opay.invite.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "邀请奖励", description = "邀请奖励")
public class OpayMasterPupilAward {

    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "受益人")
    private String opayId;

    @ApiModelProperty(value = "奖励金额")
    private BigDecimal reward=BigDecimal.ZERO;

    @ApiModelProperty(value = "收益时间")
    private Date createAt;

    @ApiModelProperty(value = "收益状态")
    private int status;

    @ApiModelProperty(value = "行为")
    private int action;

    @ApiModelProperty(value = "充值时等操作，实际金额")
    private BigDecimal amount=BigDecimal.ZERO;

    @ApiModelProperty(value = "邀请人账号类型0:普通新用户，1:老用户 2:代理")
    private int markType;

    @ApiModelProperty(value = "阶段值")
    private String stepJson;

    @ApiModelProperty(value = "当前用户为徒弟,master收益")
    private BigDecimal masterReward=BigDecimal.ZERO;

    @ApiModelProperty(value = "0:徒弟 1:师傅")
    private int masterType;

    @ApiModelProperty(value = "年月201911")
    private int month;

    @ApiModelProperty(value = "年月日20191101")
    private int day;

    @ApiModelProperty(value = "外部订单ID")
    private String orderId;

    @ApiModelProperty(value = "徒弟ID")
    private String pupilId;

    public OpayMasterPupilAward(
            String opayId, BigDecimal reward, Date createAt, int status,
             int action, BigDecimal amount,int markType, String stepJson,
            BigDecimal masterReward, int masterType,int month,int day) {
        this.opayId = opayId;
        this.reward = reward;
        this.createAt = createAt;
        this.status = status;
        this.action = action;
        this.amount = amount;
        this.markType = markType;
        this.stepJson = stepJson;
        this.masterReward = masterReward;
        this.masterType = masterType;
        this.month=month;
        this.day=day;

    }
}
