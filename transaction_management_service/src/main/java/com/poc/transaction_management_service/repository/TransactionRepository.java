package com.poc.transaction_management_service.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.poc.transaction_management_service.entity.Transaction;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long>{
	
	List<Transaction> findByAccountNumber(Long accountNumber);

}
