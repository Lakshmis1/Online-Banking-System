package com.poc.account_management_service.exception;

public class UnauthorizedAccessException extends RuntimeException{
	
	public UnauthorizedAccessException(String message) {
        super(message);
    }

}
