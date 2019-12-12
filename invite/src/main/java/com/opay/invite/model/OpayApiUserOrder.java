package com.opay.invite.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OpayApiUserOrder {
    private String actualPayAmount;

    private String orderAmount;

    private String orderNo;

    private String userId;

    private String payTime;
}
