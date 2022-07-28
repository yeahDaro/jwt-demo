package com.udemy.jwtdemo.bean;

import org.springframework.security.core.GrantedAuthority;

public class CustomPermission implements GrantedAuthority {

	private static final long serialVersionUID = 7312429871604165544L;
	
	private String permission;

	public CustomPermission(String permission) {
		this.permission = permission;
	}

	@Override
	public String getAuthority() {
		return permission;
	}

}