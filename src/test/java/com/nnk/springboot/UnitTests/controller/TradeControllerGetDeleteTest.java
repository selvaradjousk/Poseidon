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
import com.nnk.springboot.config.JwtAuthenticationSuccessHandler;
import com.nnk.springboot.config.JwtUtils;
import com.nnk.springboot.config.MyUserDetailsService;
import com.nnk.springboot.controller.TradeController;
import com.nnk.springboot.dto.TradeDTO;
import com.nnk.springboot.service.TradeService;

@DisplayName("Controller < TRADE > DELETE - UNIT TESTS")
@ExtendWith(SpringExtension.class)
@WebMvcTest(TradeController.class)
class TradeControllerGetDeleteTest {

	@MockBean
	JwtAuthenticationSuccessHandler myAuthenticationSuccessHandler;

	@MockBean
	AuthTokenFilter authenticationJwtTokenFilter;

    @MockBean
    private TradeService tradeService;

    @MockBean
    private MyUserDetailsService userDetailsService;

    @MockBean
    private JwtUtils jwtUtils;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;


    private ObjectMapper objectMapper;
	
    private static TradeDTO testTradeDTO1, testTradeDTO2;
    


    @BeforeEach
    public void setUp() {
        objectMapper = new ObjectMapper();
        testTradeDTO1 = TradeDTO.builder()
        		.tradeId(1)
        		.account("Account")
        		.type("Type")
        		.buyQuantity(10.0)
        		.build();
    
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }
    
  	// ********************************************************************

    
    @DisplayName(" Url request trade/delete/{id} valid id- "
    		+ " - Given a trade/delete/{id} valid id, "
    		+ " when GET trade/delete action request,"
    		+ " then returns delete page")    
    @Test
    public void testGetTradeDelete() throws Exception {
    	when(tradeService.getTradeById(1)).thenReturn(testTradeDTO1);
    	mockMvc.perform(MockMvcRequestBuilders.get("/trade/delete/1"))
    				.andExpect(redirectedUrl("/trade/list"))
            		.andExpect(status().isFound())
            		.andExpect(model().hasNoErrors())
            		.andExpect(status().is(302));

        verify(tradeService, times(1)).deleteTrade(1);
       
    }

    // ********************************************************************

    
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

        verify(tradeService, times(0)).deleteTrade(1);
    }

    // ********************************************************************
   
   


}
