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

import com.nnk.springboot.domain.RuleName;
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
    public String addRuleForm(RuleName bid) {
        return "ruleName/add";
    }

  	// ********************************************************************


    @PostMapping("/validate")
    public String validate(@Valid RuleName ruleName, BindingResult result, Model model) {
        // TODO: check data valid and save to db, after saving return RuleName list
        return "ruleName/add";
    }

  	// ********************************************************************


    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        // TODO: get RuleName by Id and to model then show to the form
        return "ruleName/update";
    }

  	// ********************************************************************

    @PostMapping("/update/{id}")
    public String updateRuleName(@PathVariable("id") Integer id, @Valid RuleName ruleName,
                             BindingResult result, Model model) {
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
