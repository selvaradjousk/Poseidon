package com.nnk.springboot.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nnk.springboot.dto.UserDTO;
import com.nnk.springboot.exception.DataAlreadyExistsException;
import com.nnk.springboot.service.IUserService;

import lombok.extern.log4j.Log4j2;


/** The UserController class. */
@Log4j2
@Controller
@RequestMapping("/user")
public class UserController {

	/** The user service. */
	@Autowired
    private final IUserService userService;

	// ############################################################
    

	/**
	 * Instantiates a new user controller.
	 *
	 * @param userService the user service
	 */
	public UserController(
    		final IUserService userService) {
        this.userService = userService;
    }

    
	// ############################################################
    

    /**
	 * Home.
	 *
	 * @param model the model
	 * @return the string
	 */
	@GetMapping("/list")
    public String home(final Model model) {

    	log.info("Request GET for user/list received");

        checkAdminSessionTrue(model);

    	model.addAttribute(
        		"users",
        		userService.getAllUser());

    	log.info("Request GET for user/list reponse SUCCESS");

    	return "user/list";
    }

    
	// ############################################################
    
	/**
	 * Check admin session true.
	 *
	 * @param model the model
	 */
	public void checkAdminSessionTrue(final Model model) {
		boolean adminSession = SecurityContextHolder
        		.getContext()
        		.getAuthentication()
        		.getAuthorities().toString()
        		.equals("[ADMIN]");

        if (adminSession){

        	log.info("Session ADMIN USER LIST is accessible" );

        	model.addAttribute("admin", "admin");
        }
	}

	// ############################################################

    /**
	 * Adds the user.
	 *
	 * @param bid the bid
	 * @return the string
	 */
	@GetMapping("/add")
    public String addUser(final UserDTO bid) {

    	log.info("Request GET for user/add received");

    	log.info("Request GET for user/add reponse SUCCESS(200 OK)");

    	return "user/add";
    }

	// ############################################################

    /**
	 * Validate.
	 *
	 * @param userDTO the user DTO
	 * @param result the result
	 * @param model the model
	 * @return the string
	 */
	@PostMapping("/validate")
    public String validate(
    		@Valid final UserDTO userDTO,
    		final BindingResult result,
    		final Model model) {

    	if (!result.hasErrors()) {

    		try {

    			userService.addUser(userDTO);

    			model.addAttribute(
    					"users",
    					userService.getAllUser());

    			return  "redirect:/user/list";

    		} catch (DataAlreadyExistsException e){

            	model.addAttribute("error", e);

            	return "user/add";

    		}
    	}

        return "user/add";

    }

	// ############################################################

    /**
	 * Show update form.
	 *
	 * @param id the id
	 * @param model the model
	 * @return the string
	 */
	@GetMapping("/update/{id}")
    public String showUpdateForm(
    		@PathVariable("id") final Integer id,
    		final Model model) {

    	UserDTO userDTO = userService.getUserById(id);

    	// .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
    	// user.setPassword("");

    	model.addAttribute("userDTO", userDTO);

        return "user/update";
    }

	// ############################################################

    /**
	 * Update user.
	 *
	 * @param id the id
	 * @param userDTO the user DTO
	 * @param result the result
	 * @param model the model
	 * @return the string
	 */
	@PostMapping("/update/{id}")
    public String updateUser(
    		@PathVariable("id") final Integer id,
    		@Valid final UserDTO userDTO,
    		final BindingResult result,
    		final Model model) {

    	if (result.hasErrors()) {
            return "user/update";
        }

        	userService.updateUser(id, userDTO);

			model.addAttribute(
					"users",
					userService.getAllUser());

        	return "redirect:/user/list";

    }


	// ############################################################

    /**
	 * Delete user.
	 *
	 * @param id the id
	 * @param model the model
	 * @return the string
	 */
	@GetMapping("/delete/{id}")
    public String deleteUser(
    		@PathVariable("id") final Integer id,
    		final Model model) {

    	userService.getUserById(id);

    	userService.deleteUser(id);

		model.addAttribute(
				"users",
				userService.getAllUser());

    	return "redirect:/user/list";

    }

	// ############################################################


}
