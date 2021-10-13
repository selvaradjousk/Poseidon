package com.nnk.springboot.IT.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nnk.springboot.dto.UserDTO;

@DisplayName("INTEGRATION TESTS - Controller < USER POST UPDATE>")
@AutoConfigureMockMvc
@SpringBootTest
@ActiveProfiles("test")
class UserControllerPostUpdate_IT {

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper;
	
    private static UserDTO testUserDTO1;


    @BeforeEach
    public void setUp() {

    	objectMapper = new ObjectMapper();
        testUserDTO1 = UserDTO.builder()
        		.username("username2")
        		.password("Password1!")
        		.fullname("Fullname")
        		.role("USER")
        		.build();
        
    }
    

    // ********************************************************************


    @DisplayName(" Url request POST /user/update/{id} - Without Authentication"
    		+ " - Given a User,"
    		+ " when POST /user/update/{id} action request,"
    		+ " then returns redirect /user/update/{id} page")    
    @Test
    public void testPostUserUpdateWithoutAuthentication() throws Exception {
        
        mockMvc.perform(post("/user/update/2")
	        .sessionAttr("userDTO", testUserDTO1))
	        .andExpect(status().is(401))
	        .andDo(MockMvcResultHandlers.print())
	        .andExpect(status().isUnauthorized())
	        .andExpect(status().reason(containsString("Full authentication is required to access this resource")))
	        .andExpect(unauthenticated());

    }

    // ********************************************************************


    @WithMockUser(username = "admin", authorities = { "ADMIN", "USER"})
    @DisplayName(" Url request /user/update/{id} - "
    		+ " - Given a User,"
    		+ " when POST /user/update/{id} action request,"
    		+ " then returns redirect /user/update/{id} page")    
    @Test
    public void testPostUserUpdate() throws Exception {
        
        mockMvc.perform(post("/user/update/2")
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

    }

    // ********************************************************************



    @WithMockUser(username = "admin", authorities = { "ADMIN", "USER"})
    @DisplayName(" Url request /user/update/{id} - "
    		+ " - Given a User - empty username,"
    		+ " when POST /user/update/{id} action request,"
    		+ " then returns error & redirect /user/update page")    
    @Test
    public void testPostUserUpdateEmptyUserName() throws Exception {

    	MvcResult result = mockMvc.perform(post("/user/update/2")
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

        String content = result.getResponse().getContentAsString();
        
        assertThat(content).contains("Username is mandatory");
    }

    // ********************************************************************



    @WithMockUser(username = "admin", authorities = { "ADMIN", "USER"})
    @DisplayName("Url request /user/update/{id} - UserName  Non Alphanumeric characters - "
    		+ " - Given a User - username with Non Alphanumeric characters,"
    		+ " when POST /user/update/{id} action request,"
    		+ " then returns error & redirect /user/update page")    
    @Test
    public void testPostUserUpdateWithUserNameHavingSymbols() throws Exception {

    	MvcResult result = mockMvc.perform(post("/user/update/2")
        .sessionAttr("userDTO", testUserDTO1)
        .param("username", "Username!&&&")
        .param("password", testUserDTO1.getPassword())
        .param("fullname", testUserDTO1.getFullname())
        .param("role", testUserDTO1.getRole()))
        .andExpect(model().hasErrors())
        .andExpect(model().size(1))
        .andExpect(model().attributeExists("userDTO"))
        .andExpect(view().name("user/update"))
        .andExpect(status().is(200))
        .andReturn();


        String content = result.getResponse().getContentAsString();
        
        assertThat(content).contains("Should be alphanumeric or email and minimum more than 2 characters");
    }

    // ********************************************************************


    @WithMockUser(username = "admin", authorities = { "ADMIN", "USER"})
    @DisplayName("Url request /user/update/{id} - Empty Password - "
    		+ " - Given a User - Empty Password,"
    		+ " when POST /user/update/{id} action request,"
    		+ " then returns error & redirect /user/update page")    
    @Test
    public void testPostUserUpdateWithPasswordEmpty() throws Exception {

    	MvcResult result = mockMvc.perform(post("/user/update/2")
        .sessionAttr("userDTO", testUserDTO1)
        .param("username",  testUserDTO1.getUsername())
        .param("password", "")
        .param("fullname", testUserDTO1.getFullname())
        .param("role", testUserDTO1.getRole()))
        .andExpect(model().hasErrors())
        .andExpect(model().size(1))
        .andExpect(model().attributeExists("userDTO"))
        .andExpect(view().name("user/update"))
        .andExpect(status().is(200))
        .andReturn();


        String content = result.getResponse().getContentAsString();
        
        assertThat(content).contains("Password is mandatory");

    }

    // ********************************************************************
    


    @WithMockUser(username = "admin", authorities = { "ADMIN", "USER"})
    @DisplayName("Url request /user/update/{id} - Password No UpperCase Letters- "
    		+ " - Given a User - Password No UpperCase Letters-,"
    		+ " when POST /user/update/{id} action request,"
    		+ " then returns error & redirect /user/update page")    
    @Test
    public void testPostUserUpdateWithPasswordyWithoutUpperCase() throws Exception {

    	MvcResult result = mockMvc.perform(post("/user/update/2")
        .sessionAttr("userDTO", testUserDTO1)
        .param("username",  testUserDTO1.getUsername())
        .param("password", "password&1")
        .param("fullname", testUserDTO1.getFullname())
        .param("role", testUserDTO1.getRole()))
        .andExpect(model().hasErrors())
        .andExpect(model().size(1))
        .andExpect(model().attributeExists("userDTO"))
        .andExpect(view().name("user/update"))
        .andExpect(status().is(200))
        .andReturn();

        String content = result.getResponse().getContentAsString();
        
        assertThat(content).contains("The password must contain at least 8 characters that includes any one uppercase letter, any one number and any one symbol ( &amp; ~ # @ = * - + € ^ $ £ µ % )");
        
    }

    // ********************************************************************
    

    @WithMockUser(username = "admin", authorities = { "ADMIN", "USER"})
    @DisplayName("Url request /user/update/{id} - Password WithoutLowerCase- "
    		+ " - Given a User - Password No WithoutLowerCase-,"
    		+ " when POST /user/update/{id} action request,"
    		+ " then returns error & redirect /user/update page")    
    @Test
    public void testPostUserUpdateWithPasswordWithoutLowerCase() throws Exception {

    	MvcResult result = mockMvc.perform(post("/user/update/2")
        .sessionAttr("userDTO", testUserDTO1)
        .param("username",  testUserDTO1.getUsername())
        .param("password", "PASSWORD&1")
        .param("fullname", testUserDTO1.getFullname())
        .param("role", testUserDTO1.getRole()))
        .andExpect(model().hasErrors())
        .andExpect(model().size(1))
        .andExpect(model().attributeExists("userDTO"))
        .andExpect(view().name("user/update"))
        .andExpect(status().is(200))
        .andReturn();


        String content = result.getResponse().getContentAsString();
        
        assertThat(content).contains("The password must contain at least 8 characters that includes any one uppercase letter, any one number and any one symbol ( &amp; ~ # @ = * - + € ^ $ £ µ % )");
              
    }

    // ********************************************************************


    @WithMockUser(username = "admin", authorities = { "ADMIN", "USER"})
    @DisplayName("Url request /user/update/{id} - Password WithoutAlphabets- "
    		+ " - Given a User - Password No WithoutAlphabets-,"
    		+ " when POST /user/update/{id} action request,"
    		+ " then returns error & redirect /user/update page")    
    @Test
    public void testPostUserUpdateWithPasswordWithoutAlphabets() throws Exception {

    	MvcResult result = mockMvc.perform(post("/user/update/2")
        .sessionAttr("userDTO", testUserDTO1)
        .param("username",  testUserDTO1.getUsername())
        .param("password", "!!!!!!!!!!!!&1")
        .param("fullname", testUserDTO1.getFullname())
        .param("role", testUserDTO1.getRole()))
        .andExpect(model().hasErrors())
        .andExpect(model().size(1))
        .andExpect(model().attributeExists("userDTO"))
        .andExpect(view().name("user/update"))
        .andExpect(status().is(200))
        .andReturn();

        String content = result.getResponse().getContentAsString();
        
        assertThat(content).contains("The password must contain at least 8 characters that includes any one uppercase letter, any one number and any one symbol ( &amp; ~ # @ = * - + € ^ $ £ µ % )");
       
    }

    // ********************************************************************


    @WithMockUser(username = "admin", authorities = { "ADMIN", "USER"})
    @DisplayName("Url request /user/update/{id} - Password No NumericValues- "
    		+ " - Given a User - Password No NumericValues-,"
    		+ " when POST /user/update/{id} action request,"
    		+ " then returns error & redirect /user/update page")    
    @Test
    public void testPostUserUpdateWithPasswordWithOutNumberValues() throws Exception {

    	MvcResult result = mockMvc.perform(post("/user/update/2")
        .sessionAttr("userDTO", testUserDTO1)
        .param("username",  testUserDTO1.getUsername())
        .param("password", "Password&!!!")
        .param("fullname", testUserDTO1.getFullname())
        .param("role", testUserDTO1.getRole()))
        .andExpect(model().hasErrors())
        .andExpect(model().size(1))
        .andExpect(model().attributeExists("userDTO"))
        .andExpect(view().name("user/update"))
        .andExpect(status().is(200))
        .andReturn();

        String content = result.getResponse().getContentAsString();
        
        assertThat(content).contains("The password must contain at least 8 characters that includes any one uppercase letter, any one number and any one symbol ( &amp; ~ # @ = * - + € ^ $ £ µ % )");
       
    }

    // ********************************************************************


    @WithMockUser(username = "admin", authorities = { "ADMIN", "USER"})
    @DisplayName("Url request /user/update/{id} - Password < 8 Characters- "
    		+ " - Given a User - Password  < 8 Characters-,"
    		+ " when POST /user/update/{id} action request,"
    		+ " then returns error & redirect /user/update page")    
    @Test
    public void testPostUserUpdateWithPasswordLessThan8Characters() throws Exception {

    	MvcResult result = mockMvc.perform(post("/user/update/2")
        .sessionAttr("userDTO", testUserDTO1)
        .param("username",  testUserDTO1.getUsername())
        .param("password", "P&!1")
        .param("fullname", testUserDTO1.getFullname())
        .param("role", testUserDTO1.getRole()))
        .andExpect(model().hasErrors())
        .andExpect(model().size(1))
        .andExpect(model().attributeExists("userDTO"))
        .andExpect(view().name("user/update"))
        .andExpect(status().is(200))
        .andReturn();

        String content = result.getResponse().getContentAsString();
        
        assertThat(content).contains("The password must contain at least 8 characters that includes any one uppercase letter, any one number and any one symbol ( &amp; ~ # @ = * - + € ^ $ £ µ % )");
       
    }

    // ********************************************************************

    @WithMockUser(username = "admin", authorities = { "ADMIN", "USER"})
    @DisplayName("Url request /user/update/{id} - Password No Symbols- "
    		+ " - Given a User - Password No Symbols-,"
    		+ " when POST /user/update/{id} action request,"
    		+ " then returns error & redirect /user/update page")    
    @Test
    public void testPostUserUpdateWithPasswordWithoutSymbols() throws Exception {

    	MvcResult result = mockMvc.perform(post("/user/update/2")
        .sessionAttr("userDTO", testUserDTO1)
        .param("username",  testUserDTO1.getUsername())
        .param("password", "passwordddd1")
        .param("fullname", testUserDTO1.getFullname())
        .param("role", testUserDTO1.getRole()))
        .andExpect(model().hasErrors())
        .andExpect(model().size(1))
        .andExpect(model().attributeExists("userDTO"))
        .andExpect(view().name("user/update"))
        .andExpect(status().is(200))
        .andReturn();

        String content = result.getResponse().getContentAsString();
        
        assertThat(content).contains("The password must contain at least 8 characters that includes any one uppercase letter, any one number and any one symbol ( &amp; ~ # @ = * - + € ^ $ £ µ % )");
       
    }

    // ********************************************************************


    @WithMockUser(username = "admin", authorities = { "ADMIN", "USER"})
    @DisplayName("Url request /user/update/{id} - FullNameEmpty- "
    		+ " - Given a User - FullNameEmpty-,"
    		+ " when POST /user/update/{id} action request,"
    		+ " then returns error & redirect /user/update page")    
    @Test
    public void testPostUserUpdateWithFullNameEmpty() throws Exception {

    	MvcResult result = mockMvc.perform(post("/user/update/2")
        .sessionAttr("userDTO", testUserDTO1)
        .param("username",  testUserDTO1.getUsername())
        .param("password", testUserDTO1.getPassword())
        .param("fullname", "")
        .param("role", testUserDTO1.getRole()))
        .andExpect(model().hasErrors())
        .andExpect(model().size(1))
        .andExpect(model().attributeExists("userDTO"))
        .andExpect(view().name("user/update"))
        .andExpect(status().is(200))
        .andReturn();

        String content = result.getResponse().getContentAsString();
        
        assertThat(content).contains("Should be alphanumeric and minimum more than 2 characters");
       
    }

    // ********************************************************************

    @WithMockUser(username = "admin", authorities = { "ADMIN", "USER"})
    @DisplayName("Url request /user/update/{id} - FullNameWithSymbol- "
    		+ " - Given a User - FullNameWithSymbol-,"
    		+ " when POST /user/update/{id} action request,"
    		+ " then returns error & redirect /user/update page")    
    @Test
    public void testPostUserUpdateWithFullNameWithSymbol() throws Exception {

    	MvcResult result = mockMvc.perform(post("/user/update/2")
        .sessionAttr("userDTO", testUserDTO1)
        .param("username",  testUserDTO1.getUsername())
        .param("password", testUserDTO1.getPassword())
        .param("fullname", "Fullname&&&&")
        .param("role", testUserDTO1.getRole()))
        .andExpect(model().hasErrors())
        .andExpect(model().size(1))
        .andExpect(model().attributeExists("userDTO"))
        .andExpect(view().name("user/update"))
        .andExpect(status().is(200))
        .andReturn();

        String content = result.getResponse().getContentAsString();
        
        assertThat(content).contains("Should be alphanumeric and minimum more than 2 characters");
       
    }

    // ********************************************************************

    @WithMockUser(username = "admin", authorities = { "ADMIN", "USER"})
    @DisplayName("Url request /user/update/{id} - RoleEmpty- "
    		+ " - Given a User - RoleEmpty -,"
    		+ " when POST /user/update/{id} action request,"
    		+ " then returns error & redirect /user/update page")    
    @Test
    public void testPostUserUpdateWithRoleEmpty() throws Exception {

    	MvcResult result = mockMvc.perform(post("/user/update/2")
        .sessionAttr("userDTO", testUserDTO1)
        .param("username",  testUserDTO1.getUsername())
        .param("password", testUserDTO1.getPassword())
        .param("fullname", testUserDTO1.getFullname())
        .param("role", ""))
        .andExpect(model().hasErrors())
        .andExpect(model().size(1))
        .andExpect(model().attributeExists("userDTO"))
        .andExpect(view().name("user/update"))
        .andExpect(status().is(200))
        .andReturn();

        String content = result.getResponse().getContentAsString();
        
        assertThat(content).contains("Should be alphabets and minimum more than 2 characters");
       
    }

    // ********************************************************************

    @WithMockUser(username = "admin", authorities = { "ADMIN", "USER"})
    @DisplayName("Url request /user/update/{id} - RoleWithNumericValues- "
    		+ " - Given a User - RoleWithNumericValues -,"
    		+ " when POST /user/update/{id} action request,"
    		+ " then returns error & redirect /user/update page")    
    @Test
    public void testPostUserUpdateWithRoleWithNumericValues() throws Exception {

    	MvcResult result = mockMvc.perform(post("/user/update/2")
        .sessionAttr("userDTO", testUserDTO1)
        .param("username",  testUserDTO1.getUsername())
        .param("password", testUserDTO1.getPassword())
        .param("fullname", testUserDTO1.getFullname())
        .param("role", "Role123455"))
        .andExpect(model().hasErrors())
        .andExpect(model().size(1))
        .andExpect(model().attributeExists("userDTO"))
        .andExpect(view().name("user/update"))
        .andExpect(status().is(200))
        .andReturn();

        String content = result.getResponse().getContentAsString();
        
        assertThat(content).contains("Should be alphabets and minimum more than 2 characters");
       
    }

    // ********************************************************************

    @WithMockUser(username = "admin", authorities = { "ADMIN", "USER"})
    @DisplayName("Url request /user/update/{id} - Role WithoutAlphabets- "
    		+ " - Given a User - Role WithoutAlphabets -,"
    		+ " when POST /user/update/{id} action request,"
    		+ " then returns error & redirect /user/update page")    
    @Test
    public void testPostUserUpdateWithRoleWithoutAlphabets() throws Exception {

    	MvcResult result = mockMvc.perform(post("/user/update/2")
        .sessionAttr("userDTO", testUserDTO1)
        .param("username",  testUserDTO1.getUsername())
        .param("password", testUserDTO1.getPassword())
        .param("fullname", testUserDTO1.getFullname())
        .param("role", "12345555555"))
        .andExpect(model().hasErrors())
        .andExpect(model().size(1))
        .andExpect(model().attributeExists("userDTO"))
        .andExpect(view().name("user/update"))
        .andExpect(status().is(200))
        .andReturn();

        String content = result.getResponse().getContentAsString();
        
        assertThat(content).contains("Should be alphabets and minimum more than 2 characters");
       
    }

    // ********************************************************************

    
    
    @WithMockUser(username = "admin", authorities = { "ADMIN", "USER"})
    @DisplayName("Url request /user/update/{id} - Role WithSymbols- "
    		+ " - Given a User - Role WithSymbols -,"
    		+ " when POST /user/update/{id} action request,"
    		+ " then returns error & redirect /user/update page")    
    @Test
    public void testPostUserUpdateWithRoleWithSymbols() throws Exception {

    	MvcResult result = mockMvc.perform(post("/user/update/2")
        .sessionAttr("userDTO", testUserDTO1)
        .param("username",  testUserDTO1.getUsername())
        .param("password", testUserDTO1.getPassword())
        .param("fullname", testUserDTO1.getFullname())
        .param("role", "Role!!&&&"))
        .andExpect(model().hasErrors())
        .andExpect(model().size(1))
        .andExpect(model().attributeExists("userDTO"))
        .andExpect(view().name("user/update"))
        .andExpect(status().is(200))
        .andReturn();

        String content = result.getResponse().getContentAsString();
        
        assertThat(content).contains("Should be alphabets and minimum more than 2 characters");
       
    }

    // ********************************************************************
    
      
    
}
