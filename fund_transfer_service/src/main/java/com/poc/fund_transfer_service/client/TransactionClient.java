package com.poc.fund_transfer_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestHeader;

import com.poc.fund_transfer_service.entity.Transaction;

@FeignClient(name = "transaction-management-service", url = "http://localhost:8883")
public interface TransactionClient {
	
	@PostMapping("/transactions")
    Transaction logTransaction(@RequestBody Transaction transaction);

}
