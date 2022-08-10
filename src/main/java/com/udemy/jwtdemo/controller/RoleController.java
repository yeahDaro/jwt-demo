package com.udemy.jwtdemo.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.udemy.jwtdemo.entity.RoleEntity;
import com.udemy.jwtdemo.service.RoleService;

@RestController
@RequestMapping("/api")
public class RoleController {
	
	Logger logger = LoggerFactory.getLogger(RoleController.class);
	
	@Autowired
	private RoleService roleService;
	
	@PostMapping("/roles")
	public RoleEntity create(@RequestBody RoleEntity role) {
		logger.info("create");
		return roleService.create(role);
	}
	
	@GetMapping("/roles")
	public List<RoleEntity> getAll() {
		logger.info("getAll");
		return roleService.getAll();
	}
	
	@GetMapping("/roles/{id}")
	public RoleEntity getById(@PathVariable Long id) {
		return roleService.getById(id);
	}
	
	@DeleteMapping("/roles/{id}")
	public void delete(@PathVariable Long id) {
		roleService.delete(id);
	}

}