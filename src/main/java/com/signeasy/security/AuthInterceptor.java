package com.signeasy.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.signeasy.entity.User;
import com.signeasy.service.AuthenticationService;
import com.signeasy.service.MFAService;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class AuthInterceptor implements HandlerInterceptor{

	@Autowired
	private AuthenticationService authenticationService;
	
	@Autowired
	private MFAService mfaService;
	
	@Override
	public boolean preHandle
	(HttpServletRequest request, HttpServletResponse response, Object handler) 
			throws Exception {

		
		String authHeader = request.getHeader("Authorization");
		log.info("Log for Auth Called with TOKEN " + authHeader);
		User user = authenticationService.validateUser(authHeader);
		if(mfaService.mfaRequired(user))
			return false;
		request.getSession().setAttribute("user", user);
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, 
			Object handler, ModelAndView modelAndView) throws Exception {

	}
}
