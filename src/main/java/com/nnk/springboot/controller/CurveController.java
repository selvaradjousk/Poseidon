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

import com.nnk.springboot.dto.CurvePointDTO;
import com.nnk.springboot.service.ICurvePointService;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Controller
@RequestMapping("/curvePoint")
public class CurveController {

    @Autowired
    private final ICurvePointService curvePointService;

  	// ********************************************************************

    public CurveController(
    		final ICurvePointService curvePointService) {
        this.curvePointService = curvePointService;
    }

  	// ********************************************************************

    @GetMapping("/list")
    public String home(final Model model)
    {

    	log.info("Request GET for curvePoint/list received");

    	model.addAttribute("curvePoints", curvePointService
    			.getAllCurvePoint());

    	log.info("Request GET for curvePoint/list reponse SUCCESS(200 OK)");

    	return "curvePoint/list";
    }

  	// ********************************************************************

    @GetMapping("/add")
    public String addBidForm(final CurvePointDTO curvePointDTO) {
        return "curvePoint/add";
    }

  	// ********************************************************************

    @PostMapping("/validate")
    public String validate(
    		@Valid final CurvePointDTO curvePointDTO,
    		final BindingResult result,
    		final Model model) {
        // TODO: check data valid and save to db, after saving return Curve list
        return "curvePoint/add";
    }

  	// ********************************************************************

    @GetMapping("/update/{id}")
    public String showUpdateForm(
    		@PathVariable("id") final Integer id,
    		final Model model) {
        // TODO: get CurvePoint by Id and to model then show to the form
        return "curvePoint/update";
    }

  	// ********************************************************************

    @PostMapping("/update/{id}")
    public String updateBid(
    		@PathVariable("id") final Integer id,
    		@Valid final CurvePointDTO curvePointDTO,
    		final BindingResult result,
    		final Model model) {
        // TODO: check required fields, if valid call service to update Curve and return Curve list
        return "redirect:/curvePoint/list";
    }

  	// ********************************************************************

    @GetMapping("/delete/{id}")
    public String deleteBid(
    		@PathVariable("id") final Integer id,
    		final Model model) {

       	log.info("Request DELETE curvePoint/delete/{id} received - ID: {}", id);

    	curvePointService.deleteCurvePoint(id);

    	log.info("Request DELETE for curvePoint/delete/{} SUCCESS", id);

    	return "redirect:/curvePoint/list";
    }

    // ********************************************************************

}
