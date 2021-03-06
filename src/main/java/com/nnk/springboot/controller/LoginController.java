package com.nnk.springboot.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import lombok.extern.log4j.Log4j2;


/** The Login Controller class. */
@Log4j2
@Controller
public class LoginController {



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
	 * Error.
	 *
	 * @param principal the principal
	 * @return the model and view
	 */
	@GetMapping("error")
    public ModelAndView error(
    		@AuthenticationPrincipal final OAuth2User principal) {

		ModelAndView mav = new ModelAndView();

        String errorMessage = "You are not authorized"
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
	 * @return the string login
	 */
	@RequestMapping("/login-error")
    public String loginError(final Model model) {

    	log.error("invalid user login attempt");

    	model.addAttribute("loginError", "invalid user credentials or session");

    	log.trace("Display login view");

    	return "login";
    }

	// ##############################################################



}
