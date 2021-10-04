package com.nnk.springboot.UnitTests.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.nnk.springboot.controller.BidListController;
import com.nnk.springboot.service.BidListService;

@DisplayName("Controller < BIDLIST > GET ADD - UNIT TESTS")
@ExtendWith(SpringExtension.class)
@WebMvcTest(BidListController.class)
class BidListControllerGetAddTest {


    @MockBean
    private BidListService bidListService;

    @MockBean
    private UserDetailsService userDetailsService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

	
 
    @BeforeEach
    public void setUp() {

        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }
 
    // ********************************************************************


    
    @DisplayName(" Url request /bidList/add - VALID "
    		+ " - Given a BidList,"
    		+ " when GET /bidList/add action request,"
    		+ " then returns bidList ADD page")    
    @Test
    public void testGetBidListAdd() throws Exception {
//    	 when(bidListService.getAllBidList()).thenReturn(bidListDTOList);
        mockMvc.perform(MockMvcRequestBuilders.get("/bidList/add"))
                .andExpect(model().attributeExists("bidListDTO"))
                .andExpect(model().size(1))
                .andExpect(view().name("bidList/add"))
                .andExpect(status().isOk());

    }

    // ********************************************************************

    
    
}
