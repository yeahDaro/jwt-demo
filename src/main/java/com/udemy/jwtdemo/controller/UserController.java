package com.udemy.jwtdemo.controller;

import java.security.Principal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.udemy.jwtdemo.bean.CustomUserDetailsV2;
import com.udemy.jwtdemo.entity.UserEntity;
import com.udemy.jwtdemo.model.UserModel;
import com.udemy.jwtdemo.service.CustomUserDetailsService;
import com.udemy.jwtdemo.service.UserService;

@RestController
@RequestMapping("/api")
public class UserController {
	
	Logger logger = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private CustomUserDetailsService customUserDetailsService;
	
	@PostMapping("/register")
	public UserEntity register(@RequestBody UserModel userModel) {
		logger.info("logger");
		logger.info("Data coming as part of the request: \n" + userModel);
		return userService.create(userModel);
	}
	
	@GetMapping("/current-user")
	public CustomUserDetailsV2 getCurrentUser(Principal principal) {
		logger.info("getCurrentUser");
		return customUserDetailsService.loadUserByUsername(principal.getName());
	}

}