package com.nnk.springboot.controller;

import javax.validation.Valid;

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

@Log4j2
@Controller
@RequestMapping("/bidList")
public class BidListController {

	private final IBidListService bidListService;

    // ********************************************************************

	public BidListController(final IBidListService bidListService) {
        this.bidListService = bidListService;
    }

    // ********************************************************************

	@GetMapping("/list")
    public String home(final Model model)
    {

    	log.info("Request GET for bidList/list received");

		model.addAttribute("bids", bidListService.getAllBidList());

    	log.info("Request GET for bidList/list reponse SUCCESS(200 OK)");

		return "bidList/list";
    }

    // ********************************************************************

    @GetMapping("/add")
    public String addBidForm(BidListDTO bid) {

    	log.info("Request GET for bidList/add received");

    	log.info("Request GET for bidList/add reponse SUCCESS(200 OK)");

    	return "bidList/add";
    }

    // ********************************************************************

    @PostMapping("/validate")
    public String validate(@Valid BidListDTO bid, BindingResult result, Model model) {
        // TODO: check data valid and save to db, after saving return bid list
        return "bidList/add";
    }

    // ********************************************************************

    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        // TODO: get Bid by Id and to model then show to the form
        return "bidList/update";
    }

    // ********************************************************************

    @PostMapping("/update/{id}")
    public String updateBid(@PathVariable("id") Integer id, @Valid BidListDTO bidList,
                             BindingResult result, Model model) {
        // TODO: check required fields, if valid call service to update Bid and return list Bid
        return "redirect:/bidList/list";
    }

    // ********************************************************************

    @GetMapping("/delete/{id}")
    public String deleteBid(
    		@PathVariable("id") final Integer id,
    		final Model model) {

       	log.info("Request DELETE bidList/delete/{id} received - ID: {}", id);

    	bidListService.deleteBidList(id);

    	log.info("Request DELETE for bidList/delete/{} SUCCESS", id);

    	return "redirect:/bidList/list";
    }

    // ********************************************************************

}
