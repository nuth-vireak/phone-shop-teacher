package com.piseth.java.school.phoneshop.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class UserServiceTest {
 
	public static void main(String[] args) {
		PasswordEncoder encoder = new BCryptPasswordEncoder();
		String encode = encoder.encode("mypassword");
		System.out.println(encode);
		
		String pwd = "$2a$10$ajNpE/oOH2y1V5hDgSR26OOoCkLCcdqUXMVgfA57rZ1A/R2bIkwJa";
		boolean matches = encoder.matches(encode, pwd);
		System.out.println(matches);
	}
}
