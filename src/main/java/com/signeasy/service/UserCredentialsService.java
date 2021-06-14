package com.signeasy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.signeasy.entity.UserCredentials;
import com.signeasy.enums.ErrorCodeAndMessage;
import com.signeasy.exception.InvalidUserException;
import com.signeasy.repository.UserCredentialsRepository;

@Component
public class UserCredentialsService {

	
	@Autowired
	private UserCredentialsRepository userCredentialsRepository;
	
	
	public UserCredentials getUserCreditials(String clientKey, String secret) {
		
		UserCredentials userCredentials = userCredentialsRepository.getByClientKeyAndSecret(clientKey, secret);
		if(userCredentials == null)
			throw new InvalidUserException(ErrorCodeAndMessage.INVALID_USER_CREDENTIALS_ERROR);
		return userCredentials;
	}
	
	public void updateUserToken(int id, String token, String tokenLevel) {
		userCredentialsRepository.updateToken(id, token, tokenLevel);
	}
}
