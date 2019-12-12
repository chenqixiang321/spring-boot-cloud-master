package com.opay.invite.transferconfig;

public enum ActivityType {
    CASHBACK("CASHBACK", "返现,orderType为bonusOffer，必传参数。奖励金发放传CASHBACK，打折活动传DISCOUNT"),
            ;

    private ActivityType(String activityType,String msg){
        this.activityType=activityType;
        this.msg=msg;
    }
    private String activityType;

    private String msg;

    public String getActivityType() {
        return activityType;
    }

    public void setActivityType(String activityType) {
        this.activityType = activityType;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
