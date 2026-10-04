package com.security.service;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtService {
	private final String Key="adasvdewefwef1231231weyewfvfwyr472ewfhfvyeeefv3y";
	private final SecretKey security=Keys.hmacShaKeyFor(Key.getBytes());
	
	public String generateToken(UserDetails userDetails) {
	String role=userDetails.getAuthorities().stream().findFirst().map(GrantedAuthority :: getAuthority).orElse("User_Role");
	
	return Jwts.builder()
			.subject(userDetails.getUsername())
			.claim("role", role) 
			.issuedAt(new Date(System.currentTimeMillis()))
			.expiration(new Date(System.currentTimeMillis()+1000 *60 *60))
			.signWith(security)
			.compact();
	}
	
	public Claims extractAllClaims(String token) {
		return Jwts.parser()
				.verifyWith(security)
				.build()
				.parseSignedClaims(token)
				.getPayload();
	}
	
	public String extractUserName(String token) {
	return extractAllClaims(token).getSubject();
		
	}
	public String extractRole(String token) {
	return extractAllClaims(token).get("role", String.class);
		
	}
	
}
