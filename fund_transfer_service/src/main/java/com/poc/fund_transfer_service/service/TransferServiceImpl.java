package com.poc.fund_transfer_service.service;

//import java.util.Date;
//import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poc.fund_transfer_service.client.AccountClient;
import com.poc.fund_transfer_service.client.TransactionClient;
import com.poc.fund_transfer_service.entity.Account;
import com.poc.fund_transfer_service.entity.Transaction;
import com.poc.fund_transfer_service.entity.Transfer;
import com.poc.fund_transfer_service.exception.InsufficientBalanceException;
import com.poc.fund_transfer_service.exception.UnauthorizedAccessException;
import com.poc.fund_transfer_service.repository.TransferRepository;
import com.poc.fund_transfer_service.security.JwtUtil;

import jakarta.transaction.Transactional;


@Service
public class TransferServiceImpl implements TransferService{

	@Autowired 
	private TransferRepository transferRepository;
	
    @Autowired
    private AccountClient accountClient;
    
    @Autowired
    private TransactionClient transactionClient;
    
    @Autowired 
    private JwtUtil jwtUtil;
    
    private final ReentrantLock lock1=new ReentrantLock();
    private final ReentrantLock lock2=new ReentrantLock();

    @Transactional
    public void transferFunds(Transfer transfer, String token) {
        // Fetch account details
    	String username = jwtUtil.extractUsername(token);
    	
    	String authorizationHeader = "Bearer " + token;
    
        Account fromAccount = accountClient.getAccountByAccountNumber(transfer.getFromAccount(), authorizationHeader);
        
        if (!fromAccount.getUsername().equals(username)) {
            throw new UnauthorizedAccessException("You are not authorized to transfer funds from this account");
        }
        
        Account toAccount = accountClient.getAccountByAccountNumber(transfer.getToAccount(), authorizationHeader);

        // to validate sufficient balance
        if (fromAccount.getBalance() < transfer.getAmountToTransfer()) {
            throw new InsufficientBalanceException("Insufficient balance in the account");
        }
        
        ReentrantLock firstLock= fromAccount.getAccountNumber()< toAccount.getAccountNumber()? lock1:lock2;
        ReentrantLock secondLock= fromAccount.getAccountNumber()< toAccount.getAccountNumber()? lock2:lock1;

        try {
        	
        	firstLock.lock();
        	secondLock.lock();
        	
        	// to update balances
        	fromAccount.setBalance(fromAccount.getBalance() - transfer.getAmountToTransfer());
            toAccount.setBalance(toAccount.getBalance() + transfer.getAmountToTransfer());

            // to save updated accounts
            accountClient.updateAccount(fromAccount.getAccountNumber(), fromAccount, authorizationHeader);
            accountClient.updateAccount(toAccount.getAccountNumber(), toAccount, authorizationHeader);
            
            transferRepository.save(transfer);
            
         // Log transaction for the sender (DEBIT)
            Transaction senderTransaction = new Transaction();
            senderTransaction.setAccountNumber(fromAccount.getAccountNumber());
            senderTransaction.setType("DEBIT");
            senderTransaction.setAmount(transfer.getAmountToTransfer());
            senderTransaction.setDescription("Transferred to account: " + toAccount.getAccountNumber());
            Transaction savedSenderTransaction = transactionClient.logTransaction(senderTransaction);
            System.out.println("Logged sender transaction: " + savedSenderTransaction);


            // Log transaction for the receiver (CREDIT)
            Transaction receiverTransaction = new Transaction();
            receiverTransaction.setAccountNumber(toAccount.getAccountNumber());
            receiverTransaction.setType("CREDIT");
            receiverTransaction.setAmount(transfer.getAmountToTransfer());
            receiverTransaction.setDescription("Received from account: " + fromAccount.getAccountNumber());
            Transaction savedReceiverTransaction = transactionClient.logTransaction(receiverTransaction);
            System.out.println("Logged receiver transaction: " + savedReceiverTransaction);
        }
        finally {
        	
        	firstLock.unlock();
        	secondLock.unlock();
        }
        
        
    }

	

}
