package com.nnk.springboot.UnitTests.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nnk.springboot.controller.TradeController;
import com.nnk.springboot.dto.TradeDTO;
import com.nnk.springboot.service.TradeService;

@DisplayName("Controller < TRADE > - UNIT TESTS")
@ExtendWith(SpringExtension.class)
@WebMvcTest(TradeController.class)
class TradeControllerPostValidateTest {


    @MockBean
    private TradeService tradeService;

    @MockBean
    private UserDetailsService userDetailsService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    private ObjectMapper objectMapper;
	
    private static TradeDTO testTradeDTO1, testTradeDTO2;
 
    private static List<TradeDTO> tradeDTOList;

    @BeforeEach
    public void setUp() {

        objectMapper = new ObjectMapper();
        testTradeDTO1 = TradeDTO.builder()
        		.tradeId(1)
        		.account("Account")
        		.type("Type")
        		.buyQuantity(10.0)
        		.build();
        
        testTradeDTO2 = TradeDTO.builder()
        		.tradeId(2)
        		.account("Account")
        		.type("Type")
        		.buyQuantity(10.0)
        		.build();
        
        tradeDTOList = Arrays.asList(testTradeDTO1, testTradeDTO2); 
        
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }
    
  	// ********************************************************************


    @DisplayName(" Url request /trade/validate - "
    		+ " - Given a Trade,"
    		+ " when POST /trade/validate action request,"
    		+ " then returns redirect /trade/validate page")    
    @Test
    public void testPostTradeValidate() throws Exception {
    	when(tradeService.getAllTrade()).thenReturn(tradeDTOList);
    	when(tradeService.addTrade(any(TradeDTO.class))).thenReturn(any(TradeDTO.class));
        
        mockMvc.perform(MockMvcRequestBuilders.post("/trade/validate")
        .sessionAttr("tradeDTO", testTradeDTO1)
        .param("account", testTradeDTO1.getAccount())
        .param("type", testTradeDTO1.getType())
        .param("buyQuantity", testTradeDTO1.getBuyQuantity().toString()))
        .andExpect(model().hasNoErrors())
        .andExpect(model().size(0))
        .andExpect(model().attributeDoesNotExist("tradeDTO"))
        .andExpect(redirectedUrl("/trade/list"))
        .andExpect(status().is(302));

        verify(tradeService, times(1)).getAllTrade();
        verify(tradeService, times(1)).addTrade(any(TradeDTO.class));
    }

    // ********************************************************************

    
    
}
