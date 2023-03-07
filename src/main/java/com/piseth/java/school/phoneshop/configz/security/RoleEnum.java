package com.piseth.java.school.phoneshop.configz.security;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import lombok.Getter;

import static com.piseth.java.school.phoneshop.configz.security.PermissionEnum.*;

@Getter
public enum RoleEnum {
	ADMIN(Set.of(BRAND_READ, BRAND_WRITE)),
	SALE(Set.of(BRAND_READ));
	
	private Set<PermissionEnum> permissions;
	
	private RoleEnum(Set<PermissionEnum> permissions) {
		this.permissions = permissions;
	}
	
	public Set<SimpleGrantedAuthority> getAuthorities(){
		Set<SimpleGrantedAuthority> permissions = this.getPermissions().stream()
			.map(permission -> new SimpleGrantedAuthority(permission.getDescription()))
			.collect(Collectors.toSet());
		return permissions;
	}
}
