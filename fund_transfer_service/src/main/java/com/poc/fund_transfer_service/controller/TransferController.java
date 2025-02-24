package com.poc.fund_transfer_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.poc.fund_transfer_service.entity.Transfer;
import com.poc.fund_transfer_service.service.TransferService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/transfer")
public class TransferController {

    @Autowired
    private TransferService fundTransferService;

    @PostMapping
    public ResponseEntity<String> transferFunds(@RequestBody Transfer transfer, @RequestHeader("Authorization") String authorizationHeader) {

        String token = authorizationHeader.startsWith("Bearer ") 
                ? authorizationHeader.substring(7) 
                : authorizationHeader;
        
        fundTransferService.transferFunds(transfer, token);
        return ResponseEntity.ok("Fund transfer successful!");
    }
}
