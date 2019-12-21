package com.opay.invite.config;


import com.opay.invite.service.OpayFeignApiService;
import com.opay.invite.service.impl.OpayFeignApiServiceFallback;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class OpayFeignFactory implements FallbackFactory<OpayFeignApiService> {
    private final OpayFeignApiServiceFallback opayFeignApiServiceFallback;

    public OpayFeignFactory(OpayFeignApiServiceFallback opayFeignApiServiceFallback) {
        this.opayFeignApiServiceFallback = opayFeignApiServiceFallback;
    }

    public OpayFeignApiService create(Throwable throwable) {
        log.error("OpayFeign error", throwable);
        return this.opayFeignApiServiceFallback;
    }
}
