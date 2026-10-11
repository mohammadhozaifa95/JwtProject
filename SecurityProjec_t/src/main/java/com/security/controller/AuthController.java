package com.security.controller;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.security.dto.LoginRequest;
import com.security.dto.LoginResponce;
import com.security.dto.RefreshTokenRequest;
import com.security.dto.RefreshTokenResponce;
import com.security.service.JwtService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class AuthController {

	private final AuthenticationManager authentication;
	private final JwtService jwtService;
   private final UserDetailsService userDetailsService;

	@PostMapping("/auth/login")
	public LoginResponce login(@RequestBody LoginRequest loginRequest) {
		Authentication authenticate = authentication.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.userName(), loginRequest.password()));

		UserDetails userDetails = (UserDetails) authenticate.getPrincipal();
		String accessToken = jwtService.generateAcessToken(userDetails);
		String refreshToken = jwtService.generateRefreshToken(userDetails);

		return new LoginResponce(accessToken, refreshToken, userDetails.getUsername());

	}

	@PostMapping("/auth/refresh")
	public RefreshTokenResponce generateRefreshToken(@RequestBody RefreshTokenRequest request) {
		String refreshToken = request.refreshToken();
		String tokenType = jwtService.extractTokenType(refreshToken);
		if (!"REFRESH".equals(tokenType)) {
			new RuntimeException("TokenType Is Not Valid");

		}
		if (jwtService.isTokenExpired(refreshToken)) {
			new RuntimeException("Token Expired");
		}

		String userName = jwtService.extractUserName(refreshToken);
		UserDetails userDetails = userDetailsService.loadUserByUsername(userName);
		String acessToken = jwtService.generateAcessToken(userDetails);
		String refreshToken2 = jwtService.generateRefreshToken(userDetails);

		return new RefreshTokenResponce(acessToken, refreshToken2);

	}

}
