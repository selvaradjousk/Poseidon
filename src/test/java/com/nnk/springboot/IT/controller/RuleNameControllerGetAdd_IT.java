package com.nnk.springboot.IT.controller;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

@DisplayName("INTEGRATION TESTS - Controller < RULENAME > GET ADD - UNIT TESTS")
@AutoConfigureMockMvc
@SpringBootTest
@ActiveProfiles("test")
class RuleNameControllerGetAdd_IT {


    @Autowired
    private MockMvc mockMvc;
 
    @BeforeEach
    public void setUp() {

    }
 
    // ********************************************************************



    @DisplayName(" Url request /ruleName/add - Without Authentication "
    		+ " - Given a RuleName,"
    		+ " when GET /ruleName/add action request,"
    		+ " then returns ruleName ADD page")    
    @Test
    public void testGetRuleNameAddWithoutAuthentication() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/ruleName/add"))
	        .andExpect(status().is(401))
	        .andDo(MockMvcResultHandlers.print())
	        .andExpect(status().isUnauthorized())
	        .andExpect(status().reason(containsString("Full authentication is required to access this resource")))
	        .andExpect(unauthenticated());

    }

    // ********************************************************************


    @WithMockUser(username = "admin", authorities = { "ADMIN", "USER"})
    @DisplayName(" Url request /ruleName/add - With Authentication "
    		+ " - Given a RuleName,"
    		+ " when GET /ruleName/add action request,"
    		+ " then returns ruleName ADD page")    
    @Test
    public void testGetRuleNameAddWithAuthentication() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/ruleName/add"))
                .andExpect(model().attributeExists("ruleNameDTO"))
                .andExpect(model().size(1))
                .andExpect(view().name("ruleName/add"))
                .andExpect(status().isOk());

    }

    // ********************************************************************

}
