package com.signeasy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.signeasy.entity.Role;
import com.signeasy.entity.User;
import com.signeasy.enums.ErrorCodeAndMessage;
import com.signeasy.exception.InvalidUserException;
import com.signeasy.repository.RoleRepository;
import com.signeasy.repository.UserRepository;

@Component
public class UserDetailsService {

	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	public User getUserDetails(String userId) {
		
		User user = userRepository.findByUserId(userId);
		if(user == null)
			throw new InvalidUserException(ErrorCodeAndMessage.INVALID_USER);
		return user;
	}
	
	public Role getRolePermissions(String name) {
		
		Role role = roleRepository.findByName(name);
		return role;
		
	}
	
}
