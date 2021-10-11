package com.nnk.springboot.IT.controller;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nnk.springboot.service.TradeService;

@DisplayName("INTEGRATION TESTS - Controller < TRADE > GET LIST")
@AutoConfigureMockMvc
@SpringBootTest
@ActiveProfiles("test")
class TradeControllerGetListTest {

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @Autowired
    private TradeService tradeService;
    
    @BeforeEach
    public void setUp() {

    }
    
  	// ********************************************************************

    @DisplayName(" Url request /trade/list - "
    		+ " - Given a Trade List, Without Authentication"
    		+ " when GET /trade/list action request,"
    		+ " then returns Error Authentication required") 
    @Test
    public void testGetTradeListWithoutAuthentication() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/trade/list"))
	        .andExpect(status().is(401))
	        .andDo(MockMvcResultHandlers.print())
	        .andExpect(status().isUnauthorized())
	        .andExpect(status().reason(containsString("Full authentication is required to access this resource")))
	        .andExpect(unauthenticated());

    }

    
    // ********************************************************************

    @WithMockUser(username = "admin", authorities = { "ADMIN", "USER"})
    @DisplayName(" Url request /trade/list - With Authentication "
    		+ " - Given a Trade List,"
    		+ " when GET /trade/list action request,"
    		+ " then returns tradeslist page")
    @Test
    public void testGetTradeListWithAuthentication() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/trade/list"))
                .andExpect(model().attributeExists("trades"))
                .andExpect(model().size(1))
                .andExpect(view().name("trade/list"))
                .andExpect(status().isOk());

        assertEquals(5, (tradeService.getAllTrade()).size());
    }

    // ********************************************************************

    @WithMockUser(username = "admin", authorities = { "ADMIN", "USER"})
    @DisplayName(" Url request /trade/list - NULL LIST "
    		+ " - Given a Trade List,"
    		+ " when GET /trade/list action request,"
    		+ " then returns tradeslist page")    
    @Test
    public void testGetTradeListNull() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/trade/list"))
                .andExpect(model().size(1))
                .andExpect(view().name("trade/list"))
                .andExpect(status().isOk());

    }

    // ********************************************************************
  
}
