package com.poc.transaction_management_service.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.poc.transaction_management_service.entity.Transaction;
import com.poc.transaction_management_service.service.TransactionService;

@RestController
@RequestMapping("/transactions")
public class TransactionController {
	
	@Autowired
    private TransactionService transactionService;

    @GetMapping
    public List<Transaction> getTransactions(@RequestHeader("Authorization") String authorizationHeader) {
    	 String token = authorizationHeader.startsWith("Bearer ") 
                 ? authorizationHeader.substring(7) 
                 : authorizationHeader;
        return transactionService.getTransactionsForLoggedInUser(token);
    }

    @PostMapping
    public Transaction logTransaction(@RequestBody Transaction transaction) {
        return transactionService.logTransaction(transaction);
    }

}
