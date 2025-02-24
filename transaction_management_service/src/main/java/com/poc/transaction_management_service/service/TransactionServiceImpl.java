package com.poc.transaction_management_service.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poc.transaction_management_service.client.AccountClient;
import com.poc.transaction_management_service.entity.Account;
import com.poc.transaction_management_service.entity.Transaction;
import com.poc.transaction_management_service.repository.TransactionRepository;
import com.poc.transaction_management_service.security.JwtUtil;

@Service
public class TransactionServiceImpl implements TransactionService{
	
	@Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private AccountClient accountClient;

    @Autowired
    private JwtUtil jwtUtil;

	@Override
	public List<Transaction> getTransactionsForLoggedInUser(String token) {
		// Extract username from the token
        String username = jwtUtil.extractUsername(token);

        // Fetch account details using Feign Client
        Account account = accountClient.getAccountByUsername(username, token);
        System.out.println("Fetched account number: " + account.getAccountNumber());

        // Retrieve and return transactions for the account number
        return transactionRepository.findByAccountNumber(account.getAccountNumber());
	}

	@Override
	public Transaction logTransaction(Transaction transaction) {
		System.out.println("Logging transaction: " + transaction);
		return transactionRepository.save(transaction);
	}

}
