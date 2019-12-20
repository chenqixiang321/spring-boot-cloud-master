package com.opay.im;

import com.opay.im.service.OpayFeignApiService;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication(scanBasePackages = {"com.opay"})
@EnableSwagger2
@EnableCaching
@EnableHystrix
@EnableFeignClients(clients = {OpayFeignApiService.class})
@MapperScan(basePackages = "com.opay.im.mapper")
@EnableScheduling
//@EnableApolloConfig
public class ImApplication {

    public static void main(String[] args) {
        SpringApplication.run(ImApplication.class, args);
    }
}
