package com.nnk.springboot.UnitTests.controller;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nnk.springboot.config.AuthTokenFilter;
import com.nnk.springboot.config.JwtUtils;
import com.nnk.springboot.config.MyUserDetailsService;
import com.nnk.springboot.controller.BidListController;
import com.nnk.springboot.dto.BidListDTO;
import com.nnk.springboot.service.BidListService;

@DisplayName("Controller < BIDLIST > DELETE - UNIT TESTS")
@ExtendWith(SpringExtension.class)
@WebMvcTest(BidListController.class)
class BidListControllerGetDeleteTest {


	@MockBean
	AuthTokenFilter authenticationJwtTokenFilter;
	
    @MockBean
    private BidListService bidListService;

    @MockBean
    private MyUserDetailsService userDetailsService;

    @MockBean
    private JwtUtils jwtUtils;
       
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;


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
    
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }
    
  	// ********************************************************************

    
    @DisplayName(" Url request bidList/delete/{id} valid id- "
    		+ " - Given a bidList/delete/{id} valid id, "
    		+ " when GET bidList/delete action request,"
    		+ " then returns delete page")    
    @Test
    public void testGetBidListDelete() throws Exception {
    	when(bidListService.getBidListById(1)).thenReturn(testBidListDTO1);
    	mockMvc.perform(MockMvcRequestBuilders.get("/bidList/delete/1"))
    				.andExpect(redirectedUrl("/bidList/list"))
            		.andExpect(status().isFound())
            		.andExpect(model().hasNoErrors())
            		.andExpect(status().is(302));

        verify(bidListService, times(1)).deleteBidList(1);
       
    }

    // ********************************************************************


    
    @DisplayName(" Url request bidList/delete/{id} invalid id- "
    		+ " - Given a BidList bidList/delete/{id} invalid id,"
    		+ " when GET bidList/delete action request,"
    		+ " then returns delete page")    
    @Test
    public void testGetBidListDeleteNull() throws Exception {
    	when(bidListService.getBidListById(1)).thenReturn(testBidListDTO1);
        mockMvc.perform(MockMvcRequestBuilders.get("/bidList/delete/"))
        		.andExpect(redirectedUrl(null))
                .andExpect(status().is(404))
        		.andExpect(status().isNotFound());

        verify(bidListService, times(0)).deleteBidList(1);
    }

    // ********************************************************************

}
