package com.poc.account_management_service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poc.account_management_service.client.UserClient;
import com.poc.account_management_service.entity.Account;
import com.poc.account_management_service.entity.UserDto;
import com.poc.account_management_service.exception.UnauthorizedAccessException;
import com.poc.account_management_service.repository.AccountRepository;
import com.poc.account_management_service.security.JwtUtil;

import jakarta.transaction.Transactional;

@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    private AccountRepository accountRepository;
    
    @Autowired
    private UserClient userClient; 
    
    @Autowired
    private JwtUtil jwtUtil;

    public Account createAccount(Account account, String token) {
        // Extracting username from the JWT token
        String username = jwtUtil.extractUsername(token);

        // Fetching user details using Feign client
        UserDto userDto = userClient.getUserByUsername(username);

        // Setting the email from the user details
        account.setUsername(username);
        account.setEmail(userDto.getEmail());

        return accountRepository.save(account);
    }
    
    public Account getAccountByUsername(String username, String token) {
    	
    	String extractedUsernameFromToken = jwtUtil.extractUsername(token);

        if (!username.equals(extractedUsernameFromToken)) {
            throw new UnauthorizedAccessException("Username in the token does not match the provided username");
        }
        
        Account account = accountRepository.findByUsername(username)
        		.orElseThrow(() -> new RuntimeException("Account not found for username: " + username));
        return account;
    }

    public Account getAccountByAccountNumber(Long accountNumber, String token) {
        validateToken(token);
        return accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new RuntimeException("Account not found"));
    }

    public Account updateAccount(Long accountNumber, Account account, String token) {
        validateToken(token);
        Account existingAccount = getAccountByAccountNumber(accountNumber, token);
        existingAccount.setBalance(account.getBalance());
        return accountRepository.save(existingAccount);
    }
    @Transactional
	public void deleteAccount(Long accountNumber, String token) {
        validateToken(token);
		accountRepository.deleteByAccountNumber(accountNumber);;
		
	}
    
    private void validateToken(String token) {
        String username = jwtUtil.extractUsername(token);
        if (username == null || username.isEmpty()) {
            throw new RuntimeException("Invalid token");
        }
    }

}
