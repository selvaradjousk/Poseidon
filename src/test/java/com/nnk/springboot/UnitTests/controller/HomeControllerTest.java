package com.nnk.springboot.UnitTests.controller;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.nnk.springboot.config.AuthTokenFilter;
import com.nnk.springboot.config.JwtUtils;
import com.nnk.springboot.config.MyUserDetailsService;
import com.nnk.springboot.controller.HomeController;

@DisplayName("Controller <HOME> - UNIT TESTS")
@ExtendWith(SpringExtension.class)
@WebMvcTest(HomeController.class)
class HomeControllerTest {

	@MockBean
	AuthTokenFilter authenticationJwtTokenFilter;
	
    @MockBean
    private MyUserDetailsService userDetailsService;

    @MockBean
    private JwtUtils jwtUtils;

    @Autowired
    private MockMvc mockMvc;


    @Autowired
    private WebApplicationContext context;
   
    @Mock
    private Authentication auth;
    
    @Mock
    private GrantedAuthority role;
    
    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }    
    
 
	// ********************************************************************
    
    @DisplayName("HOME Url request"
    		+ " - Given home url / request,"
    		+ " when GET / or /home request,"
    		+ " then return home page")	
    @Test
    public void testHomePage() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
        		.get("/"))
                .andExpect(view().name("home"))
                .andExpect(status().isOk());
    }    
   
    

	// ********************************************************************

    @WithMockUser(username="admin", roles={"ADMIN"})
    @DisplayName("HOME Admin Url request"
    		+ " - Given adminHome url /admin/home request,"
    		+ " when GET /admin/home request,"
    		+ " then redirect to /bidList/list")	
    @Test
    public void testAdminHomeRequest() throws Exception {
    	
//    	
//    	auth.setAuthenticated(true);
//    	when(auth.getAuthorities()).thenReturn(null)
//    	SecurityContextHolder.getContext().setAuthentication(auth);
    	
        mockMvc.perform(MockMvcRequestBuilders
        		.get("/admin/home/")
        		.with(user("Admin")
                        .password("Password1!")
                        .roles("ADMIN")))
                .andExpect(redirectedUrl("/bidList/list"));
    }

}
