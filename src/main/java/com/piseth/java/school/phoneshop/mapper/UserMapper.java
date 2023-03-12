package com.piseth.java.school.phoneshop.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.piseth.java.school.phoneshop.config.security.AuthUser;
import com.piseth.java.school.phoneshop.model.User;

@Mapper
public interface UserMapper {
	UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);
	
	AuthUser toAuthUser(User user);
}
