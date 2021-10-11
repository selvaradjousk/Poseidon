package com.nnk.springboot.UnitTests.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
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
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nnk.springboot.config.JwtUtils;
import com.nnk.springboot.config.MyUserDetailsService;
import com.nnk.springboot.controller.TradeController;
import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.dto.TradeDTO;
import com.nnk.springboot.service.TradeService;

@DisplayName("Controller < TRADE > GET LIST - UNIT TESTS")
@ExtendWith(SpringExtension.class)
@WebMvcTest(TradeController.class)
class TradeControllerGetListTest {


    @MockBean
    private TradeService tradeService;

    @MockBean
    private MyUserDetailsService userDetailsService;

    @MockBean
    private JwtUtils jwtUtils;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    private ObjectMapper objectMapper;
	
    private static TradeDTO testTradeDTO1, testTradeDTO2;
    
    private static Trade testTrade1, testTrade2;
    
    private static List<TradeDTO> tradeDTOList;
    
    private static List<Trade> tradeList;

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
        
        testTrade1 = Trade.builder()
        		.account("Account")
        		.type("Type")
        		.buyQuantity(10.0)
        		.build();
        
        testTrade2 = Trade.builder()
        		.account("Account")
        		.type("Type")
        		.buyQuantity(10.0)
        		.build();
        
        tradeList = Arrays.asList(testTrade1, testTrade2);   
    
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }
    
  	// ********************************************************************

    @WithMockUser(username="admin",roles={"ADMIN"})
    @DisplayName(" Url request /trade/list - "
    		+ " - Given a Trade List,"
    		+ " when GET /trade/list action request,"
    		+ " then returns tradeslist page")
    @Test
    public void testGetTradeList() throws Exception {
        when(tradeService.getAllTrade()).thenReturn(tradeDTOList);
        mockMvc.perform(MockMvcRequestBuilders.get("/trade/list"))
                .andExpect(model().attributeExists("trades"))
                .andExpect(model().size(1))
                .andExpect(view().name("trade/list"))
                .andExpect(status().isOk());

        verify(tradeService, times(1)).getAllTrade();
        assertEquals(2, (tradeService.getAllTrade()).size());
        assertThat(tradeDTOList).usingRecursiveComparison().isEqualTo(tradeService.getAllTrade()).toString();
    }

    // ********************************************************************

    @WithMockUser(username="admin")
    @DisplayName(" Url request /trade/list - NULL LIST "
    		+ " - Given a Trade List,"
    		+ " when GET /trade/list action request,"
    		+ " then returns tradeslist page")    
    @Test
    public void testGetTradeListNull() throws Exception {
        when(tradeService.getAllTrade()).thenReturn(null);
        mockMvc.perform(MockMvcRequestBuilders.get("/trade/list"))
//                .andExpect(model().attributeExists("trades"))
                .andExpect(model().size(1))
                .andExpect(view().name("trade/list"))
                .andExpect(status().isOk());

        verify(tradeService, times(1)).getAllTrade();
        assertNull((tradeService.getAllTrade()));
    }

    // ********************************************************************
  
}
