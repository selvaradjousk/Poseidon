package com.nnk.springboot.IT.controller;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import com.nnk.springboot.controller.BidListController;

@DisplayName("Controller < BIDLIST > GET ADD - INTEGRATION TESTS")
@AutoConfigureMockMvc
@SpringBootTest
@ActiveProfiles("test")
class BidListControllerGetAddTest {


	@InjectMocks
	BidListController bidListController;

	@Autowired
	private MockMvc mockMvc;	
 
    @BeforeEach
    public void setUp() {

    }

    // ********************************************************************

    @DisplayName(" Url request /bidList/add - Without AUTH Login Status ERROR"
    		+ " - Given a BidList,"
    		+ " when GET /bidList/add action request,"
    		+ " then returns Error Authentication required")    
    @Test
    public void testGetBidListAddWithoutUserLogin() throws Exception {

        mockMvc.perform(get("/bidList/add"))
                .andExpect(status().is(401))
                .andDo(MockMvcResultHandlers.print())
		        .andExpect(status().isUnauthorized())
		        .andExpect(status().reason(containsString("Full authentication is required to access this resource")))
		        .andExpect(unauthenticated());
    }

    // ********************************************************************

    
}
