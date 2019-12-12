package com.opay.invite.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OpayApiOrderDataRequest {

    private String senderId;

    private String senderMobile;

    private String recieptId;

    private String recieptMobile;

    private String amount;

    private String currency;

    private String country;

    private String reference;

    private String narration;

    private String productDesc;

    private String orderType;

    private String payChannel;

    private String activityType;

    private String originalOrderNo;

    private String callBackURL;

    private String qrContent;

    private String realSenderId;//针对红包

    public OpayApiOrderDataRequest(String senderId, String recieptId, String amount,
                                   String reference, String orderType, String activityType,
                                   String callBackURL, String realSenderId) {
        this.senderId = senderId;
        this.recieptId = recieptId;
        this.amount = amount;
        this.reference = reference;
        this.orderType = orderType;
        this.activityType = activityType;
        this.callBackURL = callBackURL;
        this.realSenderId = realSenderId;
        this.currency="NGN";
        this.country="NG";
    }
}
