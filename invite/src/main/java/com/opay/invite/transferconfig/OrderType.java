package com.opay.invite.transferconfig;

public enum OrderType {
    consumption("consumption","商家消费,用户使用余额或是奖励金支付"),
    bonusOffer("bonusOffer","奖励金发放,将OPOS现金余额转至用户奖励金账户"),
    MAcquiring("MAcquiring","商家收单,用户余额向商户转账"),
    MUAATransfer("MUAATransfer","商户余额向用户转账"),
    ;

    private OrderType(String orderType,String msg){
        this.orderType=orderType;
        this.msg=msg;
    }
    private String orderType;

    private String msg;

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
