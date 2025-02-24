package com.poc.transaction_management_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import com.poc.transaction_management_service.entity.Account;

@FeignClient(name = "ACCOUNT-MANAGEMENT-SERVICE", url = "http://localhost:8881")
public interface AccountClient {

	@GetMapping("/accounts/by-username/{username}")
    Account getAccountByUsername(@PathVariable String username, @RequestHeader("Authorization") String authorizationHeader);
}
