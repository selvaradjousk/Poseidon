package com.nnk.springboot.IT.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
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
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nnk.springboot.controller.BidListController;
import com.nnk.springboot.dto.BidListDTO;

@DisplayName("INTEGRATION TESTS - Controller < BIDLIST > VALIDATE")
@AutoConfigureMockMvc
@SpringBootTest
@ActiveProfiles("test")
class BidListControllerPostValidate_IT {


	@Autowired
	BidListController bidListController;

    @Autowired
    private MockMvc mockMvc;


    private ObjectMapper objectMapper;
	
    private static BidListDTO testBidListDTO1;
 

    @BeforeEach
    public void setUp() {

        objectMapper = new ObjectMapper();
        testBidListDTO1 = BidListDTO.builder()
        		.bidListId(1)
        		.account("Account")
        		.type("Type")
        		.bidQuantity(10.0)
        		.build();


        
    }
    
  	// ********************************************************************


    @DisplayName(" Url request /bidList/validate - WithoutAuthentication"
    		+ " - Given a BidList,"
    		+ " when POST /bidList/validate action request,"
    		+ " then returns error message on requirement of authentication to proceed")    
    @Test
    public void testPostBidListValidateWithoutAuthentication() throws Exception {

        
        mockMvc.perform(MockMvcRequestBuilders.post("/bidList/validate"))
    	        .andExpect(status().is(401))
    	        .andDo(MockMvcResultHandlers.print())
    	        .andExpect(status().isUnauthorized())
    	        .andExpect(status().reason(containsString("Full authentication is required to access this resource")))
    	        .andExpect(unauthenticated());
    }
    
  	// ********************************************************************

    @WithMockUser(username = "admin", authorities = { "ADMIN", "USER"})
    @DisplayName(" Url request /bidList/validate - "
    		+ " - Given a BidList,"
    		+ " when POST /bidList/validate action request,"
    		+ " then returns redirect /bidList/validate page")    
    @Test
    public void testPostBidListValidate() throws Exception {
        
        mockMvc.perform(MockMvcRequestBuilders.post("/bidList/validate")
        .sessionAttr("bidListDTO", testBidListDTO1)
	        .param("account", testBidListDTO1.getAccount())
	        .param("type", testBidListDTO1.getType())
	        .param("bidQuantity", testBidListDTO1.getBidQuantity().toString()))
	        .andExpect(model().hasNoErrors())
	        .andExpect(model().size(0))
	        .andExpect(model().attributeDoesNotExist("bidListDTO"))
	        .andExpect(redirectedUrl("/bidList/list"))
	        .andExpect(status().is(302));

    }

    // ********************************************************************

}
