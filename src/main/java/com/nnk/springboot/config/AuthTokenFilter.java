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

/**
 * The Class AuthTokenFilter.
 */
@Log4j2
public class AuthTokenFilter extends OncePerRequestFilter {


	/** The user repository. */
	@Autowired
	UserRepository userRepository;

	/** The user service. */
	@Autowired
	UserService userService;


    /** The authentication manager. */
    @Autowired
	AuthenticationManager authenticationManager;

	/** The user details service. */
	@Autowired
	private MyUserDetailsService userDetailsService;


	/**
 * Do filter internal.
 *
 * @param request the request
 * @param response the response
 * @param filterChain the filter chain
 * @throws ServletException the servlet exception
 * @throws IOException Signals that an I/O exception has occurred.
 */
@Override
	protected void doFilterInternal(
			HttpServletRequest request,
			HttpServletResponse response,
			FilterChain filterChain)
					throws ServletException, IOException {


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



		log.debug("### HttpServletResponse - response"
				+ ".getHeaderNames():"
				+ " {}", response.getHeaderNames());
		log.debug("### FilterChain: {}", filterChain);



		if (request.getUserPrincipal() != null) {

			try {
			Authentication authentication = null;
			String jwt = null;

            log.error(" ****************jwt parsed: {}", request);

            Cookie cookie = WebUtils.getCookie(request, "token");
            
            // #############IF COOKIE IS NOT NULL #####################################
            if (cookie != null) {



                logger.info("Cookie is not null ***********" + cookie);


            } else {

            	// ############# IF COOKIE IS NULL <START> ##############

            	// CHECK IF OAUTH USER IN DATABASE OR IF NOT PERSIST HIM TO DB
          	  checkForOauthUserInDatabaseIfAbsentPersistUser(request);
            }
            

		} catch (Exception e) {

			log.error("Cannot set user authentication: {}", e);

		}
		}
		filterChain.doFilter(request, response);



	}

    // ############################################################

	/**
     * Check for oauth user in database if absent persist user.
     *
     * @param request the request
     */
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
			    	+ userFoundAfterUpdate.getUsername()
			    	+ " SUCCESSFULLY");
			    }

		  } else {

			  log.debug("### User already registered in the database");

		  }
	}

}
