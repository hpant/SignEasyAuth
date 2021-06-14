package com.signeasy.response;

import lombok.Data;

@Data
public class ErrorResponse {

	private String errorMessage;
	private String errorCode;
	private String meta;
}