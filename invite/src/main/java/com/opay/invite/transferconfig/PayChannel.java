package com.opay.invite.transferconfig;

public enum PayChannel {
    BalancePayment("BalancePayment", "orderType为bonusOffer时，目前只支持BalancePayment"),
    ;

    private PayChannel(String payChannel,String msg){
        this.payChannel=payChannel;
        this.msg=msg;
    }
    private String payChannel;

    private String msg;

    public String getPayChannel() {
        return payChannel;
    }

    public void setPayChannel(String payChannel) {
        this.payChannel = payChannel;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
