package com.ecommerce.security;

import java.util.Date;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {

    private static final String SECRET_KEY = "mysecretkeymysecretkeymysecretkey"; 
    private static final long EXPIRATION_TIME = 1000 * 60 * 60; // 1 hour

    private final java.security.Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());

    // Generate Token
    public String generateToken(String email, String role) {

        return Jwts.builder()
                .setSubject(email)
                .claim("role", role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    // Extract Email
    public String extractEmail(String token) {
        return getClaims(token).getSubject();
    }

    // Extract Role
    public String extractRole(String token) {
        return getClaims(token).get("role", String.class);
    }

    // Validate Token
    public boolean validateToken(String token, String email) {

        final String extractedEmail = extractEmail(token);

        return (extractedEmail.equals(email) && !isTokenExpired(token));
    }

    // Check Expiration
    private boolean isTokenExpired(String token) {
        return getClaims(token).getExpiration().before(new Date());
    }

    // Get Claims
    private Claims getClaims(String token) {

        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}