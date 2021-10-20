package com.nnk.springboot.UnitTests.controller;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nnk.springboot.config.AuthTokenFilter;
import com.nnk.springboot.config.JwtAuthenticationSuccessHandler;
import com.nnk.springboot.config.JwtUtils;
import com.nnk.springboot.config.MyUserDetailsService;
import com.nnk.springboot.controller.RuleNameController;
import com.nnk.springboot.dto.RuleNameDTO;
import com.nnk.springboot.service.RuleNameService;

@DisplayName("Controller < RULENAME > DELETE - UNIT TESTS")
@ExtendWith(SpringExtension.class)
@WebMvcTest(RuleNameController.class)
class RuleNameControllerGetDeleteTest {

	@MockBean
	JwtAuthenticationSuccessHandler myAuthenticationSuccessHandler;

	@MockBean
	AuthTokenFilter authenticationJwtTokenFilter;

    @MockBean
    private RuleNameService ruleNameService;

    @MockBean
    private MyUserDetailsService userDetailsService;

    @MockBean
    private JwtUtils jwtUtils;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;


    private ObjectMapper objectMapper;
	
    private static RuleNameDTO testRuleNameDTO1, testRuleNameDTO2;
    


    @BeforeEach
    public void setUp() {

    	objectMapper = new ObjectMapper();
        testRuleNameDTO1 = RuleNameDTO.builder()
        		.id(1)
        		.name("RuleName1")
        		.description("Description1")
        		.json("JSON1")
        		.template("template1")
        		.sqlStr("SqlStr1")
        		.sqlPart("SqlPart1")
        		.build();
    
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }
    
  	// ********************************************************************

    
    @DisplayName(" Url request ruleName/delete/{id} valid id- "
    		+ " - Given a ruleName/delete/{id} valid id, "
    		+ " when GET ruleName/delete action request,"
    		+ " then returns delete page")    
    @Test
    public void testGetRuleNameDelete() throws Exception {
    	when(ruleNameService.getRuleNameById(1)).thenReturn(testRuleNameDTO1);
    	mockMvc.perform(MockMvcRequestBuilders.get("/ruleName/delete/1"))
    				.andExpect(redirectedUrl("/ruleName/list"))
            		.andExpect(status().isFound())
            		.andExpect(model().hasNoErrors())
            		.andExpect(status().is(302));

        verify(ruleNameService, times(1)).deleteRuleName(1);
       
    }

    // ********************************************************************


    
    @DisplayName(" Url request ruleName/delete/{id} invalid id "
    		+ " - Given a RuleName ruleName/delete/{id} invalid id,"
    		+ " when GET ruleName/delete action request,"
    		+ " then returns delete page")    
    @Test
    public void testGetRuleNameDeleteNull() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/ruleName/delete/"))
        		.andExpect(redirectedUrl(null))
                .andExpect(status().is(404))
        		.andExpect(status().isNotFound());

        verify(ruleNameService, times(0)).deleteRuleName(1);
    }

    // ********************************************************************
   
   
}
