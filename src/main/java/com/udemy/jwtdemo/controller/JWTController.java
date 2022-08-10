package com.udemy.jwtdemo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.udemy.jwtdemo.bean.CustomUserDetailsV2;
import com.udemy.jwtdemo.model.JWTRequest;
import com.udemy.jwtdemo.model.JWTResponse;
import com.udemy.jwtdemo.service.CustomUserDetailsService;
import com.udemy.jwtdemo.util.JWTUtil;

@RestController
@RequestMapping(path = "/api")
public class JWTController {
	
	Logger logger = LoggerFactory.getLogger(JWTController.class);
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private CustomUserDetailsService customUserDetailsService;
	
	@Autowired
	private JWTUtil jwtUtil;
	
	@PostMapping("/login")
	public ResponseEntity<?> generateToken(@RequestBody JWTRequest jwtRequest) {
		logger.info("generateToken");
		logger.info(jwtRequest.toString());
		
		// authenticate the user
		UsernamePasswordAuthenticationToken upat = new UsernamePasswordAuthenticationToken(jwtRequest.getUsername(), jwtRequest.getPassword());
		authenticationManager.authenticate(upat);
		
		// CustomUserDetails userDetails = customUserDetailsService.loadUserByUsernameV1(jwtRequest.getUsername());
		// String token = jwtUtil.generateTokenV1(userDetails);
		
		CustomUserDetailsV2 userDetailsV2 = customUserDetailsService.loadUserByUsername(jwtRequest.getUsername());
		String token = jwtUtil.generateTokenV2(userDetailsV2);
		
		JWTResponse jwtResponse = new JWTResponse(token);
		logger.info(jwtResponse.toString());
		
		return new ResponseEntity<JWTResponse>(jwtResponse, HttpStatus.OK);
	}

}