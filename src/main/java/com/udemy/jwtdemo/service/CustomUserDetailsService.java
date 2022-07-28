package com.udemy.jwtdemo.service;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.tomcat.util.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.udemy.jwtdemo.bean.CustomUserDetails;
import com.udemy.jwtdemo.bean.CustomPermission;

@Service
public class CustomUserDetailsService implements UserDetailsService {
	
	Logger logger = LoggerFactory.getLogger(CustomUserDetailsService.class);
	
	private static HashMap<String, String> validUsers;
	
	static {
		validUsers = new HashMap<String, String>();
		validUsers.put("daro", "MANAGER");
		validUsers.put("tania", "EMPLOYEE");
	}
	
	// This method actually does the validation for user existence (against a database or external service call or any other..) 
	@Override
	public CustomUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		logger.info("loadUserByUsername(" + username + ")");
		Map.Entry<String, String> entry = getUserEntry(username);
		if (entry!=null) {
			return createUserDetails(entry);
		}
		throw new UsernameNotFoundException("User does not exist");
	}
	
	private Map.Entry<String, String> getUserEntry(String username) {
		for (Map.Entry<String, String> entry : validUsers.entrySet()) {
			if (entry.getKey().equals(username)) {
				return entry;
			}
		}
		return null;
	}
	
	private CustomUserDetails createUserDetails(Map.Entry<String, String> entry) {
		logger.info("createUserDetails(" + entry.getKey() + ", " + entry.getValue() + ")");
		String _username = entry.getKey();
		Charset charset = Charset.forName("UTF-8");
		String _password = Base64.encodeBase64String(_username.getBytes(charset));
		String _profile = entry.getValue();
		logger.info("password: " + _password);
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		if (_profile.equals("MANAGER")) {
			authorities.add(new CustomPermission("create"));
			authorities.add(new CustomPermission("edit"));
			authorities.add(new CustomPermission("delete"));
			authorities.add(new CustomPermission("view"));
		} else if (_profile.equals("EMPLOYEE")) {
			authorities.add(new CustomPermission("view"));
		}
		return new CustomUserDetails(_username, _password, _profile, authorities);
	}

}