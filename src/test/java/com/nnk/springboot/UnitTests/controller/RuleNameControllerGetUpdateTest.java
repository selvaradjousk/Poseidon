package com.nnk.springboot.UnitTests.controller;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

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
import com.nnk.springboot.config.JwtUtils;
import com.nnk.springboot.config.MyUserDetailsService;
import com.nnk.springboot.controller.RuleNameController;
import com.nnk.springboot.dto.RuleNameDTO;
import com.nnk.springboot.service.RuleNameService;

@DisplayName("Controller < RULENAME - GET UPDATE> - UNIT TESTS")
@ExtendWith(SpringExtension.class)
@WebMvcTest(RuleNameController.class)
class RuleNameControllerGetUpdateTest {


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
        
        testRuleNameDTO2 = RuleNameDTO.builder()
        		.id(2)
        		.name("RuleName2")
        		.description("Description2")
        		.json("JSON2")
        		.template("template2")
        		.sqlStr("SqlStr2")
        		.sqlPart("SqlPart2")
        		.build();

        
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }
    // ********************************************************************


    
    @DisplayName(" Url request /ruleName/update/{id} - "
    		+ " - Given a RuleName,"
    		+ " when GET /ruleName/update/{id} action request,"
    		+ " then returns ruleName ADD page")    
    @Test
    public void testGetRuleNameUpdateById() throws Exception {
    	 when(ruleNameService.getRuleNameById(1)).thenReturn(testRuleNameDTO1);
        mockMvc.perform(MockMvcRequestBuilders.get("/ruleName/update/1"))
                .andExpect(model().attributeExists("ruleNameDTO"))
                .andExpect(model().size(1))
                .andExpect(view().name("ruleName/update"))
                .andExpect(status().isOk());

        verify(ruleNameService).getRuleNameById(1);
    }

    // ********************************************************************

    
    
}
