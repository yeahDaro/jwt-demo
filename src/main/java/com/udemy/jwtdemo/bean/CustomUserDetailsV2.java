package com.udemy.jwtdemo.bean;

import java.util.List;

import org.springframework.security.core.userdetails.User;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class CustomUserDetailsV2 extends User {
	
	private static final long serialVersionUID = -8086920393701023493L;
	
	private Long id;
	private String firstName;
	private String lastName;
	private String email;
	private String phone;
	private String profile;
	
	public CustomUserDetailsV2(String username, String password, String profile, List<CustomPermission> authorities) {
		super(username, password, authorities);
		this.profile = profile;
	}

	@Override
	public String toString() {
		return "CustomUserDetailsV2 ["
				+ "id=" + id + ", "
				+ "username=" + super.getUsername() + ", "
				+ "password=" + super.getPassword() + ", "
				+ "firstName=" + firstName + ", "
				+ "lastName=" + lastName + ", "
				+ "email=" + email + ", "
				+ "phone=" + phone + ", "
				+ "profile=" + profile + "]";
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getProfile() {
		return profile;
	}

	public void setProfile(String profile) {
		this.profile = profile;
	}
	
}