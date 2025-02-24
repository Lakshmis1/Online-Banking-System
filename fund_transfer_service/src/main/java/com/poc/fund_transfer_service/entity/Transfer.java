package com.poc.fund_transfer_service.entity;

import java.time.LocalDate;
//import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Transfer {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Long fromAccount;
    private Long toAccount;
    private Double amountToTransfer;
    private LocalDate transferDate;
    
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getFromAccount() {
		return fromAccount;
	}
	public void setFromAccount(Long fromAccount) {
		this.fromAccount = fromAccount;
	}
	public Long getToAccount() {
		return toAccount;
	}
	public void setToAccount(Long toAccount) {
		this.toAccount = toAccount;
	}
	public Double getAmountToTransfer() {
		return amountToTransfer;
	}
	public void setAmountToTransfer(Double amountToTransfer) {
		this.amountToTransfer = amountToTransfer;
	}
	public LocalDate getTransferDate() {
		return transferDate;
	}
	public void setTransferDate(LocalDate transferDate) {
		this.transferDate = transferDate;
	}
    
    
    
    

}
