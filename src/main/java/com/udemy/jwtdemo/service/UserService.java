package com.udemy.jwtdemo.service;

import com.udemy.jwtdemo.entity.UserEntity;
import com.udemy.jwtdemo.model.UserModel;

public interface UserService {
	
	public UserEntity findByUsername(String username);
	
	public UserEntity create(UserModel userModel);
	
}