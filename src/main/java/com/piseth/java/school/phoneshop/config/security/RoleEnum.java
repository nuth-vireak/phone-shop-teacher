package com.piseth.java.school.phoneshop.config.security;

import static com.piseth.java.school.phoneshop.config.security.PermissionEnum.BRAND_READ;
import static com.piseth.java.school.phoneshop.config.security.PermissionEnum.BRAND_WRITE;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum RoleEnum {
	ADMIN(Set.of(BRAND_READ, BRAND_WRITE)),
	SALE(Set.of(BRAND_READ));
	
	private Set<PermissionEnum> permissions;
	
	public Set<SimpleGrantedAuthority> getAuthorities(){
		Set<SimpleGrantedAuthority> permissions = this.getPermissions().stream()
			.map(permission -> new SimpleGrantedAuthority(permission.getDescription()))
			.collect(Collectors.toSet());
		//
		SimpleGrantedAuthority role = new SimpleGrantedAuthority("ROLE_"+ this.name());
		permissions.add(role);
		//
		
		return permissions;
	}
}
