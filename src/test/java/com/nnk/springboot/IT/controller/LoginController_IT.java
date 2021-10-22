package com.nnk.springboot.IT.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.oauth2Login;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.options;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

@DisplayName("INTEGRATION TESTS - Controller <LOGIN>")
@AutoConfigureMockMvc
@SpringBootTest
@ActiveProfiles("test")
class LoginController_IT {

    @Autowired
    private MockMvc mockMvc;


    @BeforeEach
    public void setUp() {

    }
    
    
	// ********************************************************************
    
    @DisplayName("Login Url request"
    		+ " - Given login url /login request,"
    		+ " when GET /login request,"
    		+ " then return login page")	
    @Test
    public void testHomePage() throws Exception {
        mockMvc.perform(get("/login"))
                .andExpect(view().name("login"))
                .andExpect(status().isOk());
    }    
   
    

	// ********************************************************************
 
    
    @DisplayName("Error Url request - Without Authentication"
    		+ " - Given Error url /error request,"
    		+ " when GET /error request,"
    		+ " then returns Error Authentication required")	
    @Test
    public void testErrorWithoutAuthentication() throws Exception {
        mockMvc.perform(get("/error"))
        .andExpect(status().is(401))
        .andDo(MockMvcResultHandlers.print())
        .andExpect(status().isUnauthorized())
        .andExpect(status().reason(containsString("Full authentication is required to access this resource")))
        .andExpect(unauthenticated());
    }    
   
    
	// ********************************************************************
 
    @WithMockUser(username="admin", roles={"ADMIN", "USER"})
    @DisplayName("Error Url request - With Authentication"
    		+ " - Given Error url /error request,"
    		+ " when GET /error request,"
    		+ " then return error page")	
    @Test
    public void testErrorWithAuthentication() throws Exception {
        mockMvc.perform(get("/error"))
                .andExpect(view().name("403"))
                .andExpect(status().isOk());
    }    
   
    

	// ********************************************************************
  
    @DisplayName("login Url request - With Wrong Authentication"
    		+ " - Given login url /login request,"
    		+ " when GET /login request,"
    		+ " then return error page")
    @Test
    public void loggingInWithWrongUser() throws Exception {
    	mockMvc.perform(formLogin()
            .user("joey").password("12345"))
//            .andExpect(header().exists("/login?error=true"))
    		.andExpect(redirectedUrl("/login?error=true"))
            .andExpect(unauthenticated());

    }  

	// ********************************************************************
//    @Test
//    public void testUserLogin() throws Exception {
//        RequestBuilder requestBuilder = post("/login")
//                .param("username", "admin")
//                .param("password", "Password1!");
//
//        mockMvc.perform(requestBuilder)
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(cookie().exists("JSESSIONID"));
//    }    

    @DisplayName("ERROR pagewith OAuth login")
    @Test
    void getErrorPageOAuth2Auth() throws Exception {
        mockMvc.perform(get("/error").with(oauth2Login()))
                .andExpect(status().isOk());
    }
    
	// ********************************************************************
      
    
    @DisplayName("login Url request - With Correct Authority"
    		+ " - Given correct authority url /login request,"
    		+ " when GET /login request,"
    		+ " then return /bidList/list page")
    @Test
    public void loggingInWithCorrectAuthority() throws Exception {
    	mockMvc.perform(formLogin()
                  .user("admin").password("Password1!"))
              .andExpect(redirectedUrl("/bidList/list"))
              .andExpect(status().isFound())
              .andExpect(authenticated());
      }

    // ********************************************************************
    
    @DisplayName("login Url request - With PasswordInvalid"
    		+ " - Given PasswordInvalid url /login request,"
    		+ " when GET /login request,"
    		+ " then return /bidList/list page")
    @Test
    public void loggingInWithPasswordInvalid() throws Exception {
    	mockMvc.perform(formLogin()
                  .user("admin").password("Password1!sqdfqsfd"))
              .andExpect(redirectedUrl("/login?error=true"))
              .andExpect(status().is(302))
              .andExpect(unauthenticated());
      }
    // ********************************************************************
    
    @DisplayName("test authentication is by UsernamePasswordAuthenticationToken"
    		+ " - Given Admin user url /login request,"
    		+ " when GET /login request,"
    		+ " then return auth is by UsernamePasswordAuthenticationToken")
    @Test
    public void testAuthByUsernamePasswordAuthenticationToken() throws Exception {
    	mockMvc
        .perform(formLogin().user("admin").password("Password1!"))
        .andExpect(authenticated().withAuthentication(auth ->
            assertThat(auth).isInstanceOf(UsernamePasswordAuthenticationToken.class)));
      }

	// ********************************************************************
    @Test
    public void testLoginForTestEndpointUnauthenticated() throws Exception {
    	mockMvc.perform(options("/login")
              .header("Access-Control-Request-Method", "POST")
              .header("Origin", "http://www.example.com")
        )
        .andExpect(status().is(403));
    }

	// ********************************************************************
    @WithMockUser(username="admin", roles={"ADMIN", "USER"})
    @Test
    public void testLoginForTestEndpointAuthenticated() throws Exception {
    	mockMvc.perform(options("/login")
              .header("Access-Control-Request-Method", "POST")
              .header("Origin", "http://www.example.com")
        )
        .andExpect(status().is(403));
    }

}
