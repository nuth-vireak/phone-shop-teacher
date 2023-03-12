package com.piseth.java.school.phoneshop.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class UserServiceTest {
 
	public static void main(String[] args) {
		PasswordEncoder encoder = new BCryptPasswordEncoder();
		String encode = encoder.encode("mypassword");
		System.out.println(encode);
	}
}
