package com.nnk.springboot.config;



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
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

    	log.info("auth.userDetailsService(userDetailsService) - function called");

    	auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());

    	log.info("auth.userDetailsService(userDetailsService) - function executed");

    }
    // ************************************************************************
    // ************************************************************************



    @Override
    public void configure(HttpSecurity http) throws Exception{

        // ************************************************************************
    	http.authorizeRequests()
        // ************************************************************************

                .antMatchers("/")
                	.permitAll()

                	// ----------------
                	// permit all
                	// -----------------
                .antMatchers("/css/**")
                	.permitAll()

                .antMatchers("/img/**")
                	.permitAll()

                .antMatchers("/register/**")
                	.permitAll()


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
	            	// admin
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