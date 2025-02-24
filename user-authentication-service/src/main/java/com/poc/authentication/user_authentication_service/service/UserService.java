package com.poc.authentication.user_authentication_service.service;

import org.springframework.security.core.userdetails.UserDetails;

import com.poc.authentication.user_authentication_service.dto.UserDto;
import com.poc.authentication.user_authentication_service.entity.User;

public interface UserService {
	
	User registerUser(User user);
	String authenticateUser(String username, String password);
	UserDetails loadUserByUsername(String email);
	UserDto getUserByUsername(String username);

}
