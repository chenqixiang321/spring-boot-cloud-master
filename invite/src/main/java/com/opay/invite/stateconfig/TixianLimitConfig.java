package com.opay.invite.stateconfig;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "tixian.limit")
public class TixianLimitConfig {

    private int person;

    private BigDecimal perDayAmount;

    private BigDecimal minAmount;


}
