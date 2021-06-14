package com.signeasy.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
@EnableWebSecurity
public class CustomMvcConfig implements WebMvcConfigurer {

	@Autowired
	AuthInterceptor authInterceptor;

	@Override
    public void addInterceptors(InterceptorRegistry registry){
		log.info("Interceptor Initialized");
		List<String> exclusions = new ArrayList<>();
		exclusions.add("/auth");
		exclusions.add("/2fa");
		registry.addInterceptor(authInterceptor).addPathPatterns("/**").excludePathPatterns(exclusions);
	}

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		// TODO Auto-generated method stub

		registry.addMapping("/api/**").allowedOrigins("*").allowedMethods("OPTIONS", "GET", "PUT", "POST", "DELETE").allowedHeaders("*");
		WebMvcConfigurer.super.addCorsMappings(registry);
	}
	
}