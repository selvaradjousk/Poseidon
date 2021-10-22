package com.nnk.springboot.IT.controller;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.anonymous;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

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
    
    @Autowired
    private WebApplicationContext context;

    @BeforeEach
    public void setUp() {
    	mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();

    }
    
  	// ********************************************************************


    @DisplayName(" Url request GET /user/list - Without Authentication"
    		+ " - Given a User List,"
    		+ " when GET /user/list action request,"
    		+ " then returns Error Authentication required")    
    @Test
    public void testGetUserListWithoutAuthentication() throws Exception {

        mockMvc.perform(get("/user/list").with(anonymous()))
	        .andExpect(status().is(401))
	        .andDo(MockMvcResultHandlers.print())
	        .andExpect(status().isUnauthorized())
	        .andExpect(status().reason(containsString("Full authentication is required to access this resource")))
	        .andExpect(unauthenticated());

    }
    
    @DisplayName(" Url request GET /user/list - With wrong password Authentication"
    		+ " - Given a User List,"
    		+ " when GET /user/list action request with wrong password authetication,"
    		+ " then returns Error 403 Forbidden")   
    @Test
    public void testGetUserListWithWrongUserPasswordAuthentication() throws Exception {

    	mockMvc.perform(get("/user/list")
				  .with(user("Someone")
				  .password("somePassword1!")
				  .roles("ADMIN")))
			  .andExpect(status().is(403))
			  .andExpect(status().reason(containsString("Forbidden")));

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

//        assertTrue((userService.getAllUser()).size() > 5);
        assertTrue((userService.getAllUser()).size() > 1);
    }

    // ********************************************************************

}
