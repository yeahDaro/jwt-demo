package com.udemy.jwtdemo.service;

import java.util.List;

import com.udemy.jwtdemo.entity.RoleEntity;

public interface RoleService {
	
	public RoleEntity create(RoleEntity roleEntity);
	
	public List<RoleEntity> getAll();
	
	public RoleEntity getById(Long id);
	
	public RoleEntity findByRoleName(String roleName);
	
	public void delete(Long id);

}