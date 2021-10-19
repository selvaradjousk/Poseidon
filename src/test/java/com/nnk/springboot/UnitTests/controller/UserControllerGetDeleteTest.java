package com.nnk.springboot.UnitTests.controller;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.nnk.springboot.config.AuthTokenFilter;
import com.nnk.springboot.config.JwtUtils;
import com.nnk.springboot.config.MyUserDetailsService;
import com.nnk.springboot.controller.UserController;
import com.nnk.springboot.service.UserService;

@DisplayName("Controller < USER > DELETE - UNIT TESTS")
@ExtendWith(SpringExtension.class)
@WebMvcTest(UserController.class)
class UserControllerGetDeleteTest {


	@MockBean
	AuthTokenFilter authenticationJwtTokenFilter;
	
    @MockBean
    private UserService userService;

    @MockBean
    private MyUserDetailsService userDetailsService;

    @MockBean
    private JwtUtils jwtUtils;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;


    @BeforeEach
    public void setUp() {

    
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }
    
  	// ********************************************************************

    
    @DisplayName(" Url request user/delete/{id} valid id- "
    		+ " - Given a user/delete/{id} valid id, "
    		+ " when GET user/delete action request,"
    		+ " then returns delete page")    
    @Test
    public void testGetUserDelete() throws Exception {

    	mockMvc.perform(MockMvcRequestBuilders.get("/user/delete/1"))
    				.andExpect(redirectedUrl("/user/list"))
            		.andExpect(status().isFound())
            		.andExpect(model().hasNoErrors())
            		.andExpect(status().is(302));

        verify(userService, times(1)).deleteUser(1);

        
    }

    // ********************************************************************

    
    @DisplayName(" Url request user/delete/{id} invalid id- "
    		+ " - Given a User user/delete/{id} invalid id,"
    		+ " when GET user/delete action request,"
    		+ " then returns delete page")    
    @Test
    public void testGetUserDeleteNull() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/user/delete/"))
        		.andExpect(redirectedUrl(null))
                .andExpect(status().is(404))
        		.andExpect(status().isNotFound());

        verify(userService, times(0)).deleteUser(1);
    }

    // ********************************************************************
   
   
}
