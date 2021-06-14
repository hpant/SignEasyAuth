package com.signeasy.constants;

import com.signeasy.entity.TokenInfo;

public class AppUtils {

	public static boolean actionRequired(TokenInfo tokenInfo) {
		if(tokenInfo.getLevel().equals("1FA"))
			return true;
		return false;
		
	}
}
