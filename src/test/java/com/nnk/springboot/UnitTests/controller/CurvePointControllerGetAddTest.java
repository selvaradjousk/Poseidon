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

import com.nnk.springboot.controller.CurveController;
import com.nnk.springboot.service.CurvePointService;

@DisplayName("Controller < CURVEPOINT > GET ADD - UNIT TESTS")
@ExtendWith(SpringExtension.class)
@WebMvcTest(CurveController.class)
class CurvePointControllerGetAddTest {


    @MockBean
    private CurvePointService curvePointService;

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


    
    @DisplayName(" Url request /curvePoint/add - "
    		+ " - Given a CurvePoint,"
    		+ " when GET /curvePoint/add action request,"
    		+ " then returns curvePoint ADD page")    
    @Test
    public void testGetCurvePointAdd() throws Exception {
//    	 when(curvePointService.getAllCurvePoint()).thenReturn(curvePointDTOList);
        mockMvc.perform(MockMvcRequestBuilders.get("/curvePoint/add"))
                .andExpect(model().attributeExists("curvePointDTO"))
                .andExpect(model().size(1))
                .andExpect(view().name("curvePoint/add"))
                .andExpect(status().isOk());

    }

    // ********************************************************************

    
    
}
