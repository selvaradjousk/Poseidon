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
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.nnk.springboot.config.AuthTokenFilter;
import com.nnk.springboot.config.JwtAuthenticationSuccessHandler;
import com.nnk.springboot.config.JwtUtils;
import com.nnk.springboot.config.MyUserDetailsService;
import com.nnk.springboot.controller.TradeController;
import com.nnk.springboot.service.TradeService;

@DisplayName("Controller < TRADE > GET ADD - UNIT TESTS")
@ExtendWith(SpringExtension.class)
@WebMvcTest(TradeController.class)
class TradeControllerGetAddTest {

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

	
 
    @BeforeEach
    public void setUp() {

        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }
 
    // ********************************************************************


    
    @DisplayName(" Url request /trade/add - "
    		+ " - Given a Trade,"
    		+ " when GET /trade/add action request,"
    		+ " then returns trade ADD page")    
    @Test
    public void testGetTradeAdd() throws Exception {
//    	 when(tradeService.getAllTrade()).thenReturn(tradeDTOList);
        mockMvc.perform(MockMvcRequestBuilders.get("/trade/add"))
                .andExpect(model().attributeExists("tradeDTO"))
                .andExpect(model().size(1))
                .andExpect(view().name("trade/add"))
                .andExpect(status().isOk());

    }

    // ********************************************************************

    
    
}
