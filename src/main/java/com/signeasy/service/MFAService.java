package com.signeasy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.signeasy.entity.User;
import com.signeasy.entity.UserCredentials;
import com.signeasy.enums.ErrorCodeAndMessage;
import com.signeasy.exception.UnauthorizedUserException;
import com.signeasy.response.VerificationResult;

@Component
public class MFAService {

	@Autowired
	private IVerificationService verificationService;
	
	public boolean mfaRequired(User user) {
		
		for(UserCredentials userCredentials : user.getCreds()) {
			if(userCredentials.getTokenLevel().equals("1FA"))
				throw new UnauthorizedUserException(ErrorCodeAndMessage.MFA_REQUIRED);
		}
		return false;
	}
	
	public VerificationResult startMFA() {
		
		return verificationService.startVerification();
	}
	
	public VerificationResult checkMFA(String code) {
		
		return verificationService.checkVerification(code);
	}
}
