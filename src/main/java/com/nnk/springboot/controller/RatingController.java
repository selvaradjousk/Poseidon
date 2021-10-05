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

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.dto.RatingDTO;
import com.nnk.springboot.service.IRatingService;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Controller
@RequestMapping("/rating")
public class RatingController {


    @Autowired
    private final IRatingService ratingService;

  	// ********************************************************************

    public RatingController(
    		final IRatingService ratingService) {
        this.ratingService = ratingService;
    }

  	// ********************************************************************

    @GetMapping("/list")
    public String home(final Model model)
    {

    	log.info("Request GET for rating/list received");

    	model.addAttribute("ratings", ratingService
    			.getAllRating());

    	log.info("Request GET for rating/list reponse SUCCESS(200 OK)");

    	return "rating/list";
    }

  	// ********************************************************************


    @GetMapping("/add")
    public String addRatingForm(final RatingDTO ratingDTO) {

    	log.info("Request GET for rating/add received");

    	log.info("Request GET for rating/add reponse SUCCESS(200 OK)");

    	return "rating/add";
    }

    // ********************************************************************

    @PostMapping("/validate")
    public String validate(
    		@Valid final RatingDTO ratingDTO,
    		final BindingResult result,
    		final Model model) {
        // TODO: check data valid and save to db, after saving return Rating list
        return "rating/add";
    }

    // ********************************************************************

    @GetMapping("/update/{id}")
    public String showUpdateForm(
    		@PathVariable("id") final Integer id,
    		final Model model) {

       	log.info("Request GET rating/update/{id} received - ID: {}", id);

    	RatingDTO ratingDTO = ratingService.getRatingById(id);

    	model.addAttribute("ratingDTO", ratingDTO);

    	log.info("Request GET for rating/update/{id} SUCCESS");

    	return "rating/update";
    }

    // ********************************************************************


    @PostMapping("/update/{id}")
    public String updateRating(
    		@PathVariable("id") final Integer id,
    		final @Valid RatingDTO ratingDTO,
    		final BindingResult result,
    		final Model model) {
        // TODO: check required fields, if valid call service to update Rating and return Rating list
        return "redirect:/rating/list";
    }

    // ********************************************************************

    @GetMapping("/delete/{id}")
    public String deleteRating(
    		@PathVariable("id") final Integer id,
    		final Model model) {

       	log.info("Request DELETE rating/delete/{id} received - ID: {}", id);

       	ratingService.deleteRating(id);

    	log.info("Request DELETE for rating/delete/{} SUCCESS", id);

    	return "redirect:/rating/list";
    }

    // ********************************************************************

}
