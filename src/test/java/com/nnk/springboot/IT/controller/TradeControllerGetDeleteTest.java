package com.nnk.springboot.IT.controller;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

@DisplayName("INTEGRATION TESTS - Controller < TRADE > DELETE")
@AutoConfigureMockMvc
@SpringBootTest
@ActiveProfiles("test")
class TradeControllerGetDeleteTest {

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {

    }

  	// ********************************************************************

    @DisplayName(" Url request trade/delete/{id} valid id - Without Authentication"
    		+ " - Given a trade/delete/{id} valid id, "
    		+ " when GET trade/delete action request,"
    		+ " then returns Error Authentication required")     
    @Test
    public void testGetTradeDeleteWithoutAuthentication() throws Exception {

    	mockMvc.perform(MockMvcRequestBuilders.get("/trade/delete/1"))
	        .andExpect(status().is(401))
	        .andDo(MockMvcResultHandlers.print())
	        .andExpect(status().isUnauthorized())
	        .andExpect(status().reason(containsString("Full authentication is required to access this resource")))
	        .andExpect(unauthenticated());
 
    }

  	// ********************************************************************

    @WithMockUser(username = "admin", authorities = { "ADMIN", "USER"})
    @DisplayName(" Url request trade/delete/{id} valid id - With Authentication "
    		+ " - Given a trade/delete/{id} valid id, "
    		+ " when GET trade/delete action request,"
    		+ " then returns delete page")    
    @Test
    public void testGetTradeDeleteWithAuthentication () throws Exception {

    	mockMvc.perform(MockMvcRequestBuilders.get("/trade/delete/1"))
    				.andExpect(redirectedUrl("/trade/list"))
            		.andExpect(status().isFound())
            		.andExpect(model().hasNoErrors())
            		.andExpect(status().is(302));
 
    }

    // ********************************************************************

    @WithMockUser(username = "admin", authorities = { "ADMIN", "USER"})
    @DisplayName(" Url request trade/delete/{id} invalid id- "
    		+ " - Given a Trade trade/delete/{id} invalid id,"
    		+ " when GET trade/delete action request,"
    		+ " then returns delete page")    
    @Test
    public void testGetTradeDeleteNull() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/trade/delete/"))
        		.andExpect(redirectedUrl(null))
                .andExpect(status().is(404))
        		.andExpect(status().isNotFound());

    }

    // ********************************************************************
   
   


}
