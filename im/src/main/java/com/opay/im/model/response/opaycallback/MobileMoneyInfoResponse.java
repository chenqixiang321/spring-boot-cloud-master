package com.opay.im.model.response.opaycallback;

import com.fasterxml.jackson.annotation.JsonProperty;
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
public class MobileMoneyInfoResponse {
    @JsonProperty("phone_number")
    private String phoneNumber;
    private String provider;
}
