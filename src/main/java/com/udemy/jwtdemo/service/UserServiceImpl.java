package com.udemy.jwtdemo.service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.udemy.jwtdemo.entity.RoleEntity;
import com.udemy.jwtdemo.entity.UserEntity;
import com.udemy.jwtdemo.model.UserModel;
import com.udemy.jwtdemo.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {
	
	Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Override
	public UserEntity create(UserModel model) {
		logger.info("create");
		UserEntity entity = new UserEntity();
		BeanUtils.copyProperties(model, entity);
		// TODO
//		entity.setUsername(details.getUsername());
//		entity.setPassword(details.getPassword());
//		entity.setFirstName(details.getFirstName());
//		entity.setLastName(details.getLastName());
//		entity.setEmail(details.getEmail());
//		entity.setPhone(details.getPhone());
//		entity.setProfile(details.getProfile());
		entity.setPassword(passwordEncoder.encode(entity.getPassword()));
		entity.setRoles(new HashSet<RoleEntity>());
		String[] array = model.getRoles().split(",");
		List<String> list = Arrays.asList(array);
		list.forEach(roleName -> {
			logger.info("Checking Role: " + roleName);
			RoleEntity roleEntity = roleService.findByRoleName(roleName);
			if (roleEntity==null) {
				logger.info("RoleEntity does not exist -> creating it then adding it");
				roleEntity = new RoleEntity();
				roleEntity.setRoleName(roleName);
				roleEntity = roleService.create(roleEntity);
				logger.info("created! -> " + roleEntity);
				entity.getRoles().add(roleEntity);
			} else {
				logger.info("RoleEntity already exist -> just adding it");
				entity.getRoles().add(roleEntity);
			}
			logger.info("entity.roles.size: " + entity.getRoles().size());
		});
		logger.info("Data before saving into Database: \n" + entity);
		return userRepository.save(entity);
	}

	@Override
	public UserEntity findByUsername(String username) {
		logger.info("findByUsername");
		return userRepository.findByUsername(username);
	}

}