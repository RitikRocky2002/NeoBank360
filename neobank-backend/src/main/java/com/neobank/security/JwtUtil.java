package com.neobank.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtil {

    private final String SECRET = "mysecretkeymysecretkeymysecretkey12345";

    private SecretKey getSignKey() {
        return Keys.hmacShaKeyFor(SECRET.getBytes());
    }

    // ✅ Generate token with ROLE
    public String generateToken(String email, String role) {
        return Jwts.builder()
                .subject(email)
                .claim("role", role)   // 🔥 ADD ROLE
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24))
                .signWith(getSignKey())
                .compact();
    }

    // ✅ Extract email
    public String extractEmail(String token) {
        return extractAllClaims(token).getSubject();
    }

    // ✅ Extract role
    public String extractRole(String token) {
        return extractAllClaims(token).get("role", String.class);
    }

    // ✅ Validate token (expiry check)
    public boolean validateToken(String token) {
        return extractAllClaims(token).getExpiration().after(new Date());
    }

    // 🔥 Common method
    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSignKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}