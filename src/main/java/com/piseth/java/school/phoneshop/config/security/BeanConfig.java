package com.piseth.java.school.phoneshop.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;

import com.piseth.java.school.phoneshop.config.security.jwt.JwtLoginFilter;

@Configuration
public class BeanConfig {
	@Autowired
	private AuthenticationConfiguration authenticationConfiguration;
	@Bean
	AuthenticationManager authenticationManager() throws Exception {
	    return authenticationConfiguration.getAuthenticationManager();
	}
	
	/*
	 * @Bean
	 * 
	 * @Lazy JwtLoginFilter getJwtLoginFilter(AuthenticationManager
	 * authenticationManager) { return new JwtLoginFilter(authenticationManager); }
	 */
}
