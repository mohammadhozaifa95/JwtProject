package com.security.controller;

import org.jspecify.annotations.Nullable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.security.dto.LoginRequest;
import com.security.dto.LoginResponce;
import com.security.service.JwtService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class AuthController {

	private final AuthenticationManager authentication;
	private final JwtService jwtService;
	
	@PostMapping("/auth/login")
	public LoginResponce login(@RequestBody LoginRequest loginRequest) {
		Authentication authenticate = authentication.
		authenticate(new UsernamePasswordAuthenticationToken(loginRequest.userName(),loginRequest.password()));
	   
		UserDetails userDetails = (UserDetails) authenticate.getPrincipal();
		String token = jwtService.generateToken(userDetails);
		return new LoginResponce(token,userDetails.getUsername());
		
	}

}
