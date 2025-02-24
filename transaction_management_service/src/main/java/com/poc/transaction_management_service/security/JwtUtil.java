package com.poc.transaction_management_service.security;

import java.util.Date;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

@Component
public class JwtUtil {
	
	// Secret key for JWT signing and verification
    private String secret = "afafasfafafasfasfasfafacasdasfasxASFACASDFACASDFASFASFDAFASFASDAADSCSDFADCVSGCFVADXCcadwavfsfarvf";

    // Extract username from JWT token
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // Extract expiration date from JWT token
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    // Extract claim (custom claims if any)
    public <T> T extractClaim(String token, ClaimsResolver<T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.resolve(claims);
    }

    // Extract all claims from the token
//    private Claims extractAllClaims(String token) {
//        return Jwts.parser()
//                .setSigningKey(secretKey)
//                .parseClaimsJws(token)
//                .getBody();
//    }
    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(secret).build().parseClaimsJws(token).getBody();
    }

    // Check if the token is expired
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    // Validate the JWT token
    public boolean validateToken(String token, String username) {
        return (username.equals(extractUsername(token)) && !isTokenExpired(token));
    }

    // Functional interface to resolve custom claims
    public interface ClaimsResolver<T> {
        T resolve(Claims claims);
    }

}
