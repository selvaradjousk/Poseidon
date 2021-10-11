package com.nnk.springboot.IT.controller;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;
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
import com.nnk.springboot.service.BidListService;

@DisplayName("INTEGRATION TESTS - Controller < BIDLIST > GET LIST")
@AutoConfigureMockMvc
@SpringBootTest
@ActiveProfiles("test")
class BidListControllerGetList_IT {

	@Autowired
    private MockMvc mockMvc;
	
	@Autowired
	private BidListService bidListService;

	@InjectMocks
	BidListController bidListController;

    @BeforeEach
    public void setUp() {

    }
    
  	// ********************************************************************

    @DisplayName(" Url request /bidList/list - Without Authentication"
    		+ " - Given a BidList List,"
    		+ " when GET /bidList/list action request,"
    		+ " then returns Error Authentication required")
    @Test
    public void testGetBidListListXithoutAuthentication() throws Exception {

        mockMvc.perform(get("/bidList/list"))
	        .andExpect(status().is(401))
	        .andDo(MockMvcResultHandlers.print())
	        .andExpect(status().isUnauthorized())
	        .andExpect(status().reason(containsString("Full authentication is required to access this resource")))
	        .andExpect(unauthenticated());

    }

    // ********************************************************************

    

    @WithMockUser(username="admin",roles={"ADMIN", "USER"})
    @DisplayName(" Url request /bidList/list - "
    		+ " - Given a BidList List,"
    		+ " when GET /bidList/list action request,"
    		+ " then returns bidListslist page")
    @Test
    public void testGetBidListList() throws Exception {

        mockMvc.perform(get("/bidList/list"))
                .andExpect(model().attributeExists("bids"))
                .andExpect(model().size(1))
                .andExpect(view().name("bidList/list"))
                .andExpect(status().isOk());


        assertEquals(5, (bidListService.getAllBidList()).size());

    }

    // ********************************************************************

 

}
