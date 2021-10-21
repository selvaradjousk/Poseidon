package com.nnk.springboot.IT.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nnk.springboot.dto.UserDTO;
import com.nnk.springboot.repository.UserRepository;
import com.nnk.springboot.service.UserService;

@DisplayName("INTEGRATION TESTS - Controller <REGISTER>")
@AutoConfigureMockMvc
@SpringBootTest
@ActiveProfiles("test")
class RegistrationController_IT {


    @Autowired
    private MockMvc mockMvc;

	@Autowired
	private UserService userService;

	@Autowired
	private UserRepository userRepository;
	
	//
//
//	@MockBean
//	private AuthenticationManager authenticationManager;
//
	private ObjectMapper objectMapper;
	
    private UserDTO testUserDTO1, testUserDTO2;
	
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
        		.username("user5")
        		.password("Password&1")
        		.fullname("User5")
        		.role("USER")
        		.build();
    }    
    
 
	// ********************************************************************
    
    @DisplayName("REGISTER Url request"
    		+ " - Given register url /register request,"
    		+ " when GET /register  request,"
    		+ " then return /register")	
    @Test
    public void testHomePage() throws Exception {
        mockMvc.perform(get("/register"))
                .andExpect(view().name("register"))
                .andExpect(status().isOk());
    }    
   
    

	// ********************************************************************



    @DisplayName(" Url request POST /register "
    		+ " - Given a User,"
    		+ " when POST /register action request,"
    		+ " then returns user REGISTERED")    
    @Test
    public void testPostRegisterUser() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.post("/register")
                    .sessionAttr("userDTO", testUserDTO1)
                    .param("username", testUserDTO1.getUsername())
                    .param("password", testUserDTO1.getPassword())
                    .param("fullname", testUserDTO1.getFullname())
                    .param("role", testUserDTO1.getRole()))
                    .andExpect(model().hasNoErrors())
                    .andExpect(model().size(0))
                    .andExpect(model().attributeDoesNotExist("userDTO"))
                    .andExpect(redirectedUrl("/login"))
                    .andExpect(status().is(302));


    }

	// ********************************************************************



    @DisplayName(" Url request POST /register Existing user"
    		+ " - Given a Existing User,"
    		+ " when POST /register action request,"
    		+ " then returns user exists")    
    @Test
    public void testPostRegisterUserAlredyExists() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.post("/register")
                    .sessionAttr("userDTO", testUserDTO2)
                    .param("username", testUserDTO2.getUsername())
                    .param("password", testUserDTO2.getPassword())
                    .param("fullname", testUserDTO2.getFullname())
                    .param("role", testUserDTO2.getRole()))
                    .andExpect(model().hasNoErrors())
                    .andExpect(model().size(2))
                    .andExpect(model().attributeExists("userDTO"))
//                    .andExpect(redirectedUrl("/register"))
                    .andExpect(status().is(200));


    }

    // ********************************************************************
   
    @Test
    @DisplayName(" Url request POST /register EmptyUserNameField"
    		+ " - Given a User with EmptyUserNameField,"
    		+ " when POST /register action request,"
    		+ " then returns user Error Message")   
    void testRegisterWithEmptyUserNameField() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/register")
                .sessionAttr("userDTO", testUserDTO1)
                .param("username", "")
                .param("password", testUserDTO1.getPassword())
                .param("fullname", testUserDTO1.getFullname())
                .param("role", testUserDTO1.getRole()))
                .andExpect(model().hasErrors())
                .andExpect(view().name("register"))
                .andReturn();

        String content = result.getResponse().getContentAsString();

        assertThat(content).contains("Username is mandatory");
    }    

    // ********************************************************************
   
    
    @Test
    @DisplayName(" Url request POST /register EmptyPasswordField"
    		+ " - Given a User with EmptyPasswordField,"
    		+ " when POST /register action request,"
    		+ " then returns user Error Message")   
    void testRegisterWithEmptyPasswordField() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/register")
                .sessionAttr("userDTO", testUserDTO1)
                .param("username", "testUserDTO1.getUsername")
                .param("password", "")
                .param("fullname", testUserDTO1.getFullname())
                .param("role", testUserDTO1.getRole()))
                .andExpect(model().hasErrors())
                .andExpect(view().name("register"))
                .andReturn();

        String content = result.getResponse().getContentAsString();

        assertThat(content).contains("Password is mandatory");
    }    

    // ********************************************************************
      
    
    
    
    
    
    
//
//	@Test
//	public void testRegisterUser() throws Exception {
//		UserDTO signUpRequest = new UserDTO("username", "Password1!", "fullname", "USER");
//		Mockito.when(userService.(any(UserDTO.class))).thenReturn(user);
//		String json = mapper.writeValueAsString(signUpRequest);
//		mockMvc.perform(post("/register").contentType(MediaType.APPLICATION_JSON).characterEncoding("utf-8").content(json).accept(MediaType.APPLICATION_JSON))
//				.andExpect(status().isOk()).andExpect(jsonPath("$.success").value("true")).andExpect(jsonPath("$.message").value("User registered successfully"));
//
//		// Test when user provided email already exists in the database
//		Mockito.when(userService.registerNewUser(any(SignUpRequest.class))).thenThrow(new UserAlreadyExistAuthenticationException("exists"));
//		json = mapper.writeValueAsString(signUpRequest);
//		mockMvc.perform(post("/register").contentType(MediaType.APPLICATION_JSON).characterEncoding("utf-8").content(json).accept(MediaType.APPLICATION_JSON))
//				.andExpect(status().isBadRequest()).andExpect(jsonPath("$.success").value("false")).andExpect(jsonPath("$.message").value("Email Address already in use!"));
//	}

}
