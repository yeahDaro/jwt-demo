package com.udemy.jwtdemo.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "USER_TABLE")
public class UserEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name = "USER_NAME", nullable = false)
	private String username;
	
	private String password;
	
	private String firstName;
	
	private String lastName;
	
	private String email;
	
	private String phone;
	
	private String profile;
	
	@JsonManagedReference // helps avoid circular dependency in bidirectional mapping
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(name = "USER_ROLE", 
			   joinColumns = {@JoinColumn(name = "user_id")},
			   inverseJoinColumns = {@JoinColumn(name = "role_id")})
	private Set<RoleEntity> roles = new HashSet<RoleEntity>();

	public UserEntity() {}
	
	@Override
	public String toString() {
		return "UserEntity ["
				+ "id=" + id + ", "
				+ "username=" + username + ", "
				+ "password=" + password + ", "
				+ "firstName=" + firstName + ", "
				+ "lastName=" + lastName + ", "
				+ "email=" + email + ", "
				+ "phone=" + phone + ", "
				+ "profile=" + profile + ", "
				+ "roles=" + roles + "]";
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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
	
	public Set<RoleEntity> getRoles() {
		return roles;
	}

	public void setRoles(Set<RoleEntity> roles) {
		this.roles = roles;
	}

	public String getProfile() {
		return profile;
	}

	public void setProfile(String profile) {
		this.profile = profile;
	}

}