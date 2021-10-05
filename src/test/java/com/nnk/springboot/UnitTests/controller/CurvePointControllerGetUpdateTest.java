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
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nnk.springboot.controller.CurveController;
import com.nnk.springboot.dto.CurvePointDTO;
import com.nnk.springboot.service.CurvePointService;

@DisplayName("Controller < CURVEPOINT - GET UPDATE> - UNIT TESTS")
@ExtendWith(SpringExtension.class)
@WebMvcTest(CurveController.class)
class CurvePointControllerGetUpdateTest {


    @MockBean
    private CurvePointService curvePointService;

    @MockBean
    private UserDetailsService userDetailsService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    private ObjectMapper objectMapper;
	
    private static CurvePointDTO testCurvePointDTO1;
 

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


    
    @DisplayName(" Url request /curvePoint/update/{id} - "
    		+ " - Given a CurvePoint,"
    		+ " when GET /curvePoint/update/{id} action request,"
    		+ " then returns curvePoint ADD page")    
    @Test
    public void testGetCurvePointUpdateById() throws Exception {
    	 when(curvePointService.getCurvePointById(1)).thenReturn(testCurvePointDTO1);
        mockMvc.perform(MockMvcRequestBuilders.get("/curvePoint/update/1"))
                .andExpect(model().attributeExists("curvePointDTO"))
                .andExpect(model().size(1))
                .andExpect(view().name("curvePoint/update"))
                .andExpect(status().isOk());

        verify(curvePointService).getCurvePointById(1);
    }

    // ********************************************************************

    
    
}
