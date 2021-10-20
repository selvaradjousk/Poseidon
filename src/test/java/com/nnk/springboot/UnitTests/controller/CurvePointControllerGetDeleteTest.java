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
import com.nnk.springboot.controller.CurveController;
import com.nnk.springboot.dto.CurvePointDTO;
import com.nnk.springboot.service.CurvePointService;

@DisplayName("Controller < CURVEPOINT > DELETE - UNIT TESTS")
@ExtendWith(SpringExtension.class)
@WebMvcTest(CurveController.class)
class CurvePointControllerGetDeleteTest {

	@MockBean
	AuthTokenFilter authenticationJwtTokenFilter;

	@MockBean
	JwtAuthenticationSuccessHandler myAuthenticationSuccessHandler;

    @MockBean
    private CurvePointService curvePointService;

    @MockBean
    private MyUserDetailsService userDetailsService;

    @MockBean
    private JwtUtils jwtUtils;
       
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;


    private ObjectMapper objectMapper;
	
    private static CurvePointDTO testCurvePointDTO1, testCurvePointDTO2;
    


    @BeforeEach
    public void setUp() {

        objectMapper = new ObjectMapper();
        testCurvePointDTO1 = CurvePointDTO.builder()
        		.id(1)
        		.curveId(1)
        		.term(10.0)
        		.value(10.0)
        		.build();
    
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }
    
  	// ********************************************************************

    
    @DisplayName(" Url request curvePoint/delete/{id} valid id- "
    		+ " - Given a curvePoint/delete/{id} valid id, "
    		+ " when GET curvePoint/delete action request,"
    		+ " then returns delete page")    
    @Test
    public void testGetCurvePointDelete() throws Exception {
    	when(curvePointService.getCurvePointById(1)).thenReturn(testCurvePointDTO1);
    	mockMvc.perform(MockMvcRequestBuilders.get("/curvePoint/delete/1"))
    				.andExpect(redirectedUrl("/curvePoint/list"))
            		.andExpect(status().isFound())
            		.andExpect(model().hasNoErrors())
            		.andExpect(status().is(302));

        verify(curvePointService, times(1)).deleteCurvePoint(1);
       
    }

    // ********************************************************************


    @DisplayName(" Url request curvePoint/delete/{id} invalid id- "
    		+ " - Given a CurvePoint curvePoint/delete/{id} invalid id,"
    		+ " when GET curvePoint/delete action request,"
    		+ " then returns delete page")    
    @Test
    public void testGetCurvePointDeleteNull() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/curvePoint/delete/"))
        		.andExpect(redirectedUrl(null))
                .andExpect(status().is(404))
        		.andExpect(status().isNotFound());

        verify(curvePointService, times(0)).deleteCurvePoint(1);
    }

    // ********************************************************************
   
     


}
