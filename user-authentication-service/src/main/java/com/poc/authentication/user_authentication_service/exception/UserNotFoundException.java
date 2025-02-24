package com.poc.authentication.user_authentication_service.exception;

public class UserNotFoundException extends RuntimeException{
	
	public UserNotFoundException(String message) {
		super(message);
	}
}
