package com.signeasy.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.signeasy.exception.UnauthorizedUserException;
import com.signeasy.response.ApiResponse;
import com.signeasy.response.ErrorResponse;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class ExceptionControllerAdvice {

	@ExceptionHandler(UnauthorizedUserException.class)
	public ResponseEntity<ApiResponse<ErrorResponse>> unauthorizedRequest(UnauthorizedUserException ex) {
		ApiResponse<ErrorResponse> response = new ApiResponse<ErrorResponse>();
		ErrorResponse errorResponse = new ErrorResponse();
		errorResponse.setErrorMessage(ex.getErrorMessage());
		errorResponse.setErrorCode(ex.getErrorCode());
		response.setBody(errorResponse);
		log.error("Bad Request Exception has occured {}",ex.getErrorMessage());
		return new ResponseEntity<ApiResponse<ErrorResponse>>(response, HttpStatus.UNAUTHORIZED);
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ApiResponse<ErrorResponse>> exception(Exception ex) {
		ApiResponse<ErrorResponse> response = new ApiResponse<ErrorResponse>();
		ErrorResponse errorResponse = new ErrorResponse();
		errorResponse.setErrorMessage(ex.getMessage());
		errorResponse.setErrorCode(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()));
		response.setBody(errorResponse);
		ex.printStackTrace();
		log.error("Exception has occured {}",ex);
		return new ResponseEntity<ApiResponse<ErrorResponse>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
