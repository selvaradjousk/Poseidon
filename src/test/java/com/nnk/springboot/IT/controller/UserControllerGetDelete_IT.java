package com.nnk.springboot.IT.controller;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

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

import com.nnk.springboot.dto.UserDTO;
import com.nnk.springboot.service.UserService;

@DisplayName("INTEGRATION TESTS - Controller < USER > DELETE")
@AutoConfigureMockMvc
@SpringBootTest
@ActiveProfiles("test")
class UserControllerGetDelete_IT {



    @Autowired
    private UserService userService;
    	
    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {

    }
    
  	// ********************************************************************

    
    @DisplayName(" Url request GET user/delete/{id} valid id - Without Authentication"
    		+ " - Given a user/delete/{id} valid id, "
    		+ " when GET user/delete action request,"
    		+ " then returns Error Authentication required") 
    @Test
    public void testGetUserDeleteWithoutAuthentication() throws Exception {

    	mockMvc.perform(get("/user/delete/1"))
	        .andExpect(status().is(401))
	        .andDo(MockMvcResultHandlers.print())
	        .andExpect(status().isUnauthorized())
	        .andExpect(status().reason(containsString("Full authentication is required to access this resource")))
	        .andExpect(unauthenticated());
 
    }

    // ********************************************************************
    
    @WithMockUser(username = "admin", authorities = { "ADMIN", "USER"})
    @DisplayName(" Url request user/delete/{id} valid id - With Authentication "
    		+ " - Given a user/delete/{id} valid id, "
    		+ " when GET user/delete action request,"
    		+ " then returns delete page")    
    @Test
    public void testGetUserDeleteWithAuthentication() throws Exception {

    	List<UserDTO> users = userService.getAllUser();
    	assertNotNull(users);
    	int size = users.size();
    	
    	mockMvc.perform(get("/user/delete/1"))
    				.andExpect(redirectedUrl("/user/list"))
            		.andExpect(status().isFound())
            		.andExpect(model().hasNoErrors())
            		.andExpect(status().is(302));

    	List<UserDTO> userAfterDelete = userService.getAllUser();
        assertEquals(size - 1, userAfterDelete.size());
        
    }

    // ********************************************************************

    @WithMockUser(username = "admin", authorities = { "ADMIN", "USER"})
    @DisplayName(" Url request user/delete/{id} invalid id- "
    		+ " - Given a User user/delete/{id} invalid id,"
    		+ " when GET user/delete action request,"
    		+ " then returns delete page")    
    @Test
    public void testGetUserDeleteNull() throws Exception {
    	
        mockMvc.perform(get("/user/delete/"))
        		.andExpect(redirectedUrl(null))
                .andExpect(status().is(404))
        		.andExpect(status().isNotFound());

    }

    // ********************************************************************
   
   
}
