package com.nnk.springboot.config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

/**
 * The Class JwtAuthenticationSuccessHandler.
 */
public class JwtAuthenticationSuccessHandler
			implements AuthenticationSuccessHandler {

    /**
     * On authentication success.
     *
     * @param request the request
     * @param response the response
     * @param authentication the authentication
     */
    @Override
    public void onAuthenticationSuccess(
    		final HttpServletRequest request,
    		final HttpServletResponse response,
    		final Authentication authentication) {
        // We do not need to do anything extra
    	// on REST authentication success,
    	// because there is no page to redirect to
    }

}
