package com.opay.invite.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("config.opay")
@Setter
@Getter
public class OpayConfig {
    private String domain;
    private String merchantId;
    private String aesKey;
    private String iv;
}
