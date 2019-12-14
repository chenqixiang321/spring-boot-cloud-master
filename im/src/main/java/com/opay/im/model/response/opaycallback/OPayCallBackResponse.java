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
public class OPayCallBackResponse {
    private String type;
    private String sha512;
    private PayloadResponse payload;
}
