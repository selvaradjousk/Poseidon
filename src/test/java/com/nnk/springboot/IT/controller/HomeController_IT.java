package com.nnk.springboot.IT.controller;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
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

@DisplayName("INTEGRATION TESTS - Controller <HOME>")
@AutoConfigureMockMvc
@SpringBootTest
@ActiveProfiles("test")
class HomeController_IT {

    @Autowired
    private MockMvc mockMvc;

    
    @BeforeEach
    public void setUp() {
    }    
    
 
	// ********************************************************************
    
    @DisplayName("HOME Url request"
    		+ " - Given home url / request,"
    		+ " when GET / or /home request,"
    		+ " then return home page")	
    @Test
    public void testHomePage() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(view().name("home"))
                .andExpect(status().isOk());
    }    
   
    

	// ********************************************************************

    @DisplayName("HOME Admin Url request - Without Authentication"
    		+ " - Given adminHome url /admin/home request,"
    		+ " when GET /admin/home request,"
    		+ " then returns Error Authentication required")	
    @Test
    public void testAdminHomeRequestWithoutAuthentication() throws Exception {
    	
        mockMvc.perform(get("/admin/home/"))
	        .andExpect(status().is(401))
	        .andDo(MockMvcResultHandlers.print())
	        .andExpect(status().isUnauthorized())
	        .andExpect(status().reason(containsString("Full authentication is required to access this resource")))
	        .andExpect(unauthenticated());;
    }
    
    
	// ********************************************************************

    @WithMockUser(username="admin", roles={"ADMIN"})
    @DisplayName("HOME Admin Url request - With Authentication ADMIN"
    		+ " - Given adminHome url /admin/home request,"
    		+ " when GET /admin/home request,"
    		+ " then redirect to /bidList/list")	
    @Test
    public void testAdminHomeRequestWithAuthenticationAdmin() throws Exception {
    	
        mockMvc.perform(get("/admin/home/")
        		.with(user("Admin")
                        .password("Password1!")
                        .roles("ADMIN")))
                .andExpect(redirectedUrl("/bidList/list"));
    }
    
	// ********************************************************************

    @WithMockUser(username="user", roles={"USER"})
    @DisplayName("HOME Admin Url request - With Authentication USER"
    		+ " - Given adminHome url /admin/home request,"
    		+ " when GET /admin/home request,"
    		+ " then redirect to /bidList/list")	
    @Test
    public void testAdminHomeRequestWithAuthenticationUser() throws Exception {
    	
        mockMvc.perform(get("/admin/home/"))
                .andExpect(redirectedUrl("/bidList/list"));
    }
    
	// ********************************************************************

}
