package com.nnk.springboot.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.nnk.springboot.config.JwtUtils;
import com.nnk.springboot.config.LoginRequest;
import com.nnk.springboot.repository.UserRepository;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Controller
//@RequestMapping("app")
public class LoginController {


    @Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UserRepository userRepository;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	JwtUtils jwtUtils;

	//    @Autowired

	//    private UserRepository userRepository;

	// ******************************************************************
    @GetMapping("login")
    public ModelAndView login() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("login");
        return mav;
    }
    // ******************************************************************

    @RequestMapping(value = "/login", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public String authenticateUser(
			@Valid final LoginRequest loginRequest,
			final HttpServletResponse response,
			final Model model) {

    	log.debug("POST LOGIN CALLED ***************");

        try {
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(
						loginRequest.getUsername(),
						loginRequest.getPassword()));

		log.info("*********** Authentication: " + authentication + "Login Request: " + loginRequest + "Response: " + response + "Model: " + model  + "AUTHENTICATION: " +  authentication);


		SecurityContextHolder.getContext().setAuthentication(authentication);

		String jwt = jwtUtils.generateJwtToken(authentication);

    	log.debug("JWT" + jwt+ "*******************");       

		Cookie cookie = new Cookie("token", jwt);
        cookie.setSecure(true);
        cookie.setHttpOnly(true);
        cookie.setPath("/bitList/list");
        cookie.setMaxAge(10000);
        response.addCookie(cookie);

        //		MyUserDetails userDetails = (MyUserDetails) authentication.getPrincipal();		
//		List<String> roles = userDetails.getAuthorities().stream()
//				.map(item -> item.getAuthority())
//				.collect(Collectors.toList());

    	log.debug("*********************COOKIE: " + cookie);

        return "bidList/list";

        } catch (Exception e) {

    	String errorMessage = "Invalid credentials";

    	model.addAttribute("errorMessage",errorMessage);

    	return "login";
    }
}



    // ******************************************************************
    // what way article-details link to user list ???? Need clarity!!!!
    // ******************************************************************    
//    @GetMapping("secure/article-details")
//    public ModelAndView getAllUserArticles() {
//        ModelAndView mav = new ModelAndView();
//        mav.addObject("users", userRepository.findAll());
//        mav.setViewName("user/list");
//        return mav;
//    }

//  @GetMapping("login")
//  public String login() {

//      Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//      if ( authentication == null || authentication instanceof AnonymousAuthenticationToken) {
//          return "login";
//      }
//      return "login";
//  }

  // https://www.bezkoder.com/spring-boot-jwt-authentication/
  //https://www.baeldung.com/spring-security-oauth-principal-authorities-extractor
  // https://stackoverflow.com/questions/57434960/how-do-i-get-user-details-after-user-successfuly-logs-in-with-github-i-want-to
  // https://spring.io/guides/tutorials/spring-boot-oauth2/
  // http://5.9.10.113/58200573/create-custom-oauth-login-page-and-custom-jwt-token
  // https://www.amitph.com/spring-security-oauth2-login/#Github_OAuth2_Login
  // ******************************************************************
  // what way article-details link to user list ???? Need clarity!!!!
  // ******************************************************************    
//  @GetMapping("secure/article-details")
//  public ModelAndView getAllUserArticles() {
//      ModelAndView mav = new ModelAndView();
//      mav.addObject("users", userRepository.findAll());
//      mav.setViewName("user/list");
//      return mav;
//  }

    @GetMapping("error")
    public ModelAndView error(@AuthenticationPrincipal OAuth2User principal) {
        ModelAndView mav = new ModelAndView();

        String errorMessage= "You are not authorized"
        		+ " for the requested data.";

        mav.addObject("errorMsg", errorMessage);

        if (principal != null) {

        	mav.addObject("login", principal
        			.getAttribute("login"));
        }

        mav.setViewName("403");

        log.error("Login redirect 403 error");

        return mav;
    }

    @RequestMapping("/login-error")
    public String loginError(Model model) {

    	log.error("invalid user login attempt");

    	model.addAttribute("loginError", "invalid user credentials or session");

    	log.trace("Display login view");

    	return "login";
    }
}
