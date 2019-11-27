package com.opay.invite.stateconfig;

import java.math.BigDecimal;

public class AgentRoyaltyReward {
    private int action;//3、4、5

    private BigDecimal masterReward;//师傅提成百分比

    private BigDecimal pupilReward;//徒弟提成百分比

    private String actionName;//描述


//    private BigDecimal masterAirtimeReward;//代理徒弟话费充值提成百分比
//
//    private BigDecimal masterBettingReward;//代理徒弟博彩提成百分比
//
//    private BigDecimal masterOrideReward;//代理徒弟打车百分比
//
//    private BigDecimal pupilAirtimeReward;//徒弟话费充值提成百分比
//
//    private BigDecimal pupilBettingReward;//徒弟博彩提成百分比
//
//    private BigDecimal pupilOrideReward;//徒弟打车百分比


    public int getAction() {
        return action;
    }

    public void setAction(int action) {
        this.action = action;
    }

    public BigDecimal getMasterReward() {
        return masterReward;
    }

    public void setMasterReward(BigDecimal masterReward) {
        this.masterReward = masterReward;
    }

    public BigDecimal getPupilReward() {
        return pupilReward;
    }

    public void setPupilReward(BigDecimal pupilReward) {
        this.pupilReward = pupilReward;
    }

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }
}
