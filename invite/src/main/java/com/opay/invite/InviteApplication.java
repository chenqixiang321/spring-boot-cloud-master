package com.opay.invite;

import com.opay.invite.service.OpayFeignApiService;
import com.opos.feign.OpayFeign;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableAsync;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication(scanBasePackages  = {"com.opay","com.opos"} )
@EnableSwagger2
@EnableCaching
@EnableHystrix
@EnableFeignClients(clients = {OpayFeign.class, OpayFeignApiService.class})
@EnableAsync
public class InviteApplication {

    public static void main(String[] args) {
        SpringApplication.run(InviteApplication.class, args);
    }
}
