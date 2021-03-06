package com.nnk.springboot.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.nnk.springboot.dto.UserDTO;
import com.nnk.springboot.exception.DataAlreadyExistsException;
import com.nnk.springboot.service.IUserService;

import lombok.extern.log4j.Log4j2;


/** The Registration Controller class. */
@Log4j2
@Controller
public class RegistrationController {

    /** The user service. */
    @Autowired
    private IUserService userService;

    // ############################################################

    /**
     * Instantiates a new registration controller.
     *
     * @param userService the user service
     */
    public RegistrationController(final IUserService userService) {
		super();
		this.userService = userService;
	}

    // ############################################################

    /**
     * View registration page.
     *
     * @param userDTO the user DTO
     * @return the string
     */
    @GetMapping("/register")
    public String viewRegistrationPage(final UserDTO userDTO) {

    	log.info("Registration GET requested");

    	return "register";
    }

    // ############################################################

    /**
     * Registration validate.
     *
     * @param userDTO the user DTO
     * @param result the result
     * @param model the model
     * @return the string
     */
    @PostMapping("/register")
    public String registrationValidate(
    		@Valid final UserDTO userDTO,
    		final BindingResult result,
    		final Model model) {

    	log.info("Registration data validation requested");

    	if (!result.hasErrors()) {

    		try {
                UserDTO userToAddDTO = userService
                		.addUser(userDTO);

                log.info("New user {} is registered successfully"
                		+ userToAddDTO.getUsername());

                return "redirect:/login";

    		} catch (DataAlreadyExistsException e) {

            	model.addAttribute("error", e);

            	return "register";
            }
        }

        return "register";
    }

	  // ############################################################


}
