package com.udemy.jwtdemo.bean;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;


public class CustomUserDetails extends User {

	private static final long serialVersionUID = -8969369187661311119L;
	
	private String profile;
	
	public CustomUserDetails(String username, String password, String profile, Collection<? extends GrantedAuthority> authorities) {
		super(username, password, authorities);
		this.profile = profile;
	}

	public String getProfile() {
		return profile;
	}
	
}