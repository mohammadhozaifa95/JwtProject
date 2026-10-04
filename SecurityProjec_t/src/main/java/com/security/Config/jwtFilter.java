package com.security.Config;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.security.service.JwtService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class jwtFilter  extends OncePerRequestFilter{
	private final JwtService jwtService;
	private final UserDetailsService userDetailsService;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String authHedar=request.getHeader("Authorization");
		
		if (authHedar ==null || !authHedar.startsWith("Bearer ")) {
			filterChain.doFilter(request, response);
			return;
		}
		String token= authHedar.substring(7);
		
		try {
			String userName = jwtService.extractUserName(token);
			if (userName !=null && SecurityContextHolder.getContext().getAuthentication()==null) {
				UserDetails userdetialDetails = userDetailsService.loadUserByUsername(userName);
				UsernamePasswordAuthenticationToken authenticationToken=new
				UsernamePasswordAuthenticationToken(userdetialDetails,null,userdetialDetails.getAuthorities());
				authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(authenticationToken);
			}
		} catch (Exception e) {
		e.printStackTrace();
		}
		filterChain.doFilter(request, response);
	}
}
