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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.WebUtils;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.dto.UserDTO;
import com.nnk.springboot.repository.UserRepository;
import com.nnk.springboot.service.UserService;

import lombok.extern.log4j.Log4j2;

//############################################################
// Present implementation focuses on session based implementation
// OAuth2AuthenticationToken & UsernamePasswordAuthenticationToken
// ############################################################

@Log4j2
public class AuthTokenFilter extends OncePerRequestFilter {


	@Autowired
	UserRepository userRepository;
	
	@Autowired
	UserService userService;
	
	@Autowired
	private JwtUtils jwtUtils;

    @Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	private MyUserDetailsService userDetailsService;

//	
//	@Autowired
//	PasswordEncoder passwordEncoder;


	@Override
	protected void doFilterInternal(
			HttpServletRequest request,
			HttpServletResponse response,
			FilterChain filterChain) throws ServletException, IOException {


		log.debug("### doFilterInternal called");

		log.debug("### HttpServletRequest - request: {}", request);


		log.debug("### HttpServletRequest - getUserPrincipal():"
				+ " {}", request.getUserPrincipal());
		log.debug("### HttpServletRequest - getRemoteUser():"
				+ " {}", request.getRemoteUser());
		log.debug("### HttpServletRequest - getCookies():"
				+ " {}", request.getCookies());
		log.debug("### HttpServletRequest - getAuthType():"
				+ " {}", request.getAuthType());
		log.debug("### HttpServletRequest - getRequestURI():"
				+ " {}", request.getRequestURI());
		log.debug("### HttpServletRequest - getRequestURL():"
				+ " {}", request.getRequestURL());
		log.debug("### HttpServletRequest - getSession():"
				+ " {}", request.getSession());
		log.debug("### HttpServletRequest - getRequestedSessionId():"
				+ " {}", request.getRequestedSessionId());
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

	    // ############################################################
		// THOUGH JWT not of much user with statefull session based
		// application, We have implementing to keep open the possibility
		// of application being flexible for JWT implementation
		// transition with classic login excluding oauth
		
		// Present implementation is focuses on session based implementation
		// based on the
		// OAuth2AuthenticationToken and UsernamePasswordAuthenticationToken
	    // ############################################################


		// UserPrincipal null condition is checked to prevent recursive
		// conditional check on static resources files which are
		// always apparently null (eg. images, css & js files)
		if (request.getUserPrincipal() != null ) {

			try {
			Authentication authentication = null;
			String jwt = null;

            log.error(" ****************jwt parsed: {}", request);

            Cookie cookie = WebUtils.getCookie(request, "token");
            
// #############IF COOKIE IS NOT NULL #####################################
            if (cookie != null) {

            	jwt = cookie.getValue();

                logger.info("JWT ***********" + jwt);


            } else {

            	// ############# IF COOKIE IS NULL <START> ##############

            	// CHECK IF OAUTH USER IN DATABASE OR IF NOT PERSIST HIM TO DB
          	  checkForOauthUserInDatabaseIfAbsentPersistUser(request);
            }
            
            // Jwt token validation called if not null
            if (jwt != null && jwtUtils.validateJwtToken(jwt)) {

				log.debug("jwt not null and validated");

				String username = jwtUtils.getUserNameFromJwtToken(jwt);

				log.debug("jwt urerered username : {}", username);

				UserDetails userDetails = userDetailsService
						.loadUserByUsername(username);

				log.debug("userDetails: {}", userDetails.toString());

				UsernamePasswordAuthenticationToken settingNewToken
					= new UsernamePasswordAuthenticationToken(
						userDetails,
						null,
						userDetails.getAuthorities());

				log.debug("UserNamePasswordAuthenticationToken intial:"
						+ " {}", settingNewToken);

				settingNewToken.setDetails(new WebAuthenticationDetailsSource()
						.buildDetails(request));

				log.debug("UserNamePasswordAuthenticationToken set value:"
						+ " {}", settingNewToken);

				SecurityContextHolder.getContext()
				.setAuthentication(settingNewToken);

			}

		} catch (Exception e) {

			log.error("Cannot set user authentication: {}", e);

		}
		}
		filterChain.doFilter(request, response);



	}

    // ############################################################
	
	private void checkForOauthUserInDatabaseIfAbsentPersistUser(
			HttpServletRequest request) {

		// ############################################################
		// DETERMINE IF OAUTH USER IN DATABASE OR NOT IF NOT ADDED TO DB
		// ############################################################

		// fetching the value of remote user from OAuth2AuthenticationToken
		String userName = request.getRemoteUser();

		log.debug("### Fetching Username from OAuth2AuthenticationToken :"
				+ " {}", userName);

		// check if the user already registered in the database
		if ((userRepository.findUserByUsername(userName))
				  .isPresent() == false & userName != null) {


			log.debug("### Oauth user does not exist in the database"
					+ " {}", userName);
		
			    UserDTO createUserDto = new UserDTO();
			    createUserDto.setUsername(request.getRemoteUser());

			    // check if user is OAuth github user
			    if((request.getUserPrincipal().toString()
			    		.contains("github"))) {

					log.debug("### Check Oauth user is Github User - {} ? "
							+ " if so fullname registered with"
							+ " as Github", userName);
				
			    	createUserDto.setFullname("Github");

			    	// check if user is OAuth google user
			    } else if ((request.getUserPrincipal()
			    		.toString().contains("google"))) {

			    	createUserDto.setFullname("Google");	

					log.debug("### Check Oauth user is Google User - {} ? "
							+ " if so fullname registered with"
							+ " as Google", userName);
				
			    } else {

			    	// check if user is from other Oauth provider
			    	createUserDto.setFullname("WhoAreYou");

					log.debug("### Check Oauth user is other Provider"
							+ " if so fullname registered with"
							+ " as WhoAreYou", userName);
				
			    }
			    
			    createUserDto.setRole("USER");

			    log.debug("### Oauth user role set to USER");

			    createUserDto.setPassword("Password1!");

			    log.debug("### Oauth user rolepassword set as Password1!");

			    userService.addUser(createUserDto);

			    log.debug("### userService.addUser(createUserDto) called"
			    		+ " to persist Oauth user data to database");

			    User userFoundAfterUpdate = userRepository
			    		.findByUsername(request.getRemoteUser());

			    if (userFoundAfterUpdate != null) {

			    	log.info("### New oauth user added to database: "
			    	+ userFoundAfterUpdate.getUsername()+ " SUCCESSFULLY");
			    }
 
		  } else {

			  log.debug("### User already registered in the database");

		  }
	}

}
