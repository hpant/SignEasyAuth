package com.signeasy.exception;

import com.signeasy.enums.ErrorCodeAndMessage;

import lombok.Data;

@Data
public class UnauthorizedUserException extends SignEasyException{


	private static final long serialVersionUID = 1L;

	private String errorCode;
	private String errorMessage;

	public UnauthorizedUserException(ErrorCodeAndMessage errorCodeAndMessage) {
		super(errorCodeAndMessage.getMessage());
		this.errorCode = errorCodeAndMessage.getErrorCode();
		this.errorMessage = errorCodeAndMessage.getMessage();
	}

	public UnauthorizedUserException() {
		this(ErrorCodeAndMessage.UNAUTH_ERROR);
	}


}
