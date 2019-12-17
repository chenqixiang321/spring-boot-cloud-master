package com.opay.invite.backstage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages  = {"com.opay"} )
public class InviteBackstageApplication {

	public static void main(String[] args) {
		SpringApplication.run(InviteBackstageApplication.class, args);
	}

}
