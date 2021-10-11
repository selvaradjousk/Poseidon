package com.nnk.springboot.IT.controller;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

@DisplayName("INTEGRATION TESTS - Controller < CURVEPOINT > GET ADD")
@AutoConfigureMockMvc
@SpringBootTest
@ActiveProfiles("test")
class CurvePointControllerGetAdd_IT {

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {

    }
 
    // ********************************************************************


    
    @DisplayName(" Url request /curvePoint/add - Without Authentication"
    		+ " - Given a CurvePoint,"
    		+ " when GET /curvePoint/add action request,"
    		+ " then returns curvePoint ADD page")    
    @Test
    public void testGetCurvePointAddWithoutAuthentication() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/curvePoint/add"))
	        .andExpect(status().is(401))
	        .andDo(MockMvcResultHandlers.print())
	        .andExpect(status().isUnauthorized())
	        .andExpect(status().reason(containsString("Full authentication is required to access this resource")))
	        .andExpect(unauthenticated());

    }

    // ********************************************************************


    @WithMockUser(username = "admin", authorities = { "ADMIN", "USER"})
    @DisplayName(" Url request /curvePoint/add - With Authentication"
    		+ " - Given a CurvePoint,"
    		+ " when GET /curvePoint/add action request,"
    		+ " then returns curvePoint ADD page")    
    @Test
    public void testGetCurvePointAddWithAuthentication() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/curvePoint/add"))
                .andExpect(model().attributeExists("curvePointDTO"))
                .andExpect(model().size(1))
                .andExpect(view().name("curvePoint/add"))
                .andExpect(status().isOk());

    }

    // ********************************************************************
    
    
}
