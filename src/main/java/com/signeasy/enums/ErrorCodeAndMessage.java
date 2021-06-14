package com.signeasy.enums;

public enum ErrorCodeAndMessage {

	
	INTERNAL_SERVER_ERROR("ER-500","Internal Server Error"),
	INVALID_USER_CREDENTIALS_ERROR("ER-501","Invalid User Credentials"),
	INVALID_USER("ER-502","Invalid User"),
	UNAUTH_ERROR("ER-401","UnAuthorized User"),
	AUTH_TOKEN_MISSING("ER-406","Auth Token Missing"),
	MFA_REQUIRED("ER-403","MFA Required"),
	INVALID_CODE("ER-404","Invalid code provided"),
	AUTH_TOKEN_EXPIRED("ER-405","Auth Token Expired"),
	PERMISSION_ERROR("ER-402","Insufficient Permission");
	
    private String errorCode;
	private String message;

	public String getErrorCode() {
		return errorCode;
	}

	public String getMessage() {
		return message;
	}

	ErrorCodeAndMessage(String errorCode, String message) {
		this.errorCode = errorCode;
		this.message = message;
	}
}