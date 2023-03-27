package com.piseth.java.school.phoneshop.service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import com.piseth.java.school.phoneshop.config.security.ApplicationUserService;
import com.piseth.java.school.phoneshop.config.security.AuthUser;
import com.piseth.java.school.phoneshop.exception.ApiException;
import com.piseth.java.school.phoneshop.mapper.UserMapper;
import com.piseth.java.school.phoneshop.model.Role;
import com.piseth.java.school.phoneshop.model.User;
import com.piseth.java.school.phoneshop.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Primary
@Service
@RequiredArgsConstructor
public class UserService implements ApplicationUserService{
	private final UserRepository userRepository;

	@Override
	public Optional<AuthUser> loadUserByUsername(String username) {
		User user = userRepository.findByUsername(username)
			.orElseThrow(() -> new ApiException(HttpStatus.BAD_REQUEST, "User not found %s".formatted(username)));
		AuthUser authUser = UserMapper.INSTANCE.toAuthUser(user);
		
		Set<SimpleGrantedAuthority> authorities = user.getRoles()
			.stream()
			.flatMap(role -> toStreamOfSimpleGrantedAuthority(role))
			.collect(Collectors.toSet());
		authUser.setGrantedAuthorities(authorities);
		return Optional.ofNullable(authUser);
	}
	
	private Stream<SimpleGrantedAuthority> toStreamOfSimpleGrantedAuthority(Role role){
		Set<SimpleGrantedAuthority> permissions = role.getPermissions().stream()
				.map(p -> new SimpleGrantedAuthority(p.getName()))
				.collect(Collectors.toSet());
			permissions.add(new SimpleGrantedAuthority("ROLE_"+role.getName()));
			return permissions.stream();
	}

}
