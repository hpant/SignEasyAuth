package com.signeasy.response;

import lombok.Data;

@Data
public class ValidateUserResponse {

	public boolean actionRequired = true;
	public String token;
}


