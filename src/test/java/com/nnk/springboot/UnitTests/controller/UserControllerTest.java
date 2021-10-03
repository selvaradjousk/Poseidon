package com.nnk.springboot.UnitTests.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
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

@DisplayName("Controller < USER > - UNIT TESTS")
@ExtendWith(SpringExtension.class)
@WebMvcTest(UserController.class)
class UserControllerTest {


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
 
    private static List<UserDTO> userDTOList;

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

        userDTOList = Arrays.asList(testUserDTO1, testUserDTO2); 
        
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }
    
  	// ********************************************************************

    
    @DisplayName(" Url request /listist - "
    		+ " - Given a User List,"
    		+ " when Get /list action request,"
    		+ " then returns userslist")    
    @Test
    public void testGetUserList() throws Exception {
        when(userService.getAllUser()).thenReturn(userDTOList);
        mockMvc.perform(MockMvcRequestBuilders.get("/user/list"))
                .andExpect(model().attributeExists("users"))
                .andExpect(model().size(1))
                .andExpect(view().name("user/list"))
                .andExpect(status().isOk());

        verify(userService, times(1)).getAllUser();
        assertEquals(2, (userService.getAllUser()).size());
        assertThat(userDTOList).usingRecursiveComparison().isEqualTo(userService.getAllUser()).toString();
    }

    // ********************************************************************

       
}
