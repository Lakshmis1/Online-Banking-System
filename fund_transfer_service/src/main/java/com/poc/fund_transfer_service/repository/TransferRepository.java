package com.poc.fund_transfer_service.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.poc.fund_transfer_service.entity.Transfer;

@Repository
public interface TransferRepository extends JpaRepository<Transfer, Long> {
    List<Transfer> findByFromAccountOrToAccount(Long fromAccount, Long toAccount);

}
