package com.udemy.jwtdemo.service;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.tomcat.util.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.udemy.jwtdemo.bean.CustomUserDetails;
import com.udemy.jwtdemo.bean.CustomUserDetailsV2;
import com.udemy.jwtdemo.entity.UserEntity;
import com.udemy.jwtdemo.repository.UserRepository;
import com.udemy.jwtdemo.bean.CustomPermission;

@Service
public class CustomUserDetailsService implements UserDetailsService {
	
	Logger logger = LoggerFactory.getLogger(CustomUserDetailsService.class);
	
	@Autowired
	private UserRepository userRepository;
	
	private static HashMap<String, String> validUsers;
	
	static {
		validUsers = new HashMap<String, String>();
		validUsers.put("daro", "ADMIN");
		validUsers.put("tanily", "CLIENT");
	}
	
	/**
	 * Verifies user against in-memory (HashMap)
	 */
	public CustomUserDetails loadUserByUsernameV1(String username) throws UsernameNotFoundException {
		logger.info("loadUserByUsername(" + username + ")");
		Map.Entry<String, String> entry = null;
		for (Map.Entry<String, String> tmp : validUsers.entrySet()) {
			if (tmp.getKey().equals(username)) {
				entry = tmp;
			}
		}
		if (entry!=null) {
			return createUserDetailsV1(entry);
		}
		throw new UsernameNotFoundException("User does not exist");
	}
	
	/**
	 * Verifies user against database (Repository)
	 */
	@Override
	public CustomUserDetailsV2 loadUserByUsername(String username) throws UsernameNotFoundException {
		logger.info("loadUserByUsername(" + username + ")");
		UserEntity userEntity = userRepository.findByUsername(username);
		if (userEntity!=null) {
			return createUserDetailsV2(userEntity);
		}
		throw new UsernameNotFoundException("User does not exist");
	}
	
	private CustomUserDetailsV2 createUserDetailsV2(UserEntity userEntity) {
		logger.info("createUserDetailsV2");
		List<CustomPermission> authorities = new ArrayList<CustomPermission>();
		if(userEntity.getRoles()!=null && !userEntity.getRoles().isEmpty()) {
			logger.info("UserEntity has " + userEntity.getRoles().size() + " Roles");
			userEntity.getRoles().forEach(role -> {
				authorities.add(new CustomPermission(role.getRoleName()));
			});
		} else {
			logger.info("UserEntity does not have Roles");
			authorities.add(new CustomPermission("default"));
		}
		CustomUserDetailsV2 userDetailsV2 = new CustomUserDetailsV2(userEntity.getUsername(), userEntity.getPassword(), userEntity.getProfile(), authorities);
		BeanUtils.copyProperties(userEntity, userDetailsV2);
		return userDetailsV2;
	}
	
	private CustomUserDetails createUserDetailsV1(Map.Entry<String, String> entry) {
		logger.info("createUserDetailsV1");
		String _username = entry.getKey();
		// String _password = "secret";
		String _password = Base64.encodeBase64String(_username.getBytes(Charset.forName("UTF-8")));
		String _profile = entry.getValue();
		logger.info("password: " + _password);
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		if (_profile.equals("ADMIN")) {
			authorities.add(new CustomPermission("PERRO"));
			authorities.add(new CustomPermission("GATO"));
		} else if (_profile.equals("CLIENT")) {
			authorities.add(new CustomPermission("GATO"));
			authorities.add(new CustomPermission("CONEJO"));
			authorities.add(new CustomPermission("BORREGO"));
		}
		return new CustomUserDetails(_username, _password, _profile, authorities);
	}

}