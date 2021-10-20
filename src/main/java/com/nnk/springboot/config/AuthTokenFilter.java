package com.nnk.springboot.config;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import lombok.extern.log4j.Log4j2;

//############################################################
//Present implementation focuses on JWT based implementation
//Token Based Authorisation workflow
//############################################################

@Log4j2
//@Component
public class AuthTokenFilter extends OncePerRequestFilter {


    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
	AuthenticationManager authenticationManager;


    @Autowired
    private MyUserDetailsService userDetailsService;


    // ************************************************************************

    @Override
    protected void doFilterInternal(
    		final HttpServletRequest request,
    		final HttpServletResponse response,
    		final FilterChain filterChain)
    				throws ServletException, IOException {



		log.debug("### doFilterInternal called");

		log.debug("### HttpServletRequest - request: {}", request);


		log.debug("### HttpServletRequest - getUserPrincipal():"
				+ " {}", request.getUserPrincipal());
		log.debug("### HttpServletRequest - getRemoteUser():"
				+ " {}", request.getRemoteUser());
//		log.debug("### HttpServletRequest - getCookies():"
//				+ " {}", request.getCookies());
		log.debug("### HttpServletRequest - getAuthType():"
				+ " {}", request.getAuthType());
		log.debug("### HttpServletRequest - getRequestURI():"
				+ " {}", request.getRequestURI());
		log.debug("### HttpServletRequest - getRequestURL():"
				+ " {}", request.getRequestURL());
//		log.debug("### HttpServletRequest - getSession():"
//				+ " {}", request.getSession());
//		log.debug("### HttpServletRequest - getRequestedSessionId():"
//				+ " {}", request.getRequestedSessionId());
		log.debug("### HttpServletRequest - getRemoteAddr():"
				+ " {}", request.getRemoteAddr());
		log.debug("### HttpServletRequest - getRemoteHost():"
				+ " {}", request.getRemoteHost());
		log.debug("### HttpServletRequest - getLocales():"
				+ " {}", request.getLocales());
		log.debug("### HttpServletRequest - getLocalName():"
				+ " {}", request.getLocalName());
		log.debug("### HttpServletRequest - getLocalPort():"
				+ " {}", request.getLocalPort());
		log.debug("### HttpServletRequest - getMethod():"
				+ " {}", request.getMethod());
		log.debug("### HttpServletRequest - getAttributeNames():"
				+ " {}", request.getAttributeNames());
		log.debug("### HttpServletRequest - getContentType():"
				+ " {}", request.getContentType());
		log.debug("### HttpServletRequest - getContentLength():"
				+ " {}", request.getContentLength());
		log.debug("### HttpServletRequest - getContextPath():"
				+ " {}", request.getContextPath());
		log.debug("### HttpServletRequest - getDispatcherType():"
				+ " {}", request.getDispatcherType());
		log.debug("### HttpServletRequest - getHeaderNames():"
				+ " {}", request.getHeaderNames());
		log.debug("### HttpServletRequest - getParameterMap():"
				+ " {}", request.getParameterMap());
		log.debug("### HttpServletRequest - getParameterNames():"
				+ " {}", request.getParameterNames());



		log.debug("### HttpServletResponse - response.getHeaderNames():"
				+ " {}", response.getHeaderNames());
		log.debug("### FilterChain: {}", filterChain);


		log.debug("### request.getHeader(\"Authorization\")"
						+ request.getHeader("Authorization"));

 try {

            Cookie[] cookies = request.getCookies();
            String jwt = cookies[0].getValue();


            logger.info("JWT from cookie value ***********" + jwt);


            if (jwt != null && jwtUtils.validateJwtToken(jwt)) {

				log.error("### jwt not null and validated ", jwt);

                String username = jwtUtils.getUserNameFromJwtToken(jwt);

				log.error("### jwt urerered username : {}", username);

                UserDetails userDetails = userDetailsService.loadUserByUsername(username);

				log.error("### userDetails: {}", userDetails.toString());

                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());

				log.error("### UserNamePasswordAuthenticationToken intial: {}", authentication);

                SecurityContextHolder.getContext().setAuthentication(authentication);

				log.error("### UserNamePasswordAuthenticationToken set value: {}", authentication);

            }
        } catch (Exception e) {

			log.error("Cannot set user authentication: {}", e);

        }

        filterChain.doFilter(request, response);
        SecurityContextHolder.getContext().setAuthentication(null);
    }

    // ************************************************************************

}
