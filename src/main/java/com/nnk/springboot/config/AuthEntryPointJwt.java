package com.nnk.springboot.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Component
public class AuthEntryPointJwt implements AuthenticationEntryPoint {


	@Override
	public void commence(
			HttpServletRequest request,
			HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {

        // This is invoked when user tries to access a secured REST resource
		// without supplying any credentials
        // We should just send a 401 Unauthorized response because
		// there is no 'login page' to redirect to
		// https://www.toptal.com/java/rest-security-with-jwt-spring-security-and-java

		log.error("Authenticated Entry Point commence() called");

		log.error("Unauthorized error: {}", authException.getMessage());

		response.sendError(
				HttpServletResponse.SC_UNAUTHORIZED, "Error: Unauthorized");
	}

}