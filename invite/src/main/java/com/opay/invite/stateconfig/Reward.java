package com.opay.invite.stateconfig;

import java.math.BigDecimal;

public class Reward {

    private int orderId;//阶段顺序

    private int min;//最小人数

    private int max;//最大人数

    private BigDecimal walletReward;//充值奖励.师傅得到奖励

    private BigDecimal regReward;//注册码奖励

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

    public BigDecimal getTotalReward() {
        return totalReward;
    }

    public void setTotalReward(BigDecimal totalReward) {
        this.totalReward = totalReward;
    }
}
