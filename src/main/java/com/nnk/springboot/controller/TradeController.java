package com.nnk.springboot.controller;

import javax.validation.Valid;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nnk.springboot.dto.TradeDTO;
import com.nnk.springboot.service.ITradeService;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Controller
@RequestMapping("/trade")
public class TradeController {

	private final ITradeService tradeService;

    // ********************************************************************

    public TradeController(final ITradeService tradeService) {
        this.tradeService = tradeService;
    }

    // ********************************************************************

	@GetMapping("/list")
    public String home(final Model model)
    {

    	log.info("Request GET for trade/list received");

        boolean adminSession = SecurityContextHolder
        		.getContext()
        		.getAuthentication()
        		.getAuthorities().toString()
        		.equals("[ADMIN]");

        if (adminSession){

        	log.info("Session ADMIN " + adminSession
        			+ " USER LIST is accessible" );

        	model.addAttribute("admin", "admin");
        }

		model.addAttribute("trades", tradeService.getAllTrade());

    	log.info("Request GET for trade/list reponse SUCCESS(200 OK)");

        return "trade/list";
    }

    // ********************************************************************

    @GetMapping("/add")
    public String addTrade(final TradeDTO tradeDTO) {

    	log.info("Request GET for trade/add received");

    	log.info("Request GET for trade/add reponse SUCCESS(200 OK)");

    	return "trade/add";
    }

    // ********************************************************************

    @PostMapping("/validate")
    public String validate(
    		@Valid final TradeDTO tradeDTO,
    		final BindingResult result,
    		final Model model) {

    	log.info("Request post for trade/validate received");

    	if (result.hasErrors()) {

        	log.error("Request post for trade/validate Error(s) {} ", result);

    		return "trade/add";
        }
        tradeService.addTrade(tradeDTO);

       	log.info("Request post for trade/validate SUCCESS");

        return "redirect:/trade/list";
    }


    // ********************************************************************

    @GetMapping("/update/{id}")
    public String showUpdateForm(
    		@PathVariable("id") final Integer id,
    		final Model model) {

       	log.info("Request GET trade/update/{id} received - ID: {}", id);

    	TradeDTO tradeDTO = tradeService.getTradeById(id);

    	model.addAttribute("tradeDTO", tradeDTO);

    	log.info("Request GET for trade/update{id} SUCCESS");

    	return "trade/update";
    }

    // ********************************************************************

    @PostMapping("/update/{id}")
    public String updateTrade(
    		@PathVariable("id") final Integer id,
    		@Valid final TradeDTO tradeDTO,
    		final BindingResult result,
    		final Model model) {

       	log.info("Request POST trade/update/{id} received - ID: {}", id);

        if (result.hasErrors()) {

        	log.error("Request post for trade/update{id} Error(s) {} ", result);

        	model.addAttribute("tradeDTO", tradeDTO);
            model.addAttribute(id);

          	log.info("Request POST trade/update/{id} SUCCESS for - ID: {}", id);

        	return "trade/update";
        }

        tradeService.updateTrade(id, tradeDTO);

    	log.info("Request POST for trade/update{id} SUCCESS");

        return "redirect:/trade/list";
    }

    // ********************************************************************

    @GetMapping("/delete/{id}")
    public String deleteTrade(
    		@PathVariable("id") final Integer id,
    		final Model model) {

       	log.info("Request DELETE trade/delete/{id} received - ID: {}", id);

    	tradeService.deleteTrade(id);

    	log.info("Request DELETE for trade/delete/{} SUCCESS", id);

    	return "redirect:/trade/list";
    }

    // ********************************************************************

}
