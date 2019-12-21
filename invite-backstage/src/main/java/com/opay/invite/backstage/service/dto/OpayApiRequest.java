package com.opay.invite.backstage.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OpayApiRequest {
    private String requestId;
    private String merchantId;
    private String data;
}
