package com.nnk.springboot.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Controller
public class HomeController {


//	@RequestMapping("/")
	@GetMapping("/")
	public String home(Model model)
	{

		log.info("GET Request @GetMapping(\"/\")");

		return "home";
	}

//	@RequestMapping("/admin/home")
	@GetMapping("/admin/home")
	public String adminHome(Authentication auth)
	{
	    for (GrantedAuthority role : auth.getAuthorities()) {
		        if (role.getAuthority().equals("ROLE_ADMIN")) {
		          return "redirect:/user/list";
		        }
		    }
		return "redirect:/bidList/list";
		   
				//  boolean adminSession = SecurityContextHolder
				//	.getContext()
				//	.getAuthentication()
				//	.getAuthorities().toString()
				//	.equals("[ADMIN]");
				//
				//if (adminSession) {
				//model.addAttribute("admin", "admin");	  
				//}
				
				
				//for (GrantedAuthority role : auth.getAuthorities()) {
				//if (role.getAuthority().equals("ROLE_ADMIN")) {
				//return "redirect:/user/list";
				//}
				//} 
	}
}
