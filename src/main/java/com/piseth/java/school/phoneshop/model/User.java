package com.piseth.java.school.phoneshop.model;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "users")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String lastName;
	private String firstName;
	private String username;
	private String password;
	private Boolean isAccountNonExpired;
	private Boolean isAccountNonLocked;
	private Boolean isCredentialsNonExpired;
	private Boolean isEnabled;
	
	@ManyToMany(fetch = FetchType.EAGER)
	private Set<Role> roles;
}
