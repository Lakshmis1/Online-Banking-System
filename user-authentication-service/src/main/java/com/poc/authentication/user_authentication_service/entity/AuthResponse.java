package com.poc.authentication.user_authentication_service.entity;


public class AuthResponse {
	private String jwtToken;
	private String username;
	
	private AuthResponse(Builder builder) {
		this.jwtToken=builder.jwtToken;
		this.username=builder.username;
		
	}

	public String getJwtToken() {
		return jwtToken;
	}

	public String getUsername() {
		return username;
	}

	public static class Builder{
		private String jwtToken;
		private String username;
		
		public Builder() {
			
		}
		public Builder jwtToken(String jwtToken) {
			this.jwtToken = jwtToken;
			return this;
		}

		public Builder username(String username) {
			this.username = username;
			return this;
		}
		
		public AuthResponse build() {
			return new AuthResponse(this);
		}
		
	}


}
