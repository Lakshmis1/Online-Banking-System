package com.poc.account_management_service.service;

import com.poc.account_management_service.entity.Account;

public interface AccountService {
	
	Account getAccountByUsername(String username, String token);
	Account getAccountByAccountNumber(Long accountNumber, String token);
	Account updateAccount(Long accountNumber, Account account, String token);
	Account createAccount(Account account, String token);
	void deleteAccount(Long accountNumber, String token);

}
