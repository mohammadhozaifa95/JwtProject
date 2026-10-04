package com.security.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    @Lazy
	private final jwtFilter jwtFilter;
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

		http.csrf(csrf -> csrf.disable())

				.authorizeHttpRequests(auth -> auth
						.requestMatchers("/auth/login", "/register").permitAll()
						.requestMatchers("/update", "/delete").hasRole("ADMIN")
						.anyRequest().authenticated())
				        .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
 		                return http.build();
	}

	

	/*
	 * @Bean public UserDetailsService userDetailsService(PasswordEncoder encoder) {
	 * 
	 * UserDetails admin =
	 * User.builder().username("admin").password(encoder.encode("admin@123")).roles(
	 * "ADMIN") .build();
	 * 
	 * UserDetails user =
	 * User.builder().username("user").password(encoder.encode("user@123")).roles(
	 * "USER").build();
	 * 
	 * return new InMemoryUserDetailsManager(admin, user); }
	 */
	@Bean
	AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) {
		return configuration.getAuthenticationManager();

	}
}