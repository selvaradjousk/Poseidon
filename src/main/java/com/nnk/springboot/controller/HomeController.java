package com.nnk.springboot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.log4j.Log4j2;

/** The Home Controller class. */
@Log4j2
@Controller
public class HomeController {

	// ############################################################
/**
	 * Home.
	 *
	 * @param model the model
	 * @return the string
	 */
	@GetMapping("/")
	public String home(final Model model) {

		log.info("GET Request @GetMapping(\"/\")");

		return "home";
	}


	// ############################################################

    /**
	 * Admin home.
	 *
	 * @param model the model
	 * @return the string
	 */
	@RequestMapping("/admin/home")
    public String adminHome(final Model model) {

		return "redirect:/bidList/list";

	}

    // ############################################################
}
