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

import com.nnk.springboot.dto.CurvePointDTO;
import com.nnk.springboot.service.ICurvePointService;

import lombok.extern.log4j.Log4j2;


/** The Curve Controller class. */
@Log4j2
@Controller
@RequestMapping("/curvePoint")
public class CurveController {

    /** The curve point service. */
    @Autowired
    private final ICurvePointService curvePointService;

	// ############################################################

    /**
	 * Instantiates a new curve controller.
	 *
	 * @param curvePointService the curve point service
	 */
	public CurveController(
    		final ICurvePointService curvePointService) {
        this.curvePointService = curvePointService;
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

    	log.info("Request GET for curvePoint/list received");

        boolean adminSession = SecurityContextHolder
        		.getContext()
        		.getAuthentication()
        		.getAuthorities().toString()
        		.equals("[ADMIN]");

        if (adminSession) {

        	log.info("Session ADMIN USER LIST is accessible");

        	model.addAttribute("admin", "admin");
        }

    	model.addAttribute("curvePoints", curvePointService
    			.getAllCurvePoint());

    	log.info("Request GET for curvePoint/list reponse"
    			+ " SUCCESS(200 OK)");

    	return "curvePoint/list";
    }

	// ############################################################

    /**
	 * Adds the bid form.
	 *
	 * @param curvePointDTO the curve point DTO
	 * @return the string
	 */
	@GetMapping("/add")
    public String addBidForm(final CurvePointDTO curvePointDTO) {

    	log.info("Request GET for curvePoint/add received");

    	log.info("Request GET for curvePoint/add reponse"
    			+ " SUCCESS(200 OK)");

    	return "curvePoint/add";
    }

	// ############################################################

    /**
	 * Validate.
	 *
	 * @param curvePointDTO the curve point DTO
	 * @param result the result
	 * @param model the model
	 * @return the string
	 */
	@PostMapping("/validate")
    public String validate(
    		@Valid final CurvePointDTO curvePointDTO,
    		final BindingResult result,
    		final Model model) {

    	log.info("Request post for curvePoint/validate"
    			+ " received");

    	if (result.hasErrors()) {

        	log.error("Request post for curvePoint/validate"
        			+ " Error(s) {} ", result);

    		return "curvePoint/add";
        }

    	curvePointService.addCurvePoint(curvePointDTO);

       	log.info("Request post for curvePoint/validate"
       			+ " SUCCESS");

        return "redirect:/curvePoint/list";
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
      	log.info("Request GET curvePoint/update/{id} received"
      			+ " - ID: {}", id);

    	CurvePointDTO curvePointDTO = curvePointService
    			.getCurvePointById(id);

    	model.addAttribute("curvePointDTO", curvePointDTO);

    	log.info("Request GET for curvePoint/update{id}"
    			+ " SUCCESS");

    	return "curvePoint/update";
    }

	// ############################################################

    /**
	 * Update bid.
	 *
	 * @param id the id
	 * @param curvePointDTO the curve point DTO
	 * @param result the result
	 * @param model the model
	 * @return the string
	 */
	@PostMapping("/update/{id}")
    public String updateBid(
    		@PathVariable("id") final Integer id,
    		@Valid final CurvePointDTO curvePointDTO,
    		final BindingResult result,
    		final Model model) {

    	log.info("Request POST curvePoint/update/{id} received"
    			+ " - ID: {}", id);

        if (result.hasErrors()) {

        	log.error("Request post for curvePoint/update{id}"
        			+ " Error(s) {} ", result);

        	model.addAttribute("curvePointDTO", curvePointDTO);
            model.addAttribute(id);

          	log.info("Request POST curvePoint/update/{id}"
          			+ " SUCCESS for - ID: {}", id);

        	return "curvePoint/update";
        }

        curvePointService.updateCurvePoint(id, curvePointDTO);

    	log.info("Request POST for curvePoint/update{id}"
    			+ " SUCCESS");

        return "redirect:/curvePoint/list";
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

       	log.info("Request DELETE curvePoint/delete/{id} received"
       			+ " - ID: {}", id);

    	curvePointService.deleteCurvePoint(id);

    	log.info("Request DELETE for curvePoint/delete/{}"
    			+ " SUCCESS", id);

    	return "redirect:/curvePoint/list";
    }

	// ############################################################

}
