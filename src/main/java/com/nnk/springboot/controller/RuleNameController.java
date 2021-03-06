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

import com.nnk.springboot.dto.RuleNameDTO;
import com.nnk.springboot.service.IRuleNameService;

import lombok.extern.log4j.Log4j2;


/** The RuleName Controller class. */
@Log4j2
@Controller
@RequestMapping("/ruleName")
public class RuleNameController {

    /** The rule name service. */
    @Autowired
    private final IRuleNameService ruleNameService;

	// ############################################################

    /**
	 * Instantiates a new rule name controller.
	 *
	 * @param ruleNameService the rule name service
	 */
	public RuleNameController(
    		final IRuleNameService ruleNameService) {
        this.ruleNameService = ruleNameService;
    }


	// ############################################################


    /**
	 * Home.
	 *
	 * @param model the model
	 * @return the string
	 */
	@RequestMapping("/list")
    public String home(final Model model) {

    	log.info("Request GET for ruleName/list received");

        boolean adminSession = SecurityContextHolder
        		.getContext()
        		.getAuthentication()
        		.getAuthorities().toString()
        		.equals("[ADMIN]");

        if (adminSession) {

        	log.info("Session ADMIN USER LIST is accessible");

        	model.addAttribute("admin", "admin");
        }

    	model.addAttribute("ruleNames", ruleNameService
    			.getAllRuleName());

    	log.info("Request GET for ruleName/list reponse SUCCESS(200 OK)");

    	return "ruleName/list";
    }

	// ############################################################


    /**
	 * Adds the rule form.
	 *
	 * @param ruleNameDTO the rule name DTO
	 * @return the string
	 */
	@GetMapping("/add")
    public String addRuleForm(final RuleNameDTO ruleNameDTO) {

    	log.info("Request GET for ruleName/add received");

    	log.info("Request GET for ruleName/add reponse SUCCESS(200 OK)");

    	return "ruleName/add";
    }

	// ############################################################


    /**
	 * Validate.
	 *
	 * @param ruleNameDTO the rule name DTO
	 * @param result the result
	 * @param model the model
	 * @return the string
	 */
	@PostMapping("/validate")
    public String validate(
    		final @Valid RuleNameDTO ruleNameDTO,
    		final BindingResult result,
    		final Model model) {

    	log.info("Request post for ruleName/validate received");

    	if (result.hasErrors()) {

        	log.error("Request post for ruleName/validate"
        			+ " Error(s) {} ", result);

    		return "ruleName/add";
        }

    	ruleNameService.addRuleName(ruleNameDTO);

       	log.info("Request post for ruleName/validate SUCCESS");

        return "redirect:/ruleName/list";
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

       	log.info("Request GET ruleName/update/{id} received"
       			+ " - ID: {}", id);

    	RuleNameDTO ruleNameDTO = ruleNameService
    			.getRuleNameById(id);

    	model.addAttribute("ruleNameDTO", ruleNameDTO);

    	log.info("Request GET for ruleName/update{id} SUCCESS");

    	return "ruleName/update";
    }

	// ############################################################

    /**
	 * Update rule name.
	 *
	 * @param id the id
	 * @param ruleNameDTO the rule name DTO
	 * @param result the result
	 * @param model the model
	 * @return the string
	 */
	@PostMapping("/update/{id}")
    public String updateRuleName(
    		@PathVariable("id") final Integer id,
    		@Valid final RuleNameDTO ruleNameDTO,
    		final BindingResult result,
    		final Model model) {

       	log.info("Request POST ruleName/update/{id} received"
       			+ " - ID: {}", id);

        if (result.hasErrors()) {

        	log.error("Request post for ruleName/update{id}"
        			+ " Error(s) {} ", result);

        	model.addAttribute("ruleNameDTO", ruleNameDTO);
            model.addAttribute(id);

          	log.info("Request POST ruleName/update/{id}"
          			+ " SUCCESS for - ID: {}", id);

        	return "ruleName/update";
        }

        ruleNameService.updateRuleName(id, ruleNameDTO);

    	log.info("Request POST for ruleName/update{id} SUCCESS");

        return "redirect:/ruleName/list";
    }

	// ############################################################

    /**
	 * Delete rule name.
	 *
	 * @param id the id
	 * @param model the model
	 * @return the string
	 */
	@GetMapping("/delete/{id}")
    public String deleteRuleName(
    		@PathVariable("id") final Integer id,
    		final Model model) {

       	log.info("Request DELETE ruleName/delete/{id} received - ID: {}", id);

    	ruleNameService.deleteRuleName(id);

    	log.info("Request DELETE for ruleName/delete/{} SUCCESS", id);

    	return "redirect:/ruleName/list";
    }

	// ############################################################

}
