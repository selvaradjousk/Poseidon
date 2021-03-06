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

import com.nnk.springboot.dto.BidListDTO;
import com.nnk.springboot.service.IBidListService;

import lombok.extern.log4j.Log4j2;


/** The BidList Controller class. */
@Log4j2
@Controller
@RequestMapping("/bidList")
public class BidListController {

	/** The bid list service. */
	@Autowired
	private final IBidListService bidListService;

	// ############################################################

	/**
	 * Instantiates a new bid list controller.
	 *
	 * @param bidListService the bid list service
	 */
	public BidListController(
			final IBidListService bidListService) {
        this.bidListService = bidListService;
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

    	log.info("Request GET for bidList/list received");

        boolean adminSession = SecurityContextHolder
        		.getContext()
        		.getAuthentication()
        		.getAuthorities().toString()
        		.equals("[ADMIN]");

        if (adminSession){

        	log.info("Session ADMIN USER LIST is accessible" );

        	model.addAttribute("admin", "admin");
        }

		model.addAttribute("bids", bidListService
				.getAllBidList());

    	log.info("Request GET for bidList/list reponse"
    			+ " SUCCESS(200 OK)");

		return "bidList/list";
    }

	// ############################################################

    /**
	 * Adds the bid form.
	 *
	 * @param bid the bid
	 * @return the string
	 */
	@GetMapping("/add")
    public String addBidForm(final BidListDTO bid) {

    	log.info("Request GET for bidList/add received");

    	log.info("Request GET for bidList/add reponse"
    			+ " SUCCESS(200 OK)");

    	return "bidList/add";
    }

	// ############################################################

    /**
	 * Validate.
	 *
	 * @param bidListDTO the bid list DTO
	 * @param result the result
	 * @param model the model
	 * @return the string
	 */
	@PostMapping("/validate")
    public String validate(
    		@Valid final BidListDTO bidListDTO,
    		final BindingResult result,
    		final Model model) {

    	log.info("Request post for bidList/validate"
    			+ " received");

    	if (result.hasErrors()) {

        	log.error("Request post for bidList/validate"
        			+ " Error(s) {} ", result);

    		return "bidList/add";
        }

    	bidListService.addBidList(bidListDTO);

       	log.info("Request post for bidList/validate"
       			+ " SUCCESS");

        return "redirect:/bidList/list";
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

       	log.info("Request GET bidList/update/{id} received"
       			+ " - ID: {}", id);

        BidListDTO bidListDTO = bidListService
        		.getBidListById(id);

        model.addAttribute("bidListDTO", bidListDTO);

    	log.info("Request GET for bidList/update{id}"
    			+ " SUCCESS");

    	return "bidList/update";
    }

	// ############################################################

    /**
	 * Update bid.
	 *
	 * @param id the id
	 * @param bidListDTO the bid list DTO
	 * @param result the result
	 * @param model the model
	 * @return the string
	 */
	@PostMapping("/update/{id}")
    public String updateBid(
    		@PathVariable("id") final Integer id,
    		@Valid final BidListDTO bidListDTO,
    		final BindingResult result,
    		final Model model) {

       	log.info("Request POST bidList/update/{id} received"
       			+ " - ID: {}", id);

        if (result.hasErrors()) {

            model.addAttribute("bidListDTO", bidListDTO);

            model.addAttribute(id);

        	log.error("Request post for bidList/update{id}"
        			+ " Error(s) {} ", result);

            return "bidList/update";
        }

        bidListService.updateBidList(id, bidListDTO);

        log.info("Request POST bidList/update/{id} SUCCESS for"
        		+ " - ID: {}", id);

        return "redirect:/bidList/list";
    }

	// ############################################################

    /**
	 * Delete bid.
	 *
	 * @param id the id
	 * @param model the model
	 * @return the string
	 */
	@GetMapping("/delete/{id}")
    public String deleteBid(
    		@PathVariable("id") final Integer id,
    		final Model model) {

       	log.info("Request DELETE bidList/delete/{id} received"
       			+ " - ID: {}", id);

    	bidListService.deleteBidList(id);

    	log.info("Request DELETE for bidList/delete/{}"
    			+ " SUCCESS", id);

    	return "redirect:/bidList/list";
    }

	// ############################################################

}
