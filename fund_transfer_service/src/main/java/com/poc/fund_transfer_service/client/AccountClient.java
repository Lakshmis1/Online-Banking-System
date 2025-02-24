package com.poc.fund_transfer_service.client;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;

import com.poc.fund_transfer_service.entity.Account;

//@Service
@FeignClient(name = "ACCOUNT-MANAGEMENT-SERVICE", url = "http://localhost:8881") 
public interface AccountClient {

    @GetMapping("/accounts/by-accountNumber/{accountNumber}")
    Account getAccountByAccountNumber(@PathVariable Long accountNumber, @RequestHeader("Authorization") String authorizationHeader);
    
    @GetMapping("/accounts/by-username/{username}")
    Account getAccountByUsername(@PathVariable String username, @RequestHeader("Authorization") String authorizationHeader);

    @PutMapping("/accounts/{accountNumber}")
    void updateAccount(@PathVariable Long accountNumber, @RequestBody Account account, @RequestHeader("Authorization") String authorizationHeader);
}
