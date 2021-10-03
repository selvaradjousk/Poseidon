package com.nnk.springboot.UnitTests.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
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
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nnk.springboot.controller.UserController;
import com.nnk.springboot.dto.UserDTO;
import com.nnk.springboot.service.UserService;

@DisplayName("Controller < USER POST UPDATE> - UNIT TESTS")
@ExtendWith(SpringExtension.class)
@WebMvcTest(UserController.class)
class UserControllerPostUpdateTest {


    @MockBean
    private UserService userService;

    @MockBean
    private UserDetailsService userDetailsService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    private ObjectMapper objectMapper;
	
    private static UserDTO testUserDTO1, testUserDTO2,testUserDTOupdate;
 
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
 
        
        testUserDTOupdate = UserDTO.builder()
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
    		+ " when POST /user/update/{id} action request,"
    		+ " then returns redirect /user/update/{id} page")    
    @Test
    public void testPostUserUpdate() throws Exception {
    	when(userService.getAllUser()).thenReturn(userDTOList);
        when(userService.updateUser(1, testUserDTO1)).thenReturn(testUserDTOupdate);
        
        mockMvc.perform(MockMvcRequestBuilders.post("/user/update/1")
        .sessionAttr("userDTO", testUserDTO1)
        .param("username", testUserDTO1.getUsername())
        .param("password", testUserDTO1.getPassword())
        .param("fullname", testUserDTO1.getFullname())
        .param("role", testUserDTO1.getRole()))
        .andExpect(model().hasNoErrors())
        .andExpect(model().size(0))
        .andExpect(model().attributeDoesNotExist("userDTO"))
        .andExpect(redirectedUrl("/user/list"))
        .andExpect(status().is(302));

        verify(userService, times(1)).getAllUser();
        verify(userService, times(1)).updateUser(anyInt(), any(UserDTO.class));
    }

    // ********************************************************************



    @DisplayName(" Url request /user/update/{id} - "
    		+ " - Given a User - empty username,"
    		+ " when POST /user/update/{id} action request,"
    		+ " then returns error & redirect /user/update page")    
    @Test
    public void testPostUserUpdateEmptyUserName() throws Exception {
    	when(userService.getAllUser()).thenReturn(userDTOList);
//    	when(userService.addUser(any(UserDTO.class))).thenReturn(any(UserDTO.class));
        
    	MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/user/update/1")
        .sessionAttr("userDTO", testUserDTO1)
        .param("username", "")
        .param("password", testUserDTO1.getPassword())
        .param("fullname", testUserDTO1.getFullname())
        .param("role", testUserDTO1.getRole()))
        .andExpect(model().hasErrors())
        .andExpect(model().size(1))
        .andExpect(model().attributeExists("userDTO"))
        .andExpect(view().name("user/update"))
        .andExpect(status().is(200))
        .andReturn();

        verify(userService, times(0)).getAllUser();
        verify(userService, times(0)).updateUser(anyInt(), any(UserDTO.class));

        String content = result.getResponse().getContentAsString();
        
        assertThat(content).contains("Username is mandatory");
    }

    // ********************************************************************


    
    
}
