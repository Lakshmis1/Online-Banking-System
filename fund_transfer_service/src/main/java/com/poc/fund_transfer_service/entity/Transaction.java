package com.poc.fund_transfer_service.entity;

import java.util.Date;

//import jakarta.persistence.Temporal;
//import jakarta.persistence.TemporalType;

public class Transaction {
	
	private Long accountNumber;  
    private String type;       
    private Double amount;
    private String description;
    
    private Date transactionDate = new Date();
    
	public Long getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(Long accountNumber) {
		this.accountNumber = accountNumber;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Date getTransactionDate() {
		return transactionDate;
	}
	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}

    
}
