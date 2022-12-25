package com.piseth.java.school.phoneshop.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.Setter;

@SuppressWarnings("serial")
@Getter
@Setter
public class ResourceNotFoundException extends ApiException{
	
	private String resourceName;
	private Long resourceId;
	
	public ResourceNotFoundException(String resourceName, Long resourceId) {
		super(HttpStatus.NOT_FOUND, String.format("%s not found for id=%d", resourceName, resourceId));
	}
	

}
