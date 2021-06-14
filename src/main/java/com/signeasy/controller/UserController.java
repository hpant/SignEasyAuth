package com.signeasy.controller;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.signeasy.constants.AppConstants;
import com.signeasy.constants.AppUtils;
import com.signeasy.entity.TokenInfo;
import com.signeasy.entity.User;
import com.signeasy.response.ApiResponse;
import com.signeasy.response.ValidateUserResponse;
import com.signeasy.service.AuthenticationService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
//@RequestMapping("/api")
public class UserController {

	@Autowired
	ObjectMapper mapper;
	
	@Autowired
	private AuthenticationService authenticationService;
	
	@PostConstruct
	private void configureObjectMapper() {
		mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
	}
	
	@GetMapping(value = "/auth",produces = MediaType.APPLICATION_JSON_VALUE)
	public ApiResponse<ValidateUserResponse> validateUser(@RequestParam String clientKey , @RequestParam String secret) {
		
		log.info("Validate User Call Request {} ", clientKey);
		TokenInfo tokenInfo = authenticationService.generateAuthToken(clientKey, secret);
		ValidateUserResponse validateUserResponse = new ValidateUserResponse();
		validateUserResponse.setToken(tokenInfo.getToken());
		validateUserResponse.setActionRequired(AppUtils.actionRequired(tokenInfo));
		ApiResponse<ValidateUserResponse> response = new ApiResponse<>();
		response.setBody(validateUserResponse);
		response.setMessage("SUCCESS");
		return response;
	}
	
	@GetMapping(value = "/2fa",produces = MediaType.APPLICATION_JSON_VALUE)
	public ApiResponse<ValidateUserResponse> perform2fa(@RequestParam String code, @RequestParam String token) {
		
		log.info("2FA User Call Request {} ", code);
		TokenInfo tokenInfo = authenticationService.upgradeToken(token, code);
		ValidateUserResponse validateUserResponse = new ValidateUserResponse();
		validateUserResponse.setToken(tokenInfo.getToken());
		validateUserResponse.setActionRequired(AppUtils.actionRequired(tokenInfo));
		ApiResponse<ValidateUserResponse> response = new ApiResponse<>();
		response.setBody(validateUserResponse);
		response.setMessage("SUCCESS");
		return response;
	}
	
	@PostMapping(value = "/member/add")
	public String addMember(HttpServletRequest request, HttpServletResponse response) {
		authenticationService.isAuthorized((User) request.getSession().getAttribute("user"), AppConstants.ADD_MEMBER_PER);
		return "Add Member Successful";
	}
	
	@PostMapping(value = "/member/edit")
	public String editMember(HttpServletRequest request, HttpServletResponse response) {
		authenticationService.isAuthorized((User) request.getSession().getAttribute("user"), AppConstants.EDIT_MEMBER_PER);
		return "Edit Member Successful";
	}
	
	@PostMapping(value = "/member/del")
	public String delMember(HttpServletRequest request, HttpServletResponse response) {
		authenticationService.isAuthorized((User) request.getSession().getAttribute("user"), AppConstants.DEL_MEMBER_PER);
		return "Delete Member Successful";
	}
	
	@PostMapping(value = "/document/add")
	public String addDocument(HttpServletRequest request, HttpServletResponse response) {
		authenticationService.isAuthorized((User) request.getSession().getAttribute("user"), AppConstants.ADD_DOCUMENT_PER);
		return "Add Document Successful";
	}
	
	@PostMapping(value = "/document/del")
	public String delDocument(HttpServletRequest request, HttpServletResponse response) {
		authenticationService.isAuthorized((User) request.getSession().getAttribute("user"), AppConstants.DEL_DOCUMENT_PER);
		return "Delete Document Successful";
	}
	
	@GetMapping(value = "/document")
	public String getDocument(HttpServletRequest request, HttpServletResponse response) {
		authenticationService.isAuthorized((User) request.getSession().getAttribute("user"), AppConstants.VIEW_DOCUMENT_PER);
		return "View Document Successful";
	}
}

