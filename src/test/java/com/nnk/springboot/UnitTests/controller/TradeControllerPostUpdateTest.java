package com.nnk.springboot.UnitTests.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
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

@DisplayName("Controller < TRADE > POST UPDATE - UNIT TESTS")
@ExtendWith(SpringExtension.class)
@WebMvcTest(TradeController.class)
class TradeControllerPostUpdateTest {


    @MockBean
    private TradeService tradeService;

    @MockBean
    private UserDetailsService userDetailsService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    private ObjectMapper objectMapper;
	
    private static TradeDTO testTradeDTO1, testTradeDTO2, tradeUpdateDTO;
 
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

       tradeUpdateDTO = TradeDTO.builder()
        		.tradeId(2)
        		.account("Account")
        		.type("Type")
        		.buyQuantity(10.0)
        		.build();
        
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }
    
  	// ********************************************************************


    @DisplayName(" Url request /trade/update/{id} - VALID "
    		+ " - Given a Trade,"
    		+ " when POST /trade/update/{id} action request,"
    		+ " then returns redirect /trade/update/{id} page")    
    @Test
    public void testPostTradeUpdate() throws Exception {

    	when(tradeService
    			.updateTrade(anyInt(), any(TradeDTO.class)))
    	.thenReturn(tradeUpdateDTO);
        
        mockMvc.perform(MockMvcRequestBuilders.post("/trade/update/1")
        .sessionAttr("tradeDTO", testTradeDTO1)
        .param("account", testTradeDTO1.getAccount())
        .param("type", testTradeDTO1.getType())
        .param("buyQuantity", testTradeDTO1.getBuyQuantity().toString()))
        .andExpect(model().hasNoErrors())
        .andExpect(model().size(0))
        .andExpect(model().attributeDoesNotExist("tradeDTO"))
        .andExpect(redirectedUrl("/trade/list"))
        .andExpect(status().is(302));

        verify(tradeService, times(1)).updateTrade(anyInt(), any(TradeDTO.class));
    }

    // ********************************************************************


    @DisplayName(" Url request /trade/update/{id} - EmptyAccount "
    		+ " - Given a Trade - EmptyAccount,"
    		+ " when POST /trade/update/{id} action request,"
    		+ " then returns error & redirect /trade/update/{id} page")    
    @Test
    public void testPostTradeUpdateEmptyAccount() throws Exception {
    	when(tradeService.getAllTrade()).thenReturn(tradeDTOList);
//    	when(tradeService.updateTrade(anyInt(), any(TradeDTO.class))).thenReturn(any(TradeDTO.class));
        
    	MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/trade/update/1")
        .sessionAttr("tradeDTO", testTradeDTO1)
        .param("account", "")
        .param("type", testTradeDTO1.getType())
        .param("buyQuantity", testTradeDTO1.getBuyQuantity().toString()))
        .andExpect(model().hasErrors())
        .andExpect(model().size(2))
        .andExpect(model().attributeExists("tradeDTO"))
        .andExpect(view().name("trade/update"))
        .andExpect(status().is(200))
        .andReturn();

        verify(tradeService, times(0)).getAllTrade();
        verify(tradeService, times(0)).updateTrade(anyInt(), any(TradeDTO.class));

        String content = result.getResponse().getContentAsString();
        
        assertThat(content).contains("Account is mandatory");
        assertThat(content).contains("Should be alphanumeric and minimum more than 2 characters");
    }

    // ********************************************************************
 
    
}