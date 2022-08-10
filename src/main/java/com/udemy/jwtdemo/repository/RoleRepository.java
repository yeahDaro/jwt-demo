package com.udemy.jwtdemo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.udemy.jwtdemo.entity.RoleEntity;

public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
	
	public RoleEntity findByRoleName(String roleName);
	
}