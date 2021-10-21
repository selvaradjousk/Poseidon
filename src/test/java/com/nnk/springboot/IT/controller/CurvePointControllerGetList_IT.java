package com.nnk.springboot.IT.controller;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import com.nnk.springboot.service.CurvePointService;

@DisplayName("INTEGRATION TESTS - Controller < CURVEPOINT > GET LIST")
@AutoConfigureMockMvc
@SpringBootTest
@ActiveProfiles("test")
class CurvePointControllerGetList_IT {

    @Autowired
    private MockMvc mockMvc;
    
    @Autowired
    private CurvePointService curvePointService;

    @BeforeEach
    public void setUp() {

    }
 	// ********************************************************************

    @DisplayName(" Url request /curvePoint/list - Without Authentication"
    		+ " - Given a CurvePoint List,"
    		+ " when GET /curvePoint/list action request,"
    		+ " then returns Error Authentication required")
    @Test
    public void testGetCurvePointListWithoutAuthentication() throws Exception {

        mockMvc.perform(get("/curvePoint/list"))
	        .andExpect(status().is(401))
	        .andDo(MockMvcResultHandlers.print())
	        .andExpect(status().isUnauthorized())
//	        .andExpect(status().reason(containsString("Full authentication is required to access this resource")))
	        .andExpect(unauthenticated());

    }
  
  	// ********************************************************************

    @WithMockUser(username="admin",roles={"ADMIN", "USER"})
    @DisplayName(" Url request /curvePoint/list - "
    		+ " - Given a CurvePoint List,"
    		+ " when GET /curvePoint/list action request,"
    		+ " then returns curvePointslist page")
    @Test
    public void testGetCurvePointList() throws Exception {

        mockMvc.perform(get("/curvePoint/list"))
                .andExpect(model().attributeExists("curvePoints"))
                .andExpect(model().size(1))
                .andExpect(view().name("curvePoint/list"))
                .andExpect(status().isOk());

//        assertEquals(5, (curvePointService.getAllCurvePoint()).size());
        assertTrue((curvePointService.getAllCurvePoint()).size() > 1);

    }

    // ********************************************************************

}
