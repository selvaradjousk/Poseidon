package com.nnk.springboot.config;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {


	// ####################### WORKFLOW #############################

    // User send a request with a username and password.
    // Spring security return token back to client API.
    // Client API sends token in each request as part of authentication.
    // Token invalidated on log out.
	// ##############################################################




    // ##############################################################

	// Core interface in Spring Security framework
	// to retrieve the user's authentication and authorisation information
    MyUserDetailsService userDetailsService;

    
    // ##############################################################

    @Autowired
    public SecurityConfiguration(
    		final MyUserDetailsService userDetailsService) {

    	this.userDetailsService = userDetailsService;
    }
    
    // ##############################################################

    // Configuration class to extract the authentication token
    // from the request headers
    // & and call the authentication manager for authentication
    @Bean
	public AuthTokenFilter authenticationJwtTokenFilter() {

    	log.info("### Calling authenticationJwtTokenFilter()");

		return new AuthTokenFilter();
	}
 

    // ##############################################################


    // Helper class that eases the set up of UserDetailService
    // & for setting up in-memory, JDBC, or LDAP 
    // user details or for adding a custom UserDetailsService
    @Override
    public void configure(
    		final AuthenticationManagerBuilder authenticationManagerBuilder)
    					throws Exception {

    	authenticationManagerBuilder
    		.userDetailsService(userDetailsService)
    			.passwordEncoder(passwordEncoder());
    }


    // ##############################################################

    //  Interface for authentication - On valid authentication
    // returns an Authentication instance with the authenticated flag
    @Bean
	@Override
	public AuthenticationManager authenticationManagerBean()
			throws Exception {

    	log.info("#### Calling authenticationManagerBean()");

    	return super.authenticationManagerBean();
	}


    
    // ##############################################################

    // Configuration to encode the password
    // the passwords are encoded with the bcrypt algorithm
    @Bean
    public PasswordEncoder passwordEncoder(){

    	log.info("### Calling login password Encoder Bcrypt");

        return new BCryptPasswordEncoder();
    }


    // ##############################################################
    @Bean
    public JwtAuthenticationSuccessHandler myAuthenticationSuccessHandler() {
      return new JwtAuthenticationSuccessHandler();
    }

    // Allows configuring web based security for specific
    // http requests & URL paths
    @Override
    protected void configure(
    		final HttpSecurity http) throws Exception {

    	log.info("#### configure(HttpSecurity http) initialized");

        // ##############################################################
        // Disable CSRF
    	http	.csrf().disable()
    	// ##############################################################
        
    			// SET PERMISSION ON ENDPOINTS
                .authorizeRequests()

    			.antMatchers("/resources/**").permitAll()
    			.antMatchers("/").permitAll()
    			.antMatchers("/login").permitAll()
    			.antMatchers("/register").permitAll()
    			.antMatchers("/oauthregister").permitAll()
    			.antMatchers(HttpMethod.OPTIONS, "/oauth/token").permitAll()

    			// PRIVATE ENDPOINTS for USERS & ADMIN
                .antMatchers(
                			"/bidList/**",
                			"/rating/**",
                			"/ruleName/**",
                			"/trade/**",
                			"/curvePoint/**")
                .authenticated()
 
                // -----------------
            	// PRIVATE ADMIN SPECIFIC ACCESS ENDPOINTS
            	// -----------------
                .antMatchers("/user/**", "/admin/**").hasAuthority("ADMIN")
                .anyRequest().permitAll()
//                	.and().logout().logoutSuccessHandler(
//                			this::logoutSuccessHandler).permitAll()

                .and()

                // Specifies the session creation policies for Spring Security
                // set the session creation policy to STATELESS
                // does not disable session management in the
                // underlying web server
                
                // instead, it instructs Spring Security
                // to no longer create or use an HTTP session
                // for storing the authentication object
                .sessionManagement()
                		.sessionCreationPolicy(
                				SessionCreationPolicy.STATELESS)
                .and()

                // handle the specific exceptions and sending
                // the custom responses to the client
                // on authentication error throws exception
                .exceptionHandling()
                		.authenticationEntryPoint(
                				this::authenticationEntryPointHandler)
                .and()

                // AccessDeniedHandler only applies to authenticated users
                // unauthenticated users error throws 403 exception
                .exceptionHandling()
                		.accessDeniedHandler(this::accessDeniedHandler);


        // ##############################################################   	
    	http.oauth2Login()
        // ##############################################################
        		.loginPage("/login")
        		// redirect the user to the home page on login success

//        		.defaultSuccessUrl("/bidList/list");
        		.successHandler(myAuthenticationSuccessHandler());

    	log.info("#### http.oauth2Login() ... settings DONE");


        // ##############################################################
        http.logout()
        // ##############################################################
        		// list of cookies to be deleted when the user logs out
        		// .deleteCookies("token", "JSESSIONID")
        		.deleteCookies("JSESSIONID")
        		.deleteCookies("token")

        		// Spring security default logout value
        		.logoutUrl("/app-logout")

        		// when we successfully logout, redirect the browser to
        		// /login?logout
        		.logoutSuccessUrl("/login")

        		// removes the Authentication from the SecurityContext
        		// to prevent issues with concurrent requests
        		 .clearAuthentication(true)

       		 	// invalidate the HttpSession at the time of logout
        		.invalidateHttpSession(true)
        		;

    	log.info("#### http.logout() ... settings DONE");


        // ##############################################################
        // For each api the filter has been added with above
        // http request condition
        // ##############################################################
        http.addFilterBefore(
        		authenticationJwtTokenFilter(),
        		UsernamePasswordAuthenticationFilter.class);
    }
    // ##############################################################

    

    // an interface implemented by ExceptionTranslationFilter,
    // basically a filter which is the first point of entry
    // for Spring Security to check if a user is
    // authenticated and logs the person in or throws
    // exception (unauthorised)

    private void authenticationEntryPointHandler(
    		final HttpServletRequest request,
    		final HttpServletResponse response,
    		final AuthenticationException e)
    						throws IOException {

    	log.info("#### authenticationEntryPointHandler request"
    			+ "... {}", request);


    	log.info("#### authenticationEntryPointHandler  error"
    			+ "... {}", e.getMessage());

        response.setStatus(HttpStatus.UNAUTHORIZED.value());

        if (e.getMessage().contains("Bad credentials")) {

        	log.info("#### authenticationEntryPointHandler  error"
        			+ " Bad credentials... {}", e.getMessage());

            response.sendRedirect("/login?error");

        } else {

//            response.sendRedirect("/?error");
        }
    }

    
    // ##############################################################

    // Customise the 403 error handling process
    private void accessDeniedHandler(
    		final HttpServletRequest request,
    		final HttpServletResponse response,
    		final AccessDeniedException e)
    					throws IOException {


    	log.info("#### accessDeniedHandler  error"
    			+ "... {}", e.getMessage());

        response.setStatus(HttpStatus.FORBIDDEN.value());

        response.sendRedirect("/403");

    	log.info("#### accessDeniedHandler  response"
    			+ "... {}", response.getStatus());

    	log.info("#### accessDeniedHandler  response"
    			+ "... {}", response.getContentType());

    }

    
    // ##############################################################

}