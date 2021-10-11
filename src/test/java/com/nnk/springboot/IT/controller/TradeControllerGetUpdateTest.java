package com.nnk.springboot.IT.controller;

import static org.hamcrest.CoreMatchers.containsString;
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

@DisplayName("INTEGRATION TESTS - Controller < TRADE - GET UPDATE>")
@AutoConfigureMockMvc
@SpringBootTest
@ActiveProfiles("test")
class TradeControllerGetUpdateTest {


    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

 

    @BeforeEach
    public void setUp() {

        

    }
    // ********************************************************************


    @DisplayName(" Url request GET /trade/update/{id} - Without Authentication"
    		+ " - Given a Trade,"
    		+ " when GET /trade/update/{id} action request,"
    		+ " then returns Error Authentication required") 
    @Test
    public void testGetTradeUpdateByIdWithoutAuthentication() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/trade/update/2"))
	        .andExpect(status().is(401))
	        .andDo(MockMvcResultHandlers.print())
	        .andExpect(status().isUnauthorized())
	        .andExpect(status().reason(containsString("Full authentication is required to access this resource")))
	        .andExpect(unauthenticated());


    }

    // ********************************************************************


    @WithMockUser(username = "admin", authorities = { "ADMIN", "USER"})
    @DisplayName(" Url request GET /trade/update/{id} - With Authentication"
    		+ " - Given a Trade,"
    		+ " when GET /trade/update/{id} action request,"
    		+ " then returns trade ADD page")    
    @Test
    public void testGetTradeUpdateByIdWithAuthentication() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/trade/update/2"))
                .andExpect(model().attributeExists("tradeDTO"))
                .andExpect(model().size(1))
                .andExpect(view().name("trade/update"))
                .andExpect(status().isOk());


    }

    // ********************************************************************

       
    
}
