package com.nnk.springboot.IT.controller;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import com.nnk.springboot.controller.BidListController;

@DisplayName("INTEGRATION TESTS - Controller < BIDLIST > DELETE")
@AutoConfigureMockMvc
@SpringBootTest
@ActiveProfiles("test")
class BidListControllerGetDelete_IT {


	@Autowired
    private MockMvc mockMvc;

	@Autowired
	BidListController bidListController;


    @BeforeEach
    public void setUp() {

    }
    
  	// ********************************************************************

    
    @DisplayName(" Url request bidList/delete/{id} valid id - - Without AUTH Login Status ERROR"
    		+ " - Given a bidList/delete/{id} valid id, "
    		+ " when GET bidList/delete action request,"
    		+ " then returns Error Authentication required")    
    @Test
    public void testGetBidListDeleteWithoutAuthentication() throws Exception {

    	mockMvc.perform(get("/bidList/delete/1"))
	        .andExpect(status().is(401))
	        .andDo(MockMvcResultHandlers.print())
	        .andExpect(status().isUnauthorized())
	        .andExpect(status().reason(containsString("Full authentication is required to access this resource")))
	        .andExpect(unauthenticated());

       
    }

    // ********************************************************************


    @WithMockUser(username = "admin", authorities = { "ADMIN", "USER"})
    @DisplayName(" Url request bidList/delete/{id} valid id - withAUTH Login Status FOUND"
    		+ " - Given a bidList/delete/{id} valid id, "
    		+ " when GET bidList/delete action request,"
    		+ " then returns on SUCCESS redirect to /bidList/list page")    
    @Test
    public void testGetBidListDeleteWithAuthentication() throws Exception {
	
    	mockMvc.perform(get("/bidList/delete/1"))
    				.andExpect(redirectedUrl("/bidList/list"))
            		.andExpect(status().isFound())
            		.andExpect(status().is(302));

       
    }

    // ********************************************************************

    
    @DisplayName(" Url request bidList/delete/{id} invalid id - without AUTH "
    		+ " - Given a BidList bidList/delete/{id} invalid id,"
    		+ " when GET bidList/delete action request,"
    		+ " then returns Error Authentication required")    
    @Test
    public void testGetBidListDeleteNullWithoutUserAuth() throws Exception {

        mockMvc.perform(get("/bidList/delete/"))
        		.andExpect(redirectedUrl(null))
        		.andExpect(status().is(401))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isUnauthorized())
                .andExpect(status().reason(containsString("Full authentication is required to access this resource")))
                .andExpect(unauthenticated());

    }

    // ********************************************************************

    @WithMockUser(username = "admin", authorities = { "ADMIN", "USER"})
    @DisplayName(" Url request bidList/delete/{id} invalid id - with AUTH"
    		+ " - Given a BidList bidList/delete/{id} invalid id,"
    		+ " when GET bidList/delete action request,"
    		+ " then returns delete page")    
    @Test
    public void testGetBidListDeleteNullWithUserAuth() throws Exception {

        mockMvc.perform(get("/bidList/delete/"))
        		.andExpect(redirectedUrl(null))
                .andExpect(status().is(404))
        		.andExpect(status().isNotFound());

    }

    // ********************************************************************

}
