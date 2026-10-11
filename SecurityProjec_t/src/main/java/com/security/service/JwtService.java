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

    
	private final String Key = "adasvdewefwef1231231weyewfvfwyr472ewfhfvyeeefv3y";
	private final SecretKey security = Keys.hmacShaKeyFor(Key.getBytes());

	public String generateAcessToken(UserDetails userDetails) {
		String role = userDetails.getAuthorities().stream().findFirst().map(GrantedAuthority::getAuthority)
				.orElse("User_Role");

		return Jwts.builder().subject(userDetails.getUsername()).claim("role", role)
				.claim("type", "ACESS")
				.issuedAt(new Date(System.currentTimeMillis()))
				.expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 1)).signWith(security).compact();
	}

	public String generateRefreshToken(UserDetails userDetails) {
		return Jwts.builder()
		     .subject(userDetails.getUsername())
		     .claim("type", "REFRESH")
		     .issuedAt(new Date(System.currentTimeMillis()))
		     .expiration(new Date(System.currentTimeMillis()+1000 * 60 *60 * 10))
		     .signWith(security)
		     .compact();
		  
		   
		
	}
	
	public Claims extractAllClaims(String token) {
		return Jwts.parser().verifyWith(security).build().parseSignedClaims(token).getPayload();
	}

	public String extractUserName(String token) {
		return extractAllClaims(token).getSubject();

	}

	public String extractRole(String token) {
		return extractAllClaims(token).get("role", String.class);

	}

	public boolean isTokenExpired(String token) {

		return extractAllClaims(token).getExpiration().before(new Date());
	}

	public String extractTokenType(String token) {
		return extractAllClaims(token).get("type", String.class);

	}
	public boolean isTokenValid(UserDetails userDetails,String tokrn) {
		String userName = extractUserName(tokrn);
		return userName.equals(userDetails.getUsername()) && ! isTokenExpired(tokrn);
		
	}

}
