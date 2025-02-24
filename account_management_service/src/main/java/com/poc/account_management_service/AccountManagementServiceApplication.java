package com.poc.account_management_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class AccountManagementServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AccountManagementServiceApplication.class, args);
	}

}
