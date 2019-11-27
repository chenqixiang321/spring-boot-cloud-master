package com.opay.invite.stateconfig;


public enum  ActionOperate {
    operate_register(1,"注册","Redeem"),
    operate_recharge(2,"充值钱包","1st Top-Up"),
    operate_airtime(3,"充值话费","Use Airtime"),

    operate_betting(4,"博彩","Use Betting"),

    operate_oride(5,"打车","Use Oride"),

    operate_agent(6,"代理操作","Redeem"),
    ;

    private ActionOperate(int operate,String msg,String msgEn){
        this.operate=operate;
        this.msg=msg;
        this.msgEn=msgEn;
    }

    private int operate;

    private String msg;

    private String msgEn;

    public int getOperate() {
        return operate;
    }

    public void setOperate(int operate) {
        this.operate = operate;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getMsgEn() {
        return msgEn;
    }

    public void setMsgEn(String msgEn) {
        this.msgEn = msgEn;
    }

    public static String getMsgEn(int action){
        String mn ="";
        for(ActionOperate oe : ActionOperate.values()){
            if(oe.getOperate()==action){
                mn=oe.getMsgEn();
                break;
            }
        }
        return mn;
    }
}
