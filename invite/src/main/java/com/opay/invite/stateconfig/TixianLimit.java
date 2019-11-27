package com.opay.invite.stateconfig;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Getter
@Setter
public class TixianLimit {

    private int min;

    private int max;

    private BigDecimal minAmount;


}
