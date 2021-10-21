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

import com.nnk.springboot.dto.RatingDTO;
import com.nnk.springboot.service.IRatingService;

import lombok.extern.log4j.Log4j2;


/** The Rating Controller class. */
@Log4j2
@Controller
@RequestMapping("/rating")
public class RatingController {


    /** The rating service. */
    @Autowired
    private final IRatingService ratingService;

	// ############################################################

    /**
	 * Instantiates a new rating controller.
	 *
	 * @param ratingService the rating service
	 */
	public RatingController(
    		final IRatingService ratingService) {
        this.ratingService = ratingService;
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

    	log.info("Request GET for rating/list received");

        boolean adminSession = SecurityContextHolder
        		.getContext()
        		.getAuthentication()
        		.getAuthorities().toString()
        		.equals("[ADMIN]");

        if (adminSession) {

        	log.info("Session ADMIN USER LIST is accessible");

        	model.addAttribute("admin", "admin");
        }

    	model.addAttribute("ratings", ratingService
    			.getAllRating());

    	log.info("Request GET for rating/list reponse SUCCESS(200 OK)");

    	return "rating/list";
    }

	// ############################################################


    /**
	 * Adds the rating form.
	 *
	 * @param ratingDTO the rating DTO
	 * @return the string
	 */
	@GetMapping("/add")
    public String addRatingForm(final RatingDTO ratingDTO) {

    	log.info("Request GET for rating/add received");

    	log.info("Request GET for rating/add reponse SUCCESS(200 OK)");

    	return "rating/add";
    }

	// ############################################################

    /**
	 * Validate.
	 *
	 * @param ratingDTO the rating DTO
	 * @param result the result
	 * @param model the model
	 * @return the string
	 */
	@PostMapping("/validate")
    public String validate(
    		@Valid final RatingDTO ratingDTO,
    		final BindingResult result,
    		final Model model) {

    	log.info("Request post for rating/validate received");

    	if (result.hasErrors()) {

        	log.error("Request post for rating/validate"
        			+ " Error(s) {} ", result);

    		return "rating/add";
        }

    	ratingService.addRating(ratingDTO);

       	log.info("Request post for rating/validate SUCCESS");

        return "redirect:/rating/list";
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

       	log.info("Request GET rating/update/{id} received"
       			+ " - ID: {}", id);

    	RatingDTO ratingDTO = ratingService.getRatingById(id);

    	model.addAttribute("ratingDTO", ratingDTO);

    	log.info("Request GET for rating/update/{id} SUCCESS");

    	return "rating/update";
    }

	// ############################################################


    /**
	 * Update rating.
	 *
	 * @param id the id
	 * @param ratingDTO the rating DTO
	 * @param result the result
	 * @param model the model
	 * @return the string
	 */
	@PostMapping("/update/{id}")
    public String updateRating(
    		@PathVariable("id") final Integer id,
    		final @Valid RatingDTO ratingDTO,
    		final BindingResult result,
    		final Model model) {

       	log.info("Request POST rating/update/{id} received"
       			+ " - ID: {}", id);

        if (result.hasErrors()) {

        	log.error("Request post for rating/update{id}"
        			+ " Error(s) {} ", result);

        	model.addAttribute("ratingDTO", ratingDTO);
            model.addAttribute(id);

          	log.info("Request POST rating/update/{id} SUCCESS for"
          			+ " - ID: {}", id);

        	return "rating/update";
        }

        ratingService.updateRating(id, ratingDTO);

    	log.info("Request POST for rating/update{id} SUCCESS");

        return "redirect:/rating/list";
    }

	// ############################################################

    /**
	 * Delete rating.
	 *
	 * @param id the id
	 * @param model the model
	 * @return the string
	 */
	@GetMapping("/delete/{id}")
    public String deleteRating(
    		@PathVariable("id") final Integer id,
    		final Model model) {

       	log.info("Request DELETE rating/delete/{id} received"
       			+ " - ID: {}", id);

       	ratingService.deleteRating(id);

    	log.info("Request DELETE for rating/delete/{} SUCCESS", id);

    	return "redirect:/rating/list";
    }

	// ############################################################

}
