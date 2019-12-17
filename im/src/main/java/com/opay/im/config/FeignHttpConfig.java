package com.opay.im.config;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.protocol.HttpContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cloud.commons.httpclient.ApacheHttpClientConnectionManagerFactory;
import org.springframework.cloud.commons.httpclient.ApacheHttpClientFactory;
import org.springframework.cloud.openfeign.support.FeignHttpClientProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Timer;
import java.util.TimerTask;

@Configuration
public class FeignHttpConfig {
    private final Timer connectionManagerTimer = new Timer("FeignApacheHttpClientConfiguration.connectionManagerTimer", true);
    private CloseableHttpClient httpClient;
    @Autowired(
            required = false
    )
    private RegistryBuilder registryBuilder;
    @Bean
    @ConditionalOnMissingBean({HttpClientConnectionManager.class})
    public HttpClientConnectionManager connectionManager(ApacheHttpClientConnectionManagerFactory connectionManagerFactory, FeignHttpClientProperties httpClientProperties) {
        final HttpClientConnectionManager connectionManager = connectionManagerFactory.newConnectionManager(httpClientProperties.isDisableSslValidation(), httpClientProperties.getMaxConnections(), httpClientProperties.getMaxConnectionsPerRoute(), httpClientProperties.getTimeToLive(), httpClientProperties.getTimeToLiveUnit(), this.registryBuilder);
        this.connectionManagerTimer.schedule(new TimerTask() {
            public void run() {
                connectionManager.closeExpiredConnections();
            }
        }, 30000L, (long)httpClientProperties.getConnectionTimerRepeat());
        return connectionManager;
    }

    @Bean
    public CloseableHttpClient httpClient(ApacheHttpClientFactory httpClientFactory, HttpClientConnectionManager httpClientConnectionManager, FeignHttpClientProperties httpClientProperties) {
        RequestConfig defaultRequestConfig = RequestConfig.custom().setConnectTimeout(httpClientProperties.getConnectionTimeout()).setRedirectsEnabled(httpClientProperties.isFollowRedirects()).build();
        ConnectionKeepAliveStrategy connectionKeepAliveStrategy = new ConnectionKeepAliveStrategy() {
            @Override
            public long getKeepAliveDuration(org.apache.http.HttpResponse httpResponse,
                                             HttpContext httpContext) {
                return 20 * 1000; // 20 seconds,because tomcat default keep-alive timeout is 20s
            }
        };
        this.httpClient = httpClientFactory.createBuilder().setKeepAliveStrategy(connectionKeepAliveStrategy).setConnectionManager(httpClientConnectionManager).setDefaultRequestConfig(defaultRequestConfig).build();
        return this.httpClient;
    }
}
