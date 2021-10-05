package com.nnk.springboot.UnitTests.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nnk.springboot.controller.RuleNameController;
import com.nnk.springboot.dto.RuleNameDTO;
import com.nnk.springboot.service.RuleNameService;

@DisplayName("Controller < RULENAME > -VALIDATE UNIT TESTS")
@ExtendWith(SpringExtension.class)
@WebMvcTest(RuleNameController.class)
class RuleNameControllerPostValidateTest {


    @MockBean
    private RuleNameService ruleNameService;

    @MockBean
    private UserDetailsService userDetailsService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    private ObjectMapper objectMapper;
	
    private static RuleNameDTO testRuleNameDTO1, testRuleNameDTO2;
 
    private static List<RuleNameDTO> ruleNameDTOList;

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
        
        ruleNameDTOList = Arrays.asList(testRuleNameDTO1, testRuleNameDTO2); 
        
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }
    
  	// ********************************************************************


    @DisplayName(" Url request /ruleName/validate - "
    		+ " - Given a RuleName,"
    		+ " when POST /ruleName/validate action request,"
    		+ " then returns redirect /ruleName/validate page")    
    @Test
    public void testPostRuleNameValidate() throws Exception {

    	when(ruleNameService
    			.addRuleName(any(RuleNameDTO.class)))
    	.thenReturn(any(RuleNameDTO.class));
        
        mockMvc.perform(MockMvcRequestBuilders.post("/ruleName/validate")
        .sessionAttr("ruleNameDTO", testRuleNameDTO1)
        .param("id", testRuleNameDTO1.getId().toString())
        .param("name", testRuleNameDTO1.getName())
        .param("description", testRuleNameDTO1.getDescription())
        .param("json", testRuleNameDTO1.getJson())
        .param("template", testRuleNameDTO1.getTemplate())
        .param("sqlStr", testRuleNameDTO1.getSqlStr())
        .param("sqlPart", testRuleNameDTO1.getSqlPart()))
        .andExpect(model().hasNoErrors())
        .andExpect(model().size(0))
        .andExpect(model().attributeDoesNotExist("ruleNameDTO"))
        .andExpect(redirectedUrl("/ruleName/list"))
        .andExpect(status().is(302));

        verify(ruleNameService, times(1)).addRuleName(any(RuleNameDTO.class));
    }

    // ********************************************************************


}
