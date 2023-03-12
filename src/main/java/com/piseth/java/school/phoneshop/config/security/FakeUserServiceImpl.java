package com.piseth.java.school.phoneshop.config.security;

import java.util.List;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FakeUserServiceImpl implements ApplicationUserService{
	private final PasswordEncoder passwordEncoder;

	@Override
	public Optional<AuthUser> loadUserByUsername(String username) {
		return getAuthUsers().stream()
			.filter(user -> user.getUsername().equals(username))
			.findFirst();
	}
	
	private List<AuthUser> getAuthUsers(){
		AuthUser dara = new AuthUser("mesa", passwordEncoder.encode("mesa"),RoleEnum.SALE.getAuthorities(),  true, true, true, true);
		AuthUser thida = new AuthUser("thida", passwordEncoder.encode("thida"),RoleEnum.ADMIN.getAuthorities(),  true, true, true, true);
		return List.of(dara, thida);
	}

}
