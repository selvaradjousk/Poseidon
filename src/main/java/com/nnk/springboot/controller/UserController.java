package com.nnk.springboot.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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

@Log4j2
@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
    private final IUserService userService;
    //    private UserService userService;

    
	// ********************************************************************
    

	public UserController(
    		final IUserService userService) {
        this.userService = userService;
    }

    
	// ********************************************************************
    

    @GetMapping("/list")
    public String home(Model model)
    {
        model.addAttribute(
        		"users",
        		userService.getAllUser());

        return "user/list";
    }

    
	// ********************************************************************
    
    @GetMapping("/add")
    public String addUser(UserDTO bid) {

    	return "user/add";
    }
    
	// ********************************************************************
    
    @PostMapping("/validate")
    public String validate(
    		@Valid UserDTO userDTO,
    		BindingResult result,
    		Model model) {

    	if (!result.hasErrors()) {

        	// BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        	// user.setPassword(encoder.encode(user.getPassword()));
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
    
	// ********************************************************************
    
    @GetMapping("/update/{id}")
    public String showUpdateForm(
    		@PathVariable("id") Integer id,
    		Model model) {

    	UserDTO userDTO = userService.getUserById(id);

    	// .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
    	// user.setPassword("");

    	model.addAttribute("userDTO", userDTO);

        return "user/update";
    }
    
	// ********************************************************************
    
    @PostMapping("/update/{id}")
    public String updateUser(
    		@PathVariable("id") Integer id,
    		@Valid UserDTO userDTO,
    		BindingResult result,
    		Model model) {

    	if (result.hasErrors()) {
            return "user/update";
        }

//        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
//        user.setPassword(encoder.encode(user.getPassword()));
//        user.setId(id);

        try {
        	userService.updateUser(id, userDTO);

			model.addAttribute(
					"users",
					userService.getAllUser());

        	return "redirect:/user/list";

        } catch (DataAlreadyExistsException e){

        	model.addAttribute("error", e);

        	return "/user/update";
        }
    }


    // ********************************************************************
    
    @GetMapping("/delete/{id}")
    public String deleteUser(
    		@PathVariable("id") Integer id,
    		Model model) {

    	userService.getUserById(id);

//    	.orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));

    	userService.deleteUser(id);

		model.addAttribute(
				"users",
				userService.getAllUser());

    	return "redirect:/user/list";

    }
    
	// ********************************************************************
    

}
