package com.poc.account_management_service.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.poc.account_management_service.entity.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account,Long>{

	 Optional<Account> findByAccountNumber(Long accountNumber);
	 void deleteByAccountNumber(Long accountNumber);
	 Optional<Account> findByUsername(String username);

}
