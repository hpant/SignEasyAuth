package com.signeasy.response;

import java.util.Date;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ApiResponse<T> {

	private Date timestamp;
	private T body;
	private String message;
	
	public ApiResponse() {
	 this.timestamp = new Date();	
	}
}
