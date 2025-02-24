package com.poc.account_management_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.poc.account_management_service.entity.UserDto;

@FeignClient(name = "user-authentication-service", url = "http://localhost:8880")  
public interface UserClient {

    @GetMapping("/auth/user/{username}")
    UserDto getUserByUsername(@PathVariable("username") String username);
}
