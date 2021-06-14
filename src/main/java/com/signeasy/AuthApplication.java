package com.signeasy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.PropertySource;


import lombok.extern.slf4j.Slf4j;

@SpringBootApplication(exclude = {HibernateJpaAutoConfiguration.class,SecurityAutoConfiguration.class})
@EnableConfigurationProperties
@PropertySource("application-stg.properties")
@Slf4j
public class AuthApplication extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(AuthApplication.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(AuthApplication.class, args);
		log.info("App Startup log");
	}
}