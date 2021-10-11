package com.nnk.springboot.IT.controller;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;
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

import com.nnk.springboot.service.UserService;

@DisplayName("INTEGRATION TESTS - Controller < USER > GET LIST")
@AutoConfigureMockMvc
@SpringBootTest
@ActiveProfiles("test")
class UserControllerGetList_IT {


    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserService userService;

    @BeforeEach
    public void setUp() {


    }
    
  	// ********************************************************************


    @DisplayName(" Url request GET /user/list - Without Authentication"
    		+ " - Given a User List,"
    		+ " when GET /user/list action request,"
    		+ " then returns Error Authentication required")    
    @Test
    public void testGetUserListWithoutAuthentication() throws Exception {

        mockMvc.perform(get("/user/list"))
	        .andExpect(status().is(401))
	        .andDo(MockMvcResultHandlers.print())
	        .andExpect(status().isUnauthorized())
	        .andExpect(status().reason(containsString("Full authentication is required to access this resource")))
	        .andExpect(unauthenticated());

    }

    // ********************************************************************

    @WithMockUser(username = "admin", authorities = { "ADMIN", "USER"})
    @DisplayName(" Url request /user/list - With Authentication"
    		+ " - Given a User List,"
    		+ " when GET /user/list action request,"
    		+ " then returns userslist page")    
    @Test
    public void testGetUserListWithAuthentication() throws Exception {

        mockMvc.perform(get("/user/list"))
                .andExpect(model().attributeExists("users"))
                .andExpect(model().size(1))
                .andExpect(view().name("user/list"))
                .andExpect(status().isOk());

        assertEquals(6, (userService.getAllUser()).size());
    }

    // ********************************************************************

    
}
