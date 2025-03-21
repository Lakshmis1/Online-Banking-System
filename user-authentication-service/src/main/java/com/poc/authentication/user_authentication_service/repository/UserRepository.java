package com.poc.authentication.user_authentication_service.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.poc.authentication.user_authentication_service.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
	

}
