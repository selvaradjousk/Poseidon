package com.nnk.springboot.config;



import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    UserDetailsService userDetailsService;

    // ************************************************************************
    
    // https://www.javainuse.com/spring/boot-jwt
    
    // ===>  UserDetailsService (Autowired) *
    // ===>  UserDetailsService (can be send as bean for custom user details service)

    
    // ===>  AuthEntryPointJwt (Autowired) **
    // ===>  AuthTokenFilter (Bean) ***
 
    
    // ===>  AuthenticationManager (Bean) ***
    // ===>  PasswordEncoder (Bean) ***

    // ===>  DaoAuthenticationProvider  passed into config AuthMangagerBuilder instead of userDetailsService*
    
    // ===>  configure - AuthenticationManagerBuilder ***
    // ===>  configure - HttpSecurity ***
    //			- http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
    
    // ===>  configure - Websecurity *
    // 			- web.ignoring().anthMatchers("/resources/**", "/static/**", "/webjars/**")
    
    // need stateless authentication with a JWT token
    // ===> Enable CORS and disable CSRF
    // ===> Set session management to stateless
    // ===> Set unauthorized requests exception handler
    // ===> Set permissions on endpoints
    // ===> Add JWT token filter
    
 
    // ************************************************************************
    // ************************************************************************
    @Bean
    public PasswordEncoder passwordEncoder(){

    	log.info("login password Encoder Bcrypt - function called");

        return new BCryptPasswordEncoder();
    }
    // ************************************************************************
    // ************************************************************************



    // ************************************************************************
    // ************************************************************************
    @Override
    protected void configure(final AuthenticationManagerBuilder auth) throws Exception {

    	log.info("auth.userDetailsService(userDetailsService) - function called");

    	auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());

    	log.info("auth.userDetailsService(userDetailsService) - function executed");

    }
    // ************************************************************************
    // ************************************************************************



    @Override
    public void configure(HttpSecurity http) throws Exception{

    	// -------------------------------------
        // Enable CORS and disable CSRF
        http = http.cors().and().csrf().disable();
        // -------------------------------------

        // https://stackoverflow.com/questions/57882688/spring-security-oauth2-with-jwt-redirect-to-login-page
        // https://blog.jdriven.com/2014/10/stateless-spring-security-part-2-stateless-authentication/
        // https://www.baeldung.com/spring-security-oauth-jwt
        // -------------------------------------
        // Set session management to stateless
        // -------------------------------------
//        http = http
//            .sessionManagement()
//            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//            .and();
        // The above stateless is breaking the oauth login sessions and with classic login

        
        
        
        // Following https://www.toptal.com/spring/spring-security-tutorial        
        
        

        // Set unauthorized requests exception handler
        http = http
            .exceptionHandling()
            .authenticationEntryPoint(
                (request, response, ex) -> {
                    response.sendError(
                        HttpServletResponse.SC_UNAUTHORIZED,
                        ex.getMessage()
                    );
                }
            )
            .and();        

        // ************************************************************************
    	// SET PERMISSION ON ENDPOINTS
    	http.authorizeRequests()
        // ************************************************************************

    			// ----------------
    			// PUBLIC ENDPOINTS
    			// -----------------
                .antMatchers("/")
                	.permitAll()

                .antMatchers("/css/**")
                	.permitAll()

                .antMatchers("/img/**")
                	.permitAll()

                .antMatchers("/register/**")
                	.permitAll()


        			// ----------------
        			// PRIVATE ENDPOINTS
        			// -----------------
                	
                	// -----------------
                	// user + admin
                	// -----------------
//                .antMatchers(
//                    "/bidList/**",
//                    "/curvePoint/**",
//                    "/ruleName/**",
//                    "/rating/**",
//                    "/trade/**")
//                .hasAnyRole("USER", "ADMIN")


	            	// -----------------
	            	// PRIVATE ADMIN SPECIFIC ACCESS ENDPOINTS
	            	// -----------------
                   .antMatchers("/admin/*")
                	.hasAuthority("ADMIN")

                .antMatchers("/user/*")
                	.hasAuthority("ADMIN")


//                	// -----------------
//                    // ERROR HANDLING
//                	// -----------------
//                    .and()
//                    .exceptionHandling()
//                    .accessDeniedPage("/error")  
                	
                	// PRIVATE ENDPOINTS for USERS & ADMIN
                .anyRequest().authenticated()
                ;


        // ************************************************************************    	
    	http.formLogin()
        // ************************************************************************
        		.loginPage("/login")
        		// redirect the user to the home page on login success

        		.loginProcessingUrl("/login")
        		.defaultSuccessUrl("/bidList/list")
        		// redirect the user back to the login page on error login

        		.failureUrl("/login?error=true").permitAll();

        // ************************************************************************    	
    	http.oauth2Login()
        // ************************************************************************
        		.loginPage("/login")
        		// redirect the user to the home page on login success
//
//        		.loginProcessingUrl("/login")
        		.defaultSuccessUrl("/bidList/list");
        		// redirect the user back to the login page on error login

//        		.failureUrl("/login?error=true").permitAll();

    	
    	
    	
    	
        //      http
        //				.oauth2Login()
        //      		.defaultSuccessUrl("/home", true)
        //      		.loginPage("/login");

        // https://www.baeldung.com/spring-security-logout
        // https://www.baeldung.com/spring-security-manual-logout
        
        // ************************************************************************
        http.logout()
        // ************************************************************************
        		// list of cookies to be deleted when the user logs out
        		.deleteCookies("JSESSIONID")
        		// Spring security default logout value
        		.logoutUrl("/app-logout")
        		.logoutSuccessUrl("/login");


     // ************************************************************************
        // Cross-Site Request Forgery (CSRF) is an attack that forces
        // authenticated users to submit a request to a Web application
        // against which they are currently authenticated. CSRF attacks
        // exploit the trust a Web application has in an authenticated user.

        // CSRF protection is enabled by default in the Java configuration.
        // https://stackoverflow.com/questions/52363487/what-is-the
        // -reason-to-disable-csrf-in-spring-boot-web-application

        // CSRF - disable it because it was interfering with our existing
        // authentication mechanism.
        http.csrf().disable();
     // ************************************************************************
    }


}