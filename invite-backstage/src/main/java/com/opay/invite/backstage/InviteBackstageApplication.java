package com.opay.invite.backstage;

import com.opay.invite.backstage.service.OpayFeignApiService;
import com.opos.feign.OpayFeign;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@MapperScan("com.opay.invite.backstage.dao.mapper")
@EnableFeignClients(clients = {OpayFeign.class, OpayFeignApiService.class})
@EnableSwagger2
public class InviteBackstageApplication {

    public static void main(String[] args) {
        SpringApplication.run(InviteBackstageApplication.class, args);
    }

}
