package com.nnk.springboot.config;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    MyUserDetailsService userDetailsService;


    // ##############################################################

    @Bean
	public AuthTokenFilter authenticationJwtTokenFilter() {

    	log.info("### Calling authenticationJwtTokenFilter()");

		return new AuthTokenFilter();
	}
 
 
    // ##############################################################

    @Bean
    public PasswordEncoder passwordEncoder(){

    	log.info("### Calling login password Encoder Bcrypt");

        return new BCryptPasswordEncoder();
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


    // Helper class that eases the set up of UserDetailService
    @Override
    protected void configure(final AuthenticationManagerBuilder auth)
    		throws Exception {

    	log.info("#### Calling auth.userDetailsService(userDetailsService)");

    	auth.userDetailsService(userDetailsService)
    			.passwordEncoder(passwordEncoder());

    	log.info("#### Calling auth.userDetailsService(userDetailsService)");

    }
    // ##############################################################


    // Allows configuring web based security for specific http requests
    @Override
    public void configure(HttpSecurity http) throws Exception{

    	log.info("#### configure(HttpSecurity http) initialized");

        // ##############################################################
        // Enable CORS and disable CSRF
        http = http.cors().and().csrf().disable();
        // ##############################################################

    	log.info("#### http.cors().and().csrf().disable() ... settings DONE");

        // ##############################################################
        // Set unauthorized requests exception handler
        // ##############################################################
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

    	log.info("#### http.exceptionHandling() ... settings DONE");

        // ##############################################################
    	// SET PERMISSION ON ENDPOINTS
    	http.authorizeRequests()
        // ##############################################################

	    			// ----------------
	    			// PUBLIC ENDPOINTS
	    			// -----------------
	                .antMatchers("/").permitAll()
	                .antMatchers("/css/**").permitAll()
	                .antMatchers("/img/**").permitAll()
	                .antMatchers("/register/**").permitAll()
	                .antMatchers("/login/**").permitAll()
	                .antMatchers("/validate").permitAll()
	                

	            	// -----------------
	            	// PRIVATE ADMIN SPECIFIC ACCESS ENDPOINTS
	            	// -----------------
                   .antMatchers("/admin/*").hasAuthority("ADMIN")
                   .antMatchers("/user/*").hasAuthority("ADMIN")


                	// PRIVATE ENDPOINTS for USERS & ADMIN
                   .anyRequest().authenticated()
                ;

    	log.info("#### http.authorizeRequests() ... settings DONE");


        // ##############################################################   	
    	// SET LOGIN CONFIGURATION
    	http.formLogin()
        // ##############################################################

    			// when authentication is required, redirect the browser to /login
    			.loginPage("/login")

    			// process the submitted credentials when sent the specified path
    			// i.e. URL to validate username and password
        		.loginProcessingUrl("/login")
        		
        		// redirect URL on successful login
        		.defaultSuccessUrl("/bidList/list")

        		// when authentication attempt fails,
        		// redirect the browser to /login?error
        		.failureUrl("/login?error=true").permitAll();

    	log.info("#### http.formLogin() ... settings DONE");


        // ##############################################################   	
    	http.oauth2Login()
        // ##############################################################
        		.loginPage("/login")
        		// redirect the user to the home page on login success

        		.defaultSuccessUrl("/bidList/list");

    	log.info("#### http.oauth2Login() ... settings DONE");


        // ##############################################################
        http.logout()
        // ##############################################################

        		// list of cookies to be deleted when the user logs out
        		.deleteCookies("JSESSIONID")

        		// Spring security default logout value
        		.logoutUrl("/app-logout")

        		// when we successfully logout, redirect the browser to
        		// /login?logout
        		.logoutSuccessUrl("/login")

        		// removes the Authentication from the SecurityContext
        		// to prevent issues with concurrent requests
        		 .clearAuthentication(true)

        		 // invalidate the HttpSession at the time of logout
        		.invalidateHttpSession(true);

    	log.info("#### http.logout() ... settings DONE");


        // ##############################################################
        // Cross-Site Request Forgery (CSRF) is an attack that forces
        // authenticated users to submit a request to a Web application
        // against which they are currently authenticated. CSRF attacks
        // exploit the trust a Web application has in an authenticated user.

        // CSRF protection is enabled by default in the Java configuration.
        // https://stackoverflow.com/questions/52363487/what-is-the
        // -reason-to-disable-csrf-in-spring-boot-web-application

        // CSRF - disabled to prevent interfering with our existing
        // Spring Security authentication mechanism.
        http.csrf().disable();

    	log.info("#### http.csrf().disable() ... settings DONE");

     }


    // ##############################################################

    // Used for configuration settings that impact global security
    // (ignore resources, set debug mode, reject requests by implementing
    // a custom firewall definition)


    @Override
    public void configure( WebSecurity web ) throws Exception
    {
//    	web.ignoring().antMatchers( "/**/*.*" );
        web.ignoring().antMatchers( "/**/*.png", "/**/*.PNG",
        		"/**/*.GIF", "/**/*.gif", "/static/**",
        		"error.html", "/403.html", "/home.html", 
        		"login.html", "register.html");
    }

    // ##############################################################

}