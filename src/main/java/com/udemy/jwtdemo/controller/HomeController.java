package com.udemy.jwtdemo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
	
	Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	@GetMapping("/hello")
	public String sayHello() {
		logger.info("sayHello");
		return "Hello from HomeController";
	}

}