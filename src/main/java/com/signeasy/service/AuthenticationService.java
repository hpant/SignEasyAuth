package com.signeasy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.signeasy.entity.Role;
import com.signeasy.entity.TokenInfo;
import com.signeasy.entity.User;
import com.signeasy.entity.UserCredentials;
import com.signeasy.enums.ErrorCodeAndMessage;
import com.signeasy.exception.UnauthorizedUserException;
import com.signeasy.response.VerificationResult;
import com.signeasy.security.JwtTokenProvider;

@Component
public class AuthenticationService {

	@Autowired
	private JwtTokenProvider jwtTokenProvider;

	@Autowired
	private UserCredentialsService userCredentialsService;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private MFAService mfaService;
	
	public User validateUser(String authHeader) {
		if (authHeader == null || !authHeader.startsWith("Bearer ")) {
			throw new UnauthorizedUserException(ErrorCodeAndMessage.AUTH_TOKEN_MISSING);
		}
		String token = authHeader.split(" ")[1];
		User user = jwtTokenProvider.validateToken(token);
		return user;
	}
	
	public TokenInfo generateAuthToken(String key, String secret) {
		
		UserCredentials userCredentials = userCredentialsService.getUserCreditials(key, secret);
		if(userCredentials.getUserToken() != null && !jwtTokenProvider.hasTokenExpired(userCredentials.getUserToken())) {
			if(userCredentials.getTokenLevel().equals("2FA"))
					return new TokenInfo(userCredentials.getUserToken(), userCredentials.getTokenLevel());
			else {
				mfaService.startMFA();
				return new TokenInfo(userCredentials.getUserToken(), "1FA");
			}
		}
		mfaService.startMFA();
		String token = jwtTokenProvider.createToken(userCredentials.getUser(), key, secret);
		userCredentialsService.updateUserToken(userCredentials.getId(), token, "1FA");
		return new TokenInfo(token, "1FA");
	}
	
	
	public boolean isAuthorized(User user, String permission) {
		
		Role role = userDetailsService.getRolePermissions(user.getRole().getName());
		if(role.getResourcePermissions().stream().filter(x->x.getPermission().equals(permission)).count() == 1)
			return true;
		throw new UnauthorizedUserException(ErrorCodeAndMessage.PERMISSION_ERROR);
	}
	
	
	public TokenInfo upgradeToken(String token, String code) {
		
		VerificationResult verificationResult = mfaService.checkMFA(code);
		if(!verificationResult.isValid())
			throw new UnauthorizedUserException(ErrorCodeAndMessage.INVALID_CODE);
		User user = jwtTokenProvider.validateToken(token);
		UserCredentials userCreds = null;
		for(UserCredentials userCredentials : user.getCreds())
			userCreds = userCredentials;
		UserCredentials userCredentials = userCredentialsService.getUserCreditials(userCreds.getClientKey(), userCreds.getSecret());
		String upgradedToken = jwtTokenProvider.upgradeToken(token);
		userCredentialsService.updateUserToken(userCredentials.getId(), upgradedToken, "2FA");
		return new TokenInfo(upgradedToken, "2FA");
		
	}
}
