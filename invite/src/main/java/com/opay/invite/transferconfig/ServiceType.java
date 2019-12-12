package com.opay.invite.transferconfig;


public enum ServiceType {
    TopupWithCard("TopupWithCard","快捷充值"),
    Airtime("Airtime","话费充值"),
        ;
    private String serviceType;

    private String msg;

    private ServiceType(String serviceType,String msg){
        this.serviceType=serviceType;
        this.msg=msg;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
