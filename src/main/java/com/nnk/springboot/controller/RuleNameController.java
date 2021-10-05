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

import com.nnk.springboot.dto.RuleNameDTO;
import com.nnk.springboot.service.IRuleNameService;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Controller
@RequestMapping("/ruleName")
public class RuleNameController {

    @Autowired
    private final IRuleNameService ruleNameService;

  	// ********************************************************************

    public RuleNameController(
    		final IRuleNameService ruleNameService) {
        this.ruleNameService = ruleNameService;
    }


  	// ********************************************************************


    @RequestMapping("/list")
    public String home(final Model model) {

    	log.info("Request GET for ruleName/list received");

    	model.addAttribute("ruleNames", ruleNameService
    			.getAllRuleName());

    	log.info("Request GET for ruleName/list reponse SUCCESS(200 OK)");

    	return "ruleName/list";
    }

  	// ********************************************************************


    @GetMapping("/add")
    public String addRuleForm(final RuleNameDTO ruleNameDTO) {

    	log.info("Request GET for ruleName/add received");

    	log.info("Request GET for ruleName/add reponse SUCCESS(200 OK)");

    	return "ruleName/add";
    }

  	// ********************************************************************


    @PostMapping("/validate")
    public String validate(
    		final @Valid RuleNameDTO ruleNameDTO,
    		final BindingResult result,
    		final Model model) {
        // TODO: check data valid and save to db, after saving return RuleName list
        return "ruleName/add";
    }

  	// ********************************************************************


    @GetMapping("/update/{id}")
    public String showUpdateForm(
    		@PathVariable("id") final Integer id,
    		final Model model) {

       	log.info("Request GET ruleName/update/{id} received - ID: {}", id);

    	RuleNameDTO ruleNameDTO = ruleNameService.getRuleNameById(id);

    	model.addAttribute("ruleNameDTO", ruleNameDTO);

    	log.info("Request GET for ruleName/update{id} SUCCESS");

    	return "ruleName/update";
    }

  	// ********************************************************************

    @PostMapping("/update/{id}")
    public String updateRuleName(
    		@PathVariable("id") final Integer id,
    		@Valid final RuleNameDTO ruleNameDTO,
    		final BindingResult result,
    		final Model model) {
        // TODO: check required fields, if valid call service to update RuleName and return RuleName list
        return "redirect:/ruleName/list";
    }

  	// ********************************************************************

    @GetMapping("/delete/{id}")
    public String deleteRuleName(@PathVariable("id") Integer id, Model model) {

       	log.info("Request DELETE ruleName/delete/{id} received - ID: {}", id);

    	ruleNameService.deleteRuleName(id);

    	log.info("Request DELETE for ruleName/delete/{} SUCCESS", id);

    	return "redirect:/ruleName/list";
    }

    // ********************************************************************

}
