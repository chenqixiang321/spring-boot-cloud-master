package com.opay.invite.stateconfig;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "tixian.limit")
public class TixianLimitConfig {

    private BigDecimal perDayAmount;

    private int open;

    private List<TixianLimit> list;

    private BigDecimal perAmount;

}
