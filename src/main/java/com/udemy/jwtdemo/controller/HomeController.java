package com.udemy.jwtdemo.controller;

import java.security.Principal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
	
	Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	//@PreAuthorize("hasAnyRole('ADMIN', 'PROFESOR')")
	@GetMapping("/hello")
	public String sayHello(Principal principal) {
		logger.info("sayHello");
		return "Hello " + principal.getName() + "! hasAnyRole is working";
	}
	
	//@PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_TUTOR', 'ROLE_ESTUDIANTE')")
	@GetMapping("/hello2")
	public String sayHello2(Principal principal) {
		logger.info("sayHello2");
		return "Hello2 " + principal.getName() + "! hasAnyAuthority is working too";
	}

}