package com.opay.invite.transferconfig;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "transfer.opay")
public class TransferConfig {

    private String domain;

    private String merchantId;

    private String aesKey;

    private String url;

    private String reference;

    private String userUrl;

    private String userRecordUrl;

    private String userListUrl;

    private String batchListUrl;

    private String transferNotify;

}
