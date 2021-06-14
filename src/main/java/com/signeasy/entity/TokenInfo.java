package com.signeasy.entity;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import lombok.Data;

@Data
public class TokenInfo {

	
	public static final String TOKEN = "token";
	public static final String LEVEL = "level";
	
	private String token;
	private String level;
	
	public TokenInfo(String token, String level) {
		
		Objects.requireNonNull(token);
		Objects.requireNonNull(level);
		this.token = token;
		this.level = level;
	}
	
	public static TokenInfo getTokenInfoFromMap(Map<String,String> map) {
		
		return new TokenInfo(map.get(TOKEN), map.get(LEVEL));
	}
	
	public Map<String, String> convertToMap() {
		
		Map<String, String> tokenToMap = new HashMap<String, String>();
		tokenToMap.put(TOKEN, token);
		tokenToMap.put(LEVEL, level);
		return tokenToMap;
	}
	
}
