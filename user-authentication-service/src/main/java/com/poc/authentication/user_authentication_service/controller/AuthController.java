package com.poc.authentication.user_authentication_service.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.poc.authentication.user_authentication_service.dto.UserDto;
import com.poc.authentication.user_authentication_service.entity.AuthRequest;
import com.poc.authentication.user_authentication_service.entity.AuthResponse;
import com.poc.authentication.user_authentication_service.entity.User;
import com.poc.authentication.user_authentication_service.security.JwtUtil;
import com.poc.authentication.user_authentication_service.service.UserService;


@RestController
@RequestMapping("/auth")
public class AuthController {
	
	@Autowired
	private UserService userService;
 
	@Autowired
    private UserDetailsService userDetailsService;

 
    @Autowired
    private JwtUtil jwtUtil;
 
    @Autowired
    private AuthenticationManager authenticationManager;
    
    private Logger logger = LoggerFactory.getLogger(AuthController.class);


    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UserDto userDto) {
    	try {
            // Log the incoming request
            System.out.println("Registering user: " + userDto);

            // Map UserDto to User entity
            User user = new User();
            user.setUsername(userDto.getUsername());
            user.setPassword(userDto.getPassword());
            user.setEmail(userDto.getEmail());
            user.setRole(userDto.getRole());

            userService.registerUser(user);

            return ResponseEntity.ok("User registered successfully!");
        } catch (IllegalArgumentException e) {
            // Log the specific error message
            System.err.println("Validation error: " + e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            // Log the stack trace for debugging
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred during registration.");
        }
    
    }
    
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {

        this.doAuthenticate(request.getUsername(), request.getPassword());


        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
        String token = this.jwtUtil.generateToken(userDetails);

        AuthResponse response = new AuthResponse.Builder()
                .jwtToken(token)
                .username(userDetails.getUsername()).build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    private void doAuthenticate(String email, String password) {

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(email, password);
        try {
        	authenticationManager.authenticate(authentication);


        } catch (BadCredentialsException e) {
            throw new BadCredentialsException(" Invalid Username or Password  !!");
        }

    }

    @ExceptionHandler(BadCredentialsException.class)
    public String exceptionHandler() {
        return "Credentials Invalid !!";
    }
    
    @GetMapping("/user/{username}")
    public ResponseEntity<UserDto> getUserByUsername(@PathVariable String username) {
        try {
            UserDto userDto = userService.getUserByUsername(username);
            return ResponseEntity.ok(userDto);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
    
    

}
