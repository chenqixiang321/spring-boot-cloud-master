package com.opay.invite.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OpayApiOrderResultResponse {
    private String requestId;

    private String orderNo;

    private String reference;

    private String status;

    private String errorMsg;
}
