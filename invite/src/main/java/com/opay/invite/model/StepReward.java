package com.opay.invite.model;

import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;

public class StepReward {

    @ApiModelProperty(value = "阶段顺序",hidden = true)
    private int orderId;//阶段顺序

    @ApiModelProperty(value = "人数区间min")
    private int min;//最小人数

    @ApiModelProperty(value = "人数区间max")
    private int max;//最大人数

    @ApiModelProperty(value = "阶段充值奖励")
    private BigDecimal walletReward;//充值奖励

    @ApiModelProperty(value = "注册码奖励",hidden = true)
    private BigDecimal regReward;//注册码奖励
    //
    @ApiModelProperty(value = "0:不是所属阶段，1：所属")
    private int step;//所处的接口

    //总奖励
    @ApiModelProperty(value = "阶段总奖励")
    private BigDecimal totalReward;

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public BigDecimal getWalletReward() {
        return walletReward;
    }

    public void setWalletReward(BigDecimal walletReward) {
        this.walletReward = walletReward;
    }

    public BigDecimal getRegReward() {
        return regReward;
    }

    public void setRegReward(BigDecimal regReward) {
        this.regReward = regReward;
    }

    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }

    public BigDecimal getTotalReward() {
        return totalReward;
    }

    public void setTotalReward(BigDecimal totalReward) {
        this.totalReward = totalReward;
    }
}
