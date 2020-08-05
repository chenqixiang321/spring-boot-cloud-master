package com.opay.im.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OpayAcceptLuckyMoneyRequest {
    private String accepterId;
    private Long amount;
    private String message;
    private String merchartOrderNo;
    private String sendOrderNo;
    private String clientSource;
}
