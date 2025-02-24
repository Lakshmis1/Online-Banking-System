package com.poc.authentication.user_authentication_service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.poc.authentication.user_authentication_service.dto.UserDto;
import com.poc.authentication.user_authentication_service.entity.User;
import com.poc.authentication.user_authentication_service.exception.UserNotFoundException;
import com.poc.authentication.user_authentication_service.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService, UserDetailsService{
	
	@Autowired
    private UserRepository userRepository;
 
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    public UserServiceImpl(@Lazy PasswordEncoder passwordEncoder) {
		this.passwordEncoder=passwordEncoder;
	}
    
    @Override
    public User registerUser(User user) {
        // Check if the username already exists
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new IllegalArgumentException("Username is already taken!");
        }

        // Hash the password
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Set default role if not provided
        if (user.getRole() == null || user.getRole().isEmpty()) {
            user.setRole("USER"); // Default role
        }

        // Save the user
        return userRepository.save(user);
    }


	@Override
	public String authenticateUser(String username, String password) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        if (passwordEncoder.matches(password, user.getPassword())) {
            return "Authentication successful for user: " + username;
        } else {
            throw new BadCredentialsException("Invalid credentials");
        }
    }

	@Override
	public UserDetails loadUserByUsername(String username) {
		User user=userRepository.findByUsername(username).orElse(null);
		if(user==null) {
			throw new UserNotFoundException ("user not found with username "+username);
		}
		return org.springframework.security.core.userdetails.User
				.withUsername(user.getUsername())
				.password(user.getPassword())
				.accountExpired(false)
				.accountLocked(false)
				.credentialsExpired(false)
				.disabled(false)
				.build();
	}

	@Override
	public UserDto getUserByUsername(String username) {
		User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found with username: " + username));
        
        UserDto userDto = new UserDto();
        userDto.setUsername(user.getUsername());
        userDto.setEmail(user.getEmail());
        userDto.setRole(user.getRole());

        return userDto;
	}
}
