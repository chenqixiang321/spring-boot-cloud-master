package com.opay.invite.transferconfig;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "opay.luckDraw")
public class LuckDrawConfig {

    private String merchantId;

    private String aesKey;

    private String iv;

    private String publickey;

    private String privatekey;

    private String callBack;

}
