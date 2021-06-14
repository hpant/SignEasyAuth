package com.signeasy.service;

import com.signeasy.response.VerificationResult;

public interface IVerificationService {

	public VerificationResult startVerification();
	
	public VerificationResult checkVerification(String code);
}
