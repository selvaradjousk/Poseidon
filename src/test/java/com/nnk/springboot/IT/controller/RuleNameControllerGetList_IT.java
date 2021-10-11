package com.nnk.springboot.IT.controller;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
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

import com.nnk.springboot.service.RuleNameService;

@DisplayName("INTEGRATION TESTS - Controller < RULENAME > GET LIST - UNIT TESTS")
@AutoConfigureMockMvc
@SpringBootTest
@ActiveProfiles("test")
class RuleNameControllerGetList_IT {


    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RuleNameService ruleNameService;

    @BeforeEach
    public void setUp() {

    }
    
 	// ********************************************************************

    

    @DisplayName(" Url request /ruleName/list - Without Authentication"
    		+ " - Given a RuleName List,"
    		+ " when GET /ruleName/list action request,"
    		+ " then returns ruleNameslist page")
    @Test
    public void testGetRuleNameListWithoutAuthentication() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/ruleName/list"))
	        .andExpect(status().is(401))
	        .andDo(MockMvcResultHandlers.print())
	        .andExpect(status().isUnauthorized())
	        .andExpect(status().reason(containsString("Full authentication is required to access this resource")))
	        .andExpect(unauthenticated());


    }    
  	// ********************************************************************

    
    @WithMockUser(username = "admin", authorities = { "ADMIN", "USER"})
    @DisplayName(" Url request /ruleName/list - With Authentication"
    		+ " - Given a RuleName List,"
    		+ " when GET /ruleName/list action request,"
    		+ " then returns ruleNameslist page")
    @Test
    public void testGetRuleNameListWithAuthentication() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/ruleName/list"))
                .andExpect(model().attributeExists("ruleNames"))
                .andExpect(model().size(1))
                .andExpect(view().name("ruleName/list"))
                .andExpect(status().isOk());


        assertEquals(5, (ruleNameService.getAllRuleName()).size());

    }

    // ********************************************************************

    @WithMockUser(username = "admin", authorities = { "ADMIN", "USER"})
    @DisplayName(" Url request /ruleName/list - NULL LIST "
    		+ " - Given a RuleName List,"
    		+ " when GET /ruleName/list action request,"
    		+ " then returns ruleNameslist page")    
    @Test
    public void testGetRuleNameListNull() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/ruleName/list"))
                .andExpect(model().size(1))
                .andExpect(view().name("ruleName/list"))
                .andExpect(status().isOk());

    }

    // ********************************************************************
   
 
}
