package com.piseth.java.school.phoneshop.configz.security;

import lombok.Getter;

@Getter
public enum PermissionEnum {
	BRAND_READ("brand:read"), BRAND_WRITE("brand:write");
	private String description;
	
	private PermissionEnum(String description) {
		this.description = description;
	}
	
	
}
