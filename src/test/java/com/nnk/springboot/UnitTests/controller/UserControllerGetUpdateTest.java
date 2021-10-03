package com.nnk.springboot.UnitTests.controller;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.Arrays;
import java.util.List;

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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nnk.springboot.controller.UserController;
import com.nnk.springboot.dto.UserDTO;
import com.nnk.springboot.service.UserService;

@DisplayName("Controller < USER - GET UPDATE> - UNIT TESTS")
@ExtendWith(SpringExtension.class)
@WebMvcTest(UserController.class)
class UserControllerGetUpdateTest {


    @MockBean
    private UserService userService;

    @MockBean
    private UserDetailsService userDetailsService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    private ObjectMapper objectMapper;
	
    private static UserDTO testUserDTO1, testUserDTO2;
 

    @BeforeEach
    public void setUp() {

    	objectMapper = new ObjectMapper();
        testUserDTO1 = UserDTO.builder()
        		.username("Username")
        		.password("Password&1")
        		.fullname("Fullname")
        		.role("USER")
        		.build();
        
        testUserDTO2 = UserDTO.builder()
        		.username("Username")
        		.password("Password&1")
        		.fullname("Fullname")
        		.role("USER")
        		.build();

        
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }
    // ********************************************************************


    
    @DisplayName(" Url request /user/update/{id} - "
    		+ " - Given a User,"
    		+ " when GET /user/update/{id} action request,"
    		+ " then returns user ADD page")    
    @Test
    public void testGetUserUpdateById() throws Exception {
    	 when(userService.getUserById(1)).thenReturn(testUserDTO1);
        mockMvc.perform(MockMvcRequestBuilders.get("/user/update/1"))
                .andExpect(model().attributeExists("userDTO"))
                .andExpect(model().size(1))
                .andExpect(view().name("user/update"))
                .andExpect(status().isOk());

        verify(userService).getUserById(1);
    }

    // ********************************************************************

    
    
}
