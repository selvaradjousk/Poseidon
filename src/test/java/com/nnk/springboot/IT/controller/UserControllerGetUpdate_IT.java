package com.nnk.springboot.IT.controller;

import static org.hamcrest.CoreMatchers.containsString;
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

@DisplayName("INTEGRATION TESTS - Controller < USER - GET UPDATE>")
@AutoConfigureMockMvc
@SpringBootTest
@ActiveProfiles("test")
class UserControllerGetUpdate_IT {


    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {

    }
    // ********************************************************************


    
    @DisplayName(" Url request GET /user/update/{id} - Without Authentication"
    		+ " - Given a User,"
    		+ " when GET /user/update/{id} action request,"
    		+ " then returns Error Authentication required")    
    @Test
    public void testGetUserUpdateByIdWithoutAuthentication() throws Exception {

        mockMvc.perform(get("/user/update/1"))
	        .andExpect(status().is(401))
	        .andDo(MockMvcResultHandlers.print())
	        .andExpect(status().isUnauthorized())
	        .andExpect(status().reason(containsString("Full authentication is required to access this resource")))
	        .andExpect(unauthenticated());

    }

    // ********************************************************************

    @WithMockUser(username = "admin", authorities = { "ADMIN", "USER"})
    @DisplayName(" Url request /user/update/{id} - "
    		+ " - Given a User,"
    		+ " when GET /user/update/{id} action request,"
    		+ " then returns user ADD page")    
    @Test
    public void testGetUserUpdateById() throws Exception {

        mockMvc.perform(get("/user/update/3"))
                .andExpect(model().attributeExists("userDTO"))
                .andExpect(model().size(1))
                .andExpect(view().name("user/update"))
                .andExpect(status().isOk());

    }

    // ********************************************************************

    
    
}
