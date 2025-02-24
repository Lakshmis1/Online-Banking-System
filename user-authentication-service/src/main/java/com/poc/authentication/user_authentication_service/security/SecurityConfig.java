package com.poc.authentication.user_authentication_service.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;


@Component
@EnableWebSecurity
public class SecurityConfig{
	
	@Autowired
	private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
	
	@Autowired
	private JwtAuthenticationFilter jwtAuthenticationFilter;
	
	 @Bean
	    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

	        http.csrf(csrf -> csrf.disable())
	                .authorizeRequests()
	                .requestMatchers("/accounts/**").authenticated()
	                .requestMatchers("/h2-console/**").permitAll()
	                .requestMatchers("/auth/register","/auth/login","/auth/user/{username}").permitAll()
	                .anyRequest()
	                .authenticated()
	                .and().exceptionHandling(ex -> ex.authenticationEntryPoint(jwtAuthenticationEntryPoint))
	                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
	        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
	        return http.build();
	    }
	 
	 @Bean
	 public PasswordEncoder passwordEncoder() {
		 return new BCryptPasswordEncoder();
	 }
	 
	 @Bean
	    public AuthenticationManager authenticationManager(AuthenticationConfiguration builder) throws Exception {
	        return builder.getAuthenticationManager();
	    }
}
