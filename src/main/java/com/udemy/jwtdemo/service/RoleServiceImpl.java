package com.udemy.jwtdemo.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.udemy.jwtdemo.entity.RoleEntity;
import com.udemy.jwtdemo.repository.RoleRepository;

@Service
public class RoleServiceImpl implements RoleService {
	
	Logger logger = LoggerFactory.getLogger(RoleServiceImpl.class);
	
	@Autowired
	private RoleRepository roleRepository;

	@Override
	public RoleEntity create(RoleEntity roleEntity) {
		logger.info("create");
		return roleRepository.save(roleEntity);
	}

	@Override
	public List<RoleEntity> getAll() {
		logger.info("getAll");
		return roleRepository.findAll();
	}

	@Override
	public RoleEntity getById(Long id) {
		logger.info("getById");
		return roleRepository.findById(id).orElse(null);
	}

	@Override
	public void delete(Long id) {
		logger.info("delete");
		RoleEntity role = getById(id);
		if (role!=null) {
			roleRepository.delete(role);
		}
	}

	@Override
	public RoleEntity findByRoleName(String roleName) {
		logger.info("findByRoleName");
		return roleRepository.findByRoleName(roleName);
	}

}