package com.signeasy.exception;

import com.signeasy.enums.ErrorCodeAndMessage;

import lombok.Data;

@Data
public class InvalidUserException extends SignEasyException{


	private static final long serialVersionUID = 1L;

	private String errorCode;
	private String errorMessage;

	public InvalidUserException(ErrorCodeAndMessage errorCodeAndMessage) {
		super(errorCodeAndMessage.getMessage());
		this.errorCode = errorCodeAndMessage.getErrorCode();
		this.errorMessage = errorCodeAndMessage.getMessage();
	}

	public InvalidUserException() {
		this(ErrorCodeAndMessage.INVALID_USER_CREDENTIALS_ERROR);
	}


}
