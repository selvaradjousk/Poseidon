package com.nnk.springboot.UnitTests.controller;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
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
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nnk.springboot.config.AuthTokenFilter;
import com.nnk.springboot.config.JwtAuthenticationSuccessHandler;
import com.nnk.springboot.config.JwtUtils;
import com.nnk.springboot.config.MyUserDetailsService;
import com.nnk.springboot.controller.BidListController;
import com.nnk.springboot.dto.BidListDTO;
import com.nnk.springboot.service.BidListService;

@DisplayName("Controller < BIDLIST - GET UPDATE> - UNIT TESTS")
@ExtendWith(SpringExtension.class)
@WebMvcTest(BidListController.class)
class BidListControllerGetUpdateTest {

	@MockBean
	AuthTokenFilter authenticationJwtTokenFilter;

	@MockBean
	JwtAuthenticationSuccessHandler myAuthenticationSuccessHandler;

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


    
    @DisplayName(" Url request /bidList/update/{id} - "
    		+ " - Given a BidList,"
    		+ " when GET /bidList/update/{id} action request,"
    		+ " then returns bidList ADD page")    
    @Test
    public void testGetBidListUpdateById() throws Exception {
    	 when(bidListService.getBidListById(1)).thenReturn(testBidListDTO1);
        mockMvc.perform(MockMvcRequestBuilders.get("/bidList/update/1"))
                .andExpect(model().attributeExists("bidListDTO"))
                .andExpect(model().size(1))
                .andExpect(view().name("bidList/update"))
                .andExpect(status().isOk());

        verify(bidListService).getBidListById(1);
    }

    // ********************************************************************


}
