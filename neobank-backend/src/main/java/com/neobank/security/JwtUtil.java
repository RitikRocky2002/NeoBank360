package com.neobank.security;

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

	public String generateToken(String email) {
		return Jwts.builder().subject(email).issuedAt(new Date())
				.expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24)).signWith(getSignKey())
				.compact();
	}

	public String extractEmail(String token) {
		return Jwts.parser().verifyWith(getSignKey()).build().parseSignedClaims(token).getPayload().getSubject();
	}
}