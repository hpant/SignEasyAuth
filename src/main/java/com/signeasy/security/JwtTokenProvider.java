package com.signeasy.security;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Component;

import com.signeasy.entity.Role;
import com.signeasy.entity.User;
import com.signeasy.entity.UserCredentials;
import com.signeasy.enums.ErrorCodeAndMessage;
import com.signeasy.exception.UnauthorizedUserException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class JwtTokenProvider {

	private String secretKey = "secretKey";
	
	private long validityInMilliseconds = 3600000;
	
	public String createToken(User user, String key, String secret) {
		
		Claims claims = Jwts.claims().setSubject(user.getUserId());
		Date now = new Date();
		Date validity = new Date(now.getTime() + validityInMilliseconds);
		claims.put("issuer",key);
		claims.put("issuerSecret",secret);
		claims.put("role",user.getRole().getName());
		claims.put("level", "1FA");
		return Jwts.builder()
				.setClaims(claims)
				.setIssuedAt(now)
				.setExpiration(validity)
				.signWith(SignatureAlgorithm.HS256, secretKey)
				.compact();
	}
	
	public String upgradeToken(String token) {
		try {
			Jws<Claims> jwsClaims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
			Claims claims = jwsClaims.getBody();
			String clientKey = String.valueOf(claims.get("issuer"));
			String secret = String.valueOf(claims.get("issuerSecret"));
			claims.put("issuer",clientKey);
			claims.put("issuerSecret",secret);
			claims.put("level", "2FA");
			Date now = new Date();
			Date validity = new Date(now.getTime() + validityInMilliseconds);
			return Jwts.builder()
					.setClaims(claims)
					.setIssuedAt(now)
					.setExpiration(validity)
					.signWith(SignatureAlgorithm.HS256, secretKey)
					.compact();
		} catch(ExpiredJwtException e) {
			log.info("Token expired");
			throw new UnauthorizedUserException(ErrorCodeAndMessage.AUTH_TOKEN_EXPIRED);
		} catch(JwtException e) {
			throw new UnauthorizedUserException();
		}
		
	}
	
	public User validateToken(String token) {
		try {
			Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
			if(claims.getBody().getExpiration().before(new Date()))
				throw new UnauthorizedUserException();
			User user = new User();
			user.setUserId(claims.getBody().getSubject());
			Role role = new Role();
			user.setRole(role);
			user.getRole().setName(String.valueOf(claims.getBody().get("role")));
			UserCredentials userCredentials = new UserCredentials();
			userCredentials.setClientKey(String.valueOf(claims.getBody().get("issuer")));
			userCredentials.setSecret(String.valueOf(claims.getBody().get("issuerSecret")));
			userCredentials.setTokenLevel(String.valueOf(claims.getBody().get("level")));
			Set<UserCredentials> userCreds = new HashSet<>();
			userCreds.add(userCredentials);
			user.setCreds(userCreds);
			return user;
		} catch(ExpiredJwtException e) {
			log.info("Token expired");
			throw new UnauthorizedUserException(ErrorCodeAndMessage.AUTH_TOKEN_EXPIRED);
		} catch(JwtException e) {
			throw new UnauthorizedUserException();
		}
		
	}
	
	public boolean hasTokenExpired(String token) {
		
		try {
		Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
		} catch(ExpiredJwtException e) {
			return true;
		}
		return false;
	}
}
