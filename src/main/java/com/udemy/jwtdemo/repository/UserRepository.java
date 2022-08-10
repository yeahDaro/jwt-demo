package com.udemy.jwtdemo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.udemy.jwtdemo.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
	
	public UserEntity findByUsername(String username);
	
}