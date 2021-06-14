package com.signeasy.mfa;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.signeasy.response.VerificationResult;
import com.signeasy.service.IVerificationService;
import com.twilio.Twilio;
import com.twilio.exception.ApiException;
import com.twilio.rest.verify.v2.service.Verification;
import com.twilio.rest.verify.v2.service.VerificationCheck;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class TwilioIntegration implements IVerificationService{

	@Value("${twilio.sid}")
	private String sid;
	@Value("${twilio.token}")
	private String token;
	@Value("${twilio.phone}")
	private String number;
	

	@PostConstruct
	public void init() {
		
		 Twilio.init(sid, token);
	}

    public VerificationResult startVerification() {
        try {
            Verification verification = Verification.creator("VA45656fc5d683995480fab2cba9a45b81", number, "sms").create();
            log.info("OTP sent");
            return new VerificationResult(verification.getSid());
        } catch (ApiException exception) {
        	exception.printStackTrace();
            return new VerificationResult(new String[] {exception.getMessage()});
        }
    }

    public VerificationResult checkVerification(String code) {
        try {
            VerificationCheck verification = VerificationCheck.creator("VA45656fc5d683995480fab2cba9a45b81", code).setTo(number).create();
            if("approved".equals(verification.getStatus())) {
                return new VerificationResult(verification.getSid());
            }
            return new VerificationResult(new String[]{"Invalid code."});
        } catch (ApiException exception) {
            return new VerificationResult(new String[]{exception.getMessage()});
        }
    }

}