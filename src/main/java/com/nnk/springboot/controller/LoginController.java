package com.nnk.springboot.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.nnk.springboot.config.JwtUtils;
import com.nnk.springboot.config.LoginDTO;
import com.nnk.springboot.repository.UserRepository;

import lombok.extern.log4j.Log4j2;

/**
 * The Class LoginController.
 */
@Log4j2
@Controller
public class LoginController {

	/** The cookie expiration sec. */
	@Value("${poseidon.app.jwtExpirationMs}")
    private int cookieExpirationSec;


    /** The authentication manager. */
    @Autowired
	AuthenticationManager authenticationManager;

	/** The user repository. */
	@Autowired
	UserRepository userRepository;

	/** The encoder. */
	@Autowired
	PasswordEncoder encoder;

	/** The jwt utils. */
	@Autowired
	JwtUtils jwtUtils;




	// ##############################################################


    /**
	 * Login.
	 *
	 * @return the model and view
	 */
	@GetMapping("login")
    public ModelAndView login() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("login");
        return mav;
    }


	// ##############################################################

    /**
	 * Authenticate user.
	 *
	 * @param loginDTO the login DTO
	 * @param result the result
	 * @param response the response
	 * @return the string
	 */
	@PostMapping("/login")
    public String authenticateUser(
    		@Valid final LoginDTO loginDTO,
    		final BindingResult result,
    		final HttpServletResponse response) {


    	log.debug("/login user from LoginRequest: {}",
    			loginDTO.getUsername());
    	log.debug("/login user from LoginRequest: {}",
    			loginDTO.getPassword());

    	log.debug("POST LOGIN CALLED ***************");


        if (result.hasErrors()) {

            return "/login";
        }

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                		loginDTO.getUsername(),
                		loginDTO.getPassword()));

		log.info("*********** Authentication: " + authentication
				+ "Login Request: " + loginDTO
				+ "Response: " + response);



        String jwt = jwtUtils.generateJwtToken(authentication);

    	log.debug("JWT token generated " + jwt+ "*******************");       

        // Creates a cookie and secures it
        Cookie cookie = new Cookie("Token", jwt);
        cookie.setHttpOnly(true);
        cookie.setMaxAge(cookieExpirationSec);
        response.addCookie(cookie);

        
    	log.debug("*********************COOKIE: " + cookie);

        return "redirect:/bidList/list";
    }




	// ##############################################################

    /**
	 * Error.
	 *
	 * @param principal the principal
	 * @return the model and view
	 */
	@GetMapping("error")
    public ModelAndView error(
    		@AuthenticationPrincipal OAuth2User principal) {

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

	// ##############################################################


    /**
	 * Login error.
	 *
	 * @param model the model
	 * @return the string
	 */
	@RequestMapping("/login-error")
    public String loginError(Model model) {

    	log.error("invalid user login attempt");

    	model.addAttribute("loginError", "invalid user credentials or session");

    	log.trace("Display login view");

    	return "login";
    }

	// ##############################################################



}
