package com.nnk.springboot.config;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.WebUtils;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class AuthTokenFilter extends OncePerRequestFilter {

	@Autowired
	private JwtUtils jwtUtils;

	@Autowired
	private MyUserDetailsService userDetailsService;


	@Override
	protected void doFilterInternal(
			HttpServletRequest request,
			HttpServletResponse response,
			FilterChain filterChain) throws ServletException, IOException {

		log.error("doFilterInternal called");

		log.error("HttpServletRequest: {}", request);

		log.error("HttpServletResponse: {}", response);

		log.error("FilterChain: {}", filterChain);

						try {//

			String jwt = parseJwt(request);
//			String jwt = null;

            log.error(" ****************jwt parsed: {}", request);



            if (jwt != null && jwtUtils.validateJwtToken(jwt)) {

				log.error("jwt not null and validated");

				String username = jwtUtils.getUserNameFromJwtToken(jwt);

				log.error("jwt urerered username : {}", username);

				UserDetails userDetails = userDetailsService.loadUserByUsername(username);

				log.error("userDetails: {}", userDetails.toString());

				UsernamePasswordAuthenticationToken authentication
					= new UsernamePasswordAuthenticationToken(
						userDetails,
						null,
						userDetails.getAuthorities());

				log.error("UserNamePasswordAuthenticationToken intial: {}", authentication);

				authentication.setDetails(new WebAuthenticationDetailsSource()
						.buildDetails(request));

				log.error("UserNamePasswordAuthenticationToken set value: {}", authentication);

				SecurityContextHolder.getContext().setAuthentication(authentication);

			}

		} catch (Exception e) {

			log.error("Cannot set user authentication: {}", e);

		}

		filterChain.doFilter(request, response);

	}//

	private String parseJwt(HttpServletRequest request) {

		log.error("jwt parseing request : {}", request);

		String headerAuth = request.getHeader("Authorization");

		log.error("headerAuth : {}", headerAuth);
		if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {

			log.error("jwt contains header & Bearer : {}", headerAuth.length());

			log.error("jwt contains header length : {}", headerAuth.substring(7, headerAuth.length()));
//

			headerAuth.substring(7, headerAuth.length());
		}
		return null;

	}
}
