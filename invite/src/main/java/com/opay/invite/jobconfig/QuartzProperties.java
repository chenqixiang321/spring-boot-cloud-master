package com.opay.invite.jobconfig;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Properties;

/**
 * quartz 配置类
 *
 * @author liuqs
 */
@ConfigurationProperties(prefix = "spring.quartz")
public class QuartzProperties {

    private Properties properties = new Properties();

    public Properties getProperties() {
        return properties;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }
}
