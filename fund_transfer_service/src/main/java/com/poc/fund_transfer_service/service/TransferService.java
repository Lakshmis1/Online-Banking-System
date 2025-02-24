package com.poc.fund_transfer_service.service;

import java.util.List;

import com.poc.fund_transfer_service.entity.Transfer;

public interface TransferService {
	
	void transferFunds(Transfer transfer, String token);
	//List<Transfer> getTransferHistory(Long accountId);

}
