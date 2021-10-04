package com.nnk.springboot.controller;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.dto.TradeDTO;
import com.nnk.springboot.service.ITradeService;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Controller
public class TradeController {

	private final ITradeService tradeService;

    // ********************************************************************

    public TradeController(final ITradeService tradeService) {
        this.tradeService = tradeService;
    }

    // ********************************************************************

	@RequestMapping("/trade/list")
    public String home(final Model model)
    {

    	log.info("Request GET for trade/list received");

		model.addAttribute("trades", tradeService.getAllTrade());

    	log.info("Request GET for trade/list reponse SUCCESS(200 OK)");

        return "trade/list";
    }

    // ********************************************************************

    @GetMapping("/trade/add")
    public String addUser(final TradeDTO tradeDTO) {

    	log.info("Request GET for trade/add received");

    	log.info("Request GET for trade/add reponse SUCCESS(200 OK)");

    	return "trade/add";
    }

    // ********************************************************************

    @PostMapping("/trade/validate")
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

    @GetMapping("/trade/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        // TODO: get Trade by Id and to model then show to the form
        return "trade/update";
    }

    // ********************************************************************

    @PostMapping("/trade/update/{id}")
    public String updateTrade(@PathVariable("id") Integer id, @Valid Trade trade,
                             BindingResult result, Model model) {
        // TODO: check required fields, if valid call service to update Trade and return Trade list
        return "redirect:/trade/list";
    }

    // ********************************************************************

    @GetMapping("/trade/delete/{id}")
    public String deleteTrade(@PathVariable("id") Integer id, Model model) {
        // TODO: Find Trade by Id and delete the Trade, return to Trade list
        return "redirect:/trade/list";
    }

    // ********************************************************************

}
