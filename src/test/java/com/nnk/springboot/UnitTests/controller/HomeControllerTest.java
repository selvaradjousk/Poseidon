package com.nnk.springboot.UnitTests.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.nnk.springboot.controller.HomeController;

@DisplayName("Controller <HOME> - UNIT TESTS")
@ExtendWith(SpringExtension.class)
@WebMvcTest(HomeController.class)
class HomeControllerTest {

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
    
    @DisplayName("HOME Url request"
    		+ " - Given home url / request,"
    		+ " when GET / or /home request,"
    		+ " then return home page")	
    @Test
    public void testHomePage() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
        		.get("/"))
                .andExpect(view().name("home"))
                .andExpect(status().isOk());
    }    
   
    

	// ********************************************************************

    @WithMockUser(username="admin")
    @DisplayName("HOME Admin Url request"
    		+ " - Given adminHome url /admin/home request,"
    		+ " when GET /admin/home request,"
    		+ " then redirect to /bidList/list")	
    @Test
    public void testAdminHomeRequest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
        		.get("/admin/home"))
                .andExpect(redirectedUrl("/bidList/list"));
    }

}
