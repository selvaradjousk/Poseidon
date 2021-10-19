package com.nnk.springboot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Controller
public class HomeController {

	// ############################################################
//	@RequestMapping("/")
	@GetMapping("/")
	public String home(Model model) {

		log.info("GET Request @GetMapping(\"/\")");

		return "home";
	}


	// ############################################################

    @RequestMapping("/admin/home")
    public String adminHome(Model model) {
        return "redirect:/bidList/list";
    }

    // ############################################################	
}
