package com.poc.transaction_management_service.service;

import java.util.List;

import com.poc.transaction_management_service.entity.Transaction;

public interface TransactionService {
	
	List<Transaction> getTransactionsForLoggedInUser(String token);
    Transaction logTransaction(Transaction transaction);

}
