package com.signeasy.exception;

public class SignEasyException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public SignEasyException() {

	}

	public SignEasyException(Throwable cause) {
		initCause(cause);
	}
	
	public SignEasyException(String message) {
		super(message);
	}
	
	public SignEasyException(Throwable cause,String message) {
		super(message);
		initCause(cause);
	}
	
	@Override
	public String getMessage() {
		return super.getMessage();
	}
}
