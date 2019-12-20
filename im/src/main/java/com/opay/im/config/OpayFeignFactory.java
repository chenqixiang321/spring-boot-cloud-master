package com.opay.im.config;

import com.opay.im.service.OpayFeignApiService;
import com.opay.im.service.impl.OpayFeignApiServiceFallback;
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
