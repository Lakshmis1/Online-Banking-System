package com.poc.account_management_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.poc.account_management_service.entity.Account;
import com.poc.account_management_service.service.AccountService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/accounts")
public class AccountController {
    @Autowired
    private AccountService accountService;

    @PostMapping
    public ResponseEntity<Account> createAccount(@RequestBody Account account, @RequestHeader("Authorization") String authorizationHeader) {

        // to extract the token from the "Authorization" header
        String token = authorizationHeader.startsWith("Bearer ") 
                ? authorizationHeader.substring(7) 
                : authorizationHeader;

    	Account createdAccount = accountService.createAccount(account, token);
        return ResponseEntity.ok(createdAccount);
    }
    
    @GetMapping("/by-username/{username}")
    public ResponseEntity<Account> getAccountByUsername(@PathVariable String username, 
                                                         @RequestHeader("Authorization") String authorizationHeader) {
        String token = authorizationHeader.startsWith("Bearer ") 
        		? authorizationHeader.substring(7)
        		: authorizationHeader;

        // Call the service with both username and token
        Account account = accountService.getAccountByUsername(username, token);

        return ResponseEntity.ok(account);
    }

    @GetMapping("/by-accountNumber/{accountNumber}")
    public ResponseEntity<Account> getAccountByAccountNumber(@PathVariable Long accountNumber, 
            @RequestHeader("Authorization") String authorizationHeader) {

        String token = authorizationHeader.startsWith("Bearer ")
                ? authorizationHeader.substring(7)
                : authorizationHeader; 
        Account account = accountService.getAccountByAccountNumber(accountNumber, token);
        return ResponseEntity.ok(account);    
   }

    @PutMapping("/{accountNumber}")
    public ResponseEntity<Account> updateAccount(@PathVariable Long accountNumber, @RequestBody Account account,
    		                                     @RequestHeader("Authorization") String authorizationHeader) {
    	String token = authorizationHeader.startsWith("Bearer ")
                ? authorizationHeader.substring(7)
                : authorizationHeader; 
    	Account updatedAccount = accountService.updateAccount(accountNumber, account, token);
        return ResponseEntity.ok(updatedAccount);
    }
    
    @DeleteMapping("/{accountNumber}")
    public ResponseEntity<?> deleteAccount(@PathVariable Long accountNumber,@RequestHeader("Authorization") String authorizationHeader) {

        String token = authorizationHeader.startsWith("Bearer ")
                ? authorizationHeader.substring(7)
                : authorizationHeader;
    	accountService.deleteAccount(accountNumber, token);
    	return ResponseEntity.ok("Account with account number "+accountNumber+" is deleted.");
    }
}
