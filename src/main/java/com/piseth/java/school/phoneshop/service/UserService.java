package com.piseth.java.school.phoneshop.service;

import java.util.Optional;

import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.piseth.java.school.phoneshop.config.security.ApplicationUserService;
import com.piseth.java.school.phoneshop.config.security.AuthUser;
import com.piseth.java.school.phoneshop.exception.ApiException;
import com.piseth.java.school.phoneshop.exception.ResourceNotFoundException;
import com.piseth.java.school.phoneshop.mapper.UserMapper;
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
		authUser.setGrantedAuthorities(user.getRole().getAuthorities());
		return Optional.ofNullable(authUser);
	}

}
