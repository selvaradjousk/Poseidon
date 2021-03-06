package com.nnk.springboot.UnitTests.controller;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.nnk.springboot.config.AuthTokenFilter;
import com.nnk.springboot.config.MyUserDetailsService;
import com.nnk.springboot.controller.LoginController;
import com.nnk.springboot.repository.UserRepository;
import com.nnk.springboot.service.UserService;

@DisplayName("Controller <LOGIN> - UNIT TESTS")
@ExtendWith(SpringExtension.class)
@WebMvcTest(LoginController.class)
class LoginControllerTest {

	@MockBean
	AuthTokenFilter authenticationJwtTokenFilter;
	
    @MockBean
    private UserService userService;

    @MockBean
    private UserRepository userRepository;
       
    @MockBean
    private MyUserDetailsService userDetailsService;

    @MockBean
    private AuthenticationManager authenticationManager;

    @Autowired
    private MockMvc mockMvc;

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
    
    @DisplayName("Login Url request"
    		+ " - Given login url /login request,"
    		+ " when GET /login request,"
    		+ " then return login page")	
    @Test
    public void testHomePage() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
        		.get("/login"))
                .andExpect(view().name("login"))
                .andExpect(status().isOk());
    }    
   
    

	// ********************************************************************
 
    
    @DisplayName("Error Url request without authentication"
    		+ " - Given Error url /error request,"
    		+ " when GET /error request  without authentication,"
    		+ " then return error page")	
    @Test
    public void testError() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
        		.get("/error"))
                .andExpect(status().is(401))
                ;
    }    
   

	// ********************************************************************
 
    @WithMockUser(username="admin", password="Password1!", roles={"ADMIN"})
    @DisplayName("Error Url request WithAuthentication"
    		+ " - Given Error url /error request,"
    		+ " when GET /error request,"
    		+ " then return error page")	
    @Test
    public void testErrorWithAuthentication() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
        		.get("/error"))
                .andExpect(status().is(200));
    }    
       

	// ********************************************************************

}
