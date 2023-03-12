package com.piseth.java.school.phoneshop.config.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthUserService implements UserDetailsService{
	private final ApplicationUserService applicationUserService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return applicationUserService.loadUserByUsername(username)
			.orElseThrow(() -> new UsernameNotFoundException("User [%s] is not found".formatted(username)));
	}

}
