package com.nnk.springboot.UnitTests.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
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
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nnk.springboot.config.AuthTokenFilter;
import com.nnk.springboot.config.MyUserDetailsService;
import com.nnk.springboot.controller.UserController;
import com.nnk.springboot.dto.UserDTO;
import com.nnk.springboot.service.UserService;

@DisplayName("Controller < USER > - UNIT TESTS")
@ExtendWith(SpringExtension.class)
@WebMvcTest(UserController.class)
class UserControllerPostValidateTest {


	@MockBean
	AuthTokenFilter authenticationJwtTokenFilter;
	
    @MockBean
    private UserService userService;

    @MockBean
    private MyUserDetailsService userDetailsService;


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


    @DisplayName(" Url request /user/validate - "
    		+ " - Given a User,"
    		+ " when POST /user/validate action request,"
    		+ " then returns redirect /user/validate page")    
    @Test
    public void testPostUserValidate() throws Exception {
    	when(userService.getAllUser()).thenReturn(userDTOList);
    	when(userService.addUser(any(UserDTO.class))).thenReturn(any(UserDTO.class));
        
        mockMvc.perform(MockMvcRequestBuilders.post("/user/validate")
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
        verify(userService, times(1)).addUser(any(UserDTO.class));
    }

    // ********************************************************************


    @DisplayName(" Url request /user/validate - "
    		+ " - Given a User - empty username,"
    		+ " when POST /user/validate action request,"
    		+ " then returns error & redirect /user/add page")    
    @Test
    public void testPostUserValidateEmptyUserName() throws Exception {
    	when(userService.getAllUser()).thenReturn(userDTOList);
//    	when(userService.addUser(any(UserDTO.class))).thenReturn(any(UserDTO.class));
        
    	MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/user/validate")
        .sessionAttr("userDTO", testUserDTO1)
        .param("username", "")
        .param("password", testUserDTO1.getPassword())
        .param("fullname", testUserDTO1.getFullname())
        .param("role", testUserDTO1.getRole()))
        .andExpect(model().hasErrors())
        .andExpect(model().size(1))
        .andExpect(model().attributeExists("userDTO"))
        .andExpect(view().name("user/add"))
        .andExpect(status().is(200))
        .andReturn();

        verify(userService, times(0)).getAllUser();
        verify(userService, times(0)).addUser(any(UserDTO.class));

        String content = result.getResponse().getContentAsString();
        
        assertThat(content).contains("Username is mandatory");
    }

    // ********************************************************************



    @DisplayName("Url request /user/validate - UserName  Non Alphanumeric characters - "
    		+ " - Given a User - username with Non Alphanumeric characters,"
    		+ " when POST /user/validate action request,"
    		+ " then returns error & redirect /user/add page")    
    @Test
    public void testPostUserValidateWithUserNameHavingSymbols() throws Exception {
    	when(userService.getAllUser()).thenReturn(userDTOList);
//    	when(userService.addUser(any(UserDTO.class))).thenReturn(any(UserDTO.class));
        
    	MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/user/validate")
        .sessionAttr("userDTO", testUserDTO1)
        .param("username", "Username!&&&")
        .param("password", testUserDTO1.getPassword())
        .param("fullname", testUserDTO1.getFullname())
        .param("role", testUserDTO1.getRole()))
        .andExpect(model().hasErrors())
        .andExpect(model().size(1))
        .andExpect(model().attributeExists("userDTO"))
        .andExpect(view().name("user/add"))
        .andExpect(status().is(200))
        .andReturn();

        verify(userService, times(0)).getAllUser();
        verify(userService, times(0)).addUser(any(UserDTO.class));
        

        String content = result.getResponse().getContentAsString();
        
        assertThat(content).contains("Should be alphanumeric or email and minimum more than 2 characters");
    }

    // ********************************************************************


    @DisplayName("Url request /user/validate - Empty Password - "
    		+ " - Given a User - Empty Password,"
    		+ " when POST /user/validate action request,"
    		+ " then returns error & redirect /user/add page")    
    @Test
    public void testPostUserValidateWithPasswordEmpty() throws Exception {
    	when(userService.getAllUser()).thenReturn(userDTOList);
//    	when(userService.addUser(any(UserDTO.class))).thenReturn(any(UserDTO.class));
        
    	MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/user/validate")
        .sessionAttr("userDTO", testUserDTO1)
        .param("username",  testUserDTO1.getUsername())
        .param("password", "")
        .param("fullname", testUserDTO1.getFullname())
        .param("role", testUserDTO1.getRole()))
        .andExpect(model().hasErrors())
        .andExpect(model().size(1))
        .andExpect(model().attributeExists("userDTO"))
        .andExpect(view().name("user/add"))
        .andExpect(status().is(200))
        .andReturn();

        verify(userService, times(0)).getAllUser();
        verify(userService, times(0)).addUser(any(UserDTO.class));
        

        String content = result.getResponse().getContentAsString();
        
        assertThat(content).contains("Password is mandatory");

    }

    // ********************************************************************



    @DisplayName("Url request /user/validate - Password No capital Letters- "
    		+ " - Given a User - Password No capital Letters-,"
    		+ " when POST /user/validate action request,"
    		+ " then returns error & redirect /user/add page")    
    @Test
    public void testPostUserValidateWithPasswordyWithoutCapitalLetters() throws Exception {
    	when(userService.getAllUser()).thenReturn(userDTOList);
//    	when(userService.addUser(any(UserDTO.class))).thenReturn(any(UserDTO.class));
        
    	MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/user/validate")
        .sessionAttr("userDTO", testUserDTO1)
        .param("username",  testUserDTO1.getUsername())
        .param("password", "password&1")
        .param("fullname", testUserDTO1.getFullname())
        .param("role", testUserDTO1.getRole()))
        .andExpect(model().hasErrors())
        .andExpect(model().size(1))
        .andExpect(model().attributeExists("userDTO"))
        .andExpect(view().name("user/add"))
        .andExpect(status().is(200))
        .andReturn();

        verify(userService, times(0)).getAllUser();
        verify(userService, times(0)).addUser(any(UserDTO.class));
        

        String content = result.getResponse().getContentAsString();
        
        assertThat(content).contains("The password must contain at least 8 characters that includes any one uppercase letter, any one number and any one symbol ( &amp; ~ # @ = * - + € ^ $ £ µ % )");
        
    }

    // ********************************************************************



    @DisplayName("Url request /user/validate - Password WithoutSmallLetters- "
    		+ " - Given a User - Password No WithoutSmallLetters-,"
    		+ " when POST /user/validate action request,"
    		+ " then returns error & redirect /user/add page")    
    @Test
    public void testPostUserValidateWithPasswordWithoutSmallLetters() throws Exception {
    	when(userService.getAllUser()).thenReturn(userDTOList);
//    	when(userService.addUser(any(UserDTO.class))).thenReturn(any(UserDTO.class));
        
    	MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/user/validate")
        .sessionAttr("userDTO", testUserDTO1)
        .param("username",  testUserDTO1.getUsername())
        .param("password", "PASSWORD&1")
        .param("fullname", testUserDTO1.getFullname())
        .param("role", testUserDTO1.getRole()))
        .andExpect(model().hasErrors())
        .andExpect(model().size(1))
        .andExpect(model().attributeExists("userDTO"))
        .andExpect(view().name("user/add"))
        .andExpect(status().is(200))
        .andReturn();

        verify(userService, times(0)).getAllUser();
        verify(userService, times(0)).addUser(any(UserDTO.class));
        

        String content = result.getResponse().getContentAsString();
        
        assertThat(content).contains("The password must contain at least 8 characters that includes any one uppercase letter, any one number and any one symbol ( &amp; ~ # @ = * - + € ^ $ £ µ % )");
              
    }

    // ********************************************************************



    @DisplayName("Url request /user/validate - Password WithoutAlphabets- "
    		+ " - Given a User - Password No WithoutAlphabets-,"
    		+ " when POST /user/validate action request,"
    		+ " then returns error & redirect /user/add page")    
    @Test
    public void testPostUserValidateWithPasswordWithoutAlphabets() throws Exception {
    	when(userService.getAllUser()).thenReturn(userDTOList);
//    	when(userService.addUser(any(UserDTO.class))).thenReturn(any(UserDTO.class));
        
    	MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/user/validate")
        .sessionAttr("userDTO", testUserDTO1)
        .param("username",  testUserDTO1.getUsername())
        .param("password", "!!!!!!!!!!!!&1")
        .param("fullname", testUserDTO1.getFullname())
        .param("role", testUserDTO1.getRole()))
        .andExpect(model().hasErrors())
        .andExpect(model().size(1))
        .andExpect(model().attributeExists("userDTO"))
        .andExpect(view().name("user/add"))
        .andExpect(status().is(200))
        .andReturn();

        verify(userService, times(0)).getAllUser();
        verify(userService, times(0)).addUser(any(UserDTO.class));

        String content = result.getResponse().getContentAsString();
        
        assertThat(content).contains("The password must contain at least 8 characters that includes any one uppercase letter, any one number and any one symbol ( &amp; ~ # @ = * - + € ^ $ £ µ % )");
       
    }

    // ********************************************************************



    @DisplayName("Url request /user/validate - Password No NumericValues- "
    		+ " - Given a User - Password No NumericValues-,"
    		+ " when POST /user/validate action request,"
    		+ " then returns error & redirect /user/add page")    
    @Test
    public void testPostUserValidateWithPasswordWithOutNumberValues() throws Exception {
    	when(userService.getAllUser()).thenReturn(userDTOList);
//    	when(userService.addUser(any(UserDTO.class))).thenReturn(any(UserDTO.class));
        
    	MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/user/validate")
        .sessionAttr("userDTO", testUserDTO1)
        .param("username",  testUserDTO1.getUsername())
        .param("password", "Password&!!!")
        .param("fullname", testUserDTO1.getFullname())
        .param("role", testUserDTO1.getRole()))
        .andExpect(model().hasErrors())
        .andExpect(model().size(1))
        .andExpect(model().attributeExists("userDTO"))
        .andExpect(view().name("user/add"))
        .andExpect(status().is(200))
        .andReturn();

        verify(userService, times(0)).getAllUser();
        verify(userService, times(0)).addUser(any(UserDTO.class));

        String content = result.getResponse().getContentAsString();
        
        assertThat(content).contains("The password must contain at least 8 characters that includes any one uppercase letter, any one number and any one symbol ( &amp; ~ # @ = * - + € ^ $ £ µ % )");
       
    }

    // ********************************************************************



    @DisplayName("Url request /user/validate - Password < 8 Characters- "
    		+ " - Given a User - Password  < 8 Characters-,"
    		+ " when POST /user/validate action request,"
    		+ " then returns error & redirect /user/add page")    
    @Test
    public void testPostUserValidateWithPasswordLessThan8Characters() throws Exception {
    	when(userService.getAllUser()).thenReturn(userDTOList);
//    	when(userService.addUser(any(UserDTO.class))).thenReturn(any(UserDTO.class));
        
    	MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/user/validate")
        .sessionAttr("userDTO", testUserDTO1)
        .param("username",  testUserDTO1.getUsername())
        .param("password", "P&!1")
        .param("fullname", testUserDTO1.getFullname())
        .param("role", testUserDTO1.getRole()))
        .andExpect(model().hasErrors())
        .andExpect(model().size(1))
        .andExpect(model().attributeExists("userDTO"))
        .andExpect(view().name("user/add"))
        .andExpect(status().is(200))
        .andReturn();

        verify(userService, times(0)).getAllUser();
        verify(userService, times(0)).addUser(any(UserDTO.class));

        String content = result.getResponse().getContentAsString();
        
        assertThat(content).contains("The password must contain at least 8 characters that includes any one uppercase letter, any one number and any one symbol ( &amp; ~ # @ = * - + € ^ $ £ µ % )");
       
    }

    // ********************************************************************


    @DisplayName("Url request /user/validate - Password No Symbols- "
    		+ " - Given a User - Password No Symbols-,"
    		+ " when POST /user/validate action request,"
    		+ " then returns error & redirect /user/add page")    
    @Test
    public void testPostUserValidateWithPasswordWithoutSymbols() throws Exception {
    	when(userService.getAllUser()).thenReturn(userDTOList);
//    	when(userService.addUser(any(UserDTO.class))).thenReturn(any(UserDTO.class));
        
    	MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/user/validate")
        .sessionAttr("userDTO", testUserDTO1)
        .param("username",  testUserDTO1.getUsername())
        .param("password", "passwordddd1")
        .param("fullname", testUserDTO1.getFullname())
        .param("role", testUserDTO1.getRole()))
        .andExpect(model().hasErrors())
        .andExpect(model().size(1))
        .andExpect(model().attributeExists("userDTO"))
        .andExpect(view().name("user/add"))
        .andExpect(status().is(200))
        .andReturn();

        verify(userService, times(0)).getAllUser();
        verify(userService, times(0)).addUser(any(UserDTO.class));

        String content = result.getResponse().getContentAsString();
        
        assertThat(content).contains("The password must contain at least 8 characters that includes any one uppercase letter, any one number and any one symbol ( &amp; ~ # @ = * - + € ^ $ £ µ % )");
       
    }

    // ********************************************************************



    @DisplayName("Url request /user/validate - FullNameEmpty- "
    		+ " - Given a User - FullNameEmpty-,"
    		+ " when POST /user/validate action request,"
    		+ " then returns error & redirect /user/add page")    
    @Test
    public void testPostUserValidateWithFullNameEmpty() throws Exception {
    	when(userService.getAllUser()).thenReturn(userDTOList);
//    	when(userService.addUser(any(UserDTO.class))).thenReturn(any(UserDTO.class));
        
    	MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/user/validate")
        .sessionAttr("userDTO", testUserDTO1)
        .param("username",  testUserDTO1.getUsername())
        .param("password", testUserDTO1.getPassword())
        .param("fullname", "")
        .param("role", testUserDTO1.getRole()))
        .andExpect(model().hasErrors())
        .andExpect(model().size(1))
        .andExpect(model().attributeExists("userDTO"))
        .andExpect(view().name("user/add"))
        .andExpect(status().is(200))
        .andReturn();

        verify(userService, times(0)).getAllUser();
        verify(userService, times(0)).addUser(any(UserDTO.class));

        String content = result.getResponse().getContentAsString();
        
        assertThat(content).contains("Should be alphanumeric and minimum more than 2 characters");
       
    }

    // ********************************************************************


    @DisplayName("Url request /user/validate - FullNameWithSymbol- "
    		+ " - Given a User - FullNameWithSymbol-,"
    		+ " when POST /user/validate action request,"
    		+ " then returns error & redirect /user/add page")    
    @Test
    public void testPostUserValidateWithFullNameWithSymbol() throws Exception {
    	when(userService.getAllUser()).thenReturn(userDTOList);
//    	when(userService.addUser(any(UserDTO.class))).thenReturn(any(UserDTO.class));
        
    	MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/user/validate")
        .sessionAttr("userDTO", testUserDTO1)
        .param("username",  testUserDTO1.getUsername())
        .param("password", testUserDTO1.getPassword())
        .param("fullname", "Fullname&&&&")
        .param("role", testUserDTO1.getRole()))
        .andExpect(model().hasErrors())
        .andExpect(model().size(1))
        .andExpect(model().attributeExists("userDTO"))
        .andExpect(view().name("user/add"))
        .andExpect(status().is(200))
        .andReturn();

        verify(userService, times(0)).getAllUser();
        verify(userService, times(0)).addUser(any(UserDTO.class));

        String content = result.getResponse().getContentAsString();
        
        assertThat(content).contains("Should be alphanumeric and minimum more than 2 characters");
       
    }

    // ********************************************************************


    @DisplayName("Url request /user/validate - RoleEmpty- "
    		+ " - Given a User - RoleEmpty -,"
    		+ " when POST /user/validate action request,"
    		+ " then returns error & redirect /user/add page")    
    @Test
    public void testPostUserValidateWithRoleEmpty() throws Exception {
    	when(userService.getAllUser()).thenReturn(userDTOList);
//    	when(userService.addUser(any(UserDTO.class))).thenReturn(any(UserDTO.class));
        
    	MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/user/validate")
        .sessionAttr("userDTO", testUserDTO1)
        .param("username",  testUserDTO1.getUsername())
        .param("password", testUserDTO1.getPassword())
        .param("fullname", testUserDTO1.getFullname())
        .param("role", ""))
        .andExpect(model().hasErrors())
        .andExpect(model().size(1))
        .andExpect(model().attributeExists("userDTO"))
        .andExpect(view().name("user/add"))
        .andExpect(status().is(200))
        .andReturn();

        verify(userService, times(0)).getAllUser();
        verify(userService, times(0)).addUser(any(UserDTO.class));

        String content = result.getResponse().getContentAsString();
        
        assertThat(content).contains("Should be alphabets and minimum more than 2 characters");
       
    }

    // ********************************************************************

    @DisplayName("Url request /user/validate - RoleWithNumericValues- "
    		+ " - Given a User - RoleWithNumericValues -,"
    		+ " when POST /user/validate action request,"
    		+ " then returns error & redirect /user/add page")    
    @Test
    public void testPostUserValidateWithRoleWithNumericValues() throws Exception {
    	when(userService.getAllUser()).thenReturn(userDTOList);
//    	when(userService.addUser(any(UserDTO.class))).thenReturn(any(UserDTO.class));
        
    	MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/user/validate")
        .sessionAttr("userDTO", testUserDTO1)
        .param("username",  testUserDTO1.getUsername())
        .param("password", testUserDTO1.getPassword())
        .param("fullname", testUserDTO1.getFullname())
        .param("role", "Role123455"))
        .andExpect(model().hasErrors())
        .andExpect(model().size(1))
        .andExpect(model().attributeExists("userDTO"))
        .andExpect(view().name("user/add"))
        .andExpect(status().is(200))
        .andReturn();

        verify(userService, times(0)).getAllUser();
        verify(userService, times(0)).addUser(any(UserDTO.class));

        String content = result.getResponse().getContentAsString();
        
        assertThat(content).contains("Should be alphabets and minimum more than 2 characters");
       
    }

    // ********************************************************************

    @DisplayName("Url request /user/validate - Role WithoutAlphabets- "
    		+ " - Given a User - Role WithoutAlphabets -,"
    		+ " when POST /user/validate action request,"
    		+ " then returns error & redirect /user/add page")    
    @Test
    public void testPostUserValidateWithRoleWithoutAlphabets() throws Exception {
    	when(userService.getAllUser()).thenReturn(userDTOList);
//    	when(userService.addUser(any(UserDTO.class))).thenReturn(any(UserDTO.class));
        
    	MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/user/validate")
        .sessionAttr("userDTO", testUserDTO1)
        .param("username",  testUserDTO1.getUsername())
        .param("password", testUserDTO1.getPassword())
        .param("fullname", testUserDTO1.getFullname())
        .param("role", "12345555555"))
        .andExpect(model().hasErrors())
        .andExpect(model().size(1))
        .andExpect(model().attributeExists("userDTO"))
        .andExpect(view().name("user/add"))
        .andExpect(status().is(200))
        .andReturn();

        verify(userService, times(0)).getAllUser();
        verify(userService, times(0)).addUser(any(UserDTO.class));

        String content = result.getResponse().getContentAsString();
        
        assertThat(content).contains("Should be alphabets and minimum more than 2 characters");
       
    }

    // ********************************************************************
  
    
    

    @DisplayName("Url request /user/validate - Role WithSymbols- "
    		+ " - Given a User - Role WithSymbols -,"
    		+ " when POST /user/validate action request,"
    		+ " then returns error & redirect /user/add page")    
    @Test
    public void testPostUserValidateWithRoleWithSymbols() throws Exception {
    	when(userService.getAllUser()).thenReturn(userDTOList);
//    	when(userService.addUser(any(UserDTO.class))).thenReturn(any(UserDTO.class));
        
    	MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/user/validate")
        .sessionAttr("userDTO", testUserDTO1)
        .param("username",  testUserDTO1.getUsername())
        .param("password", testUserDTO1.getPassword())
        .param("fullname", testUserDTO1.getFullname())
        .param("role", "Role!!&&&"))
        .andExpect(model().hasErrors())
        .andExpect(model().size(1))
        .andExpect(model().attributeExists("userDTO"))
        .andExpect(view().name("user/add"))
        .andExpect(status().is(200))
        .andReturn();

        verify(userService, times(0)).getAllUser();
        verify(userService, times(0)).addUser(any(UserDTO.class));

        String content = result.getResponse().getContentAsString();
        
        assertThat(content).contains("Should be alphabets and minimum more than 2 characters");
       
    }

    // ********************************************************************
   
    
    
}
