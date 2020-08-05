package com.opay.im.model.response.opaycallback;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class PayloadResponse {
    private String status;
    private String reference;
    private String token;
    private String transactionId;
    private String timestamp;
    private boolean refunded;
    private String amount;
    private String channel;
    private String country;
    private String currency;
    private String fee;
    private String instrumentId;
    private String instrumentType;
    private MobileMoneyInfoResponse mobileMoneyInfo;
    private BankInfoResponse bankInfo;
    private CardInfoResponse cardInfo;
}
