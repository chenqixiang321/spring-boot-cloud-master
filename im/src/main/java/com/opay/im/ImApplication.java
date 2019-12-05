package com.opay.im;

import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;
import com.opay.im.service.OpayFriends;
import com.opos.feign.OpayFeign;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication(scanBasePackages = {"com.opay", "com.opos"})
@EnableSwagger2
@EnableCaching
@EnableHystrix
@EnableFeignClients(clients = {OpayFeign.class, OpayFriends.class})
@MapperScan(basePackages = "com.opay.im.mapper")
@EnableApolloConfig
public class ImApplication {

    public static void main(String[] args) {
        SpringApplication.run(ImApplication.class, args);
    }
}
