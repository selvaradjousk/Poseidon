package com.nnk.springboot.UnitTests.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
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
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nnk.springboot.controller.RuleNameController;
import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.dto.RuleNameDTO;
import com.nnk.springboot.service.RuleNameService;

@DisplayName("Controller < RULENAME > GET LIST - UNIT TESTS")
@ExtendWith(SpringExtension.class)
@WebMvcTest(RuleNameController.class)
class RuleNameControllerGetListTest {


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
    
    private static RuleName testRuleName1, testRuleName2;
    
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

    
    @DisplayName(" Url request /ruleName/list - "
    		+ " - Given a RuleName List,"
    		+ " when GET /ruleName/list action request,"
    		+ " then returns ruleNameslist page")
    @Test
    public void testGetRuleNameList() throws Exception {
        when(ruleNameService.getAllRuleName()).thenReturn(ruleNameDTOList);
        mockMvc.perform(MockMvcRequestBuilders.get("/ruleName/list"))
                .andExpect(model().attributeExists("ruleNames"))
                .andExpect(model().size(1))
                .andExpect(view().name("ruleName/list"))
                .andExpect(status().isOk());

        verify(ruleNameService, times(1)).getAllRuleName();
        assertEquals(2, (ruleNameService.getAllRuleName()).size());
        assertThat(ruleNameDTOList).usingRecursiveComparison().isEqualTo(ruleNameService.getAllRuleName()).toString();
    }

    // ********************************************************************

    
    @DisplayName(" Url request /ruleName/list - NULL LIST "
    		+ " - Given a RuleName List,"
    		+ " when GET /ruleName/list action request,"
    		+ " then returns ruleNameslist page")    
    @Test
    public void testGetRuleNameListNull() throws Exception {
        when(ruleNameService.getAllRuleName()).thenReturn(null);
        mockMvc.perform(MockMvcRequestBuilders.get("/ruleName/list"))
//                .andExpect(model().attributeExists("ruleNames"))
                .andExpect(model().size(1))
                .andExpect(view().name("ruleName/list"))
                .andExpect(status().isOk());

        verify(ruleNameService, times(1)).getAllRuleName();
        assertNull((ruleNameService.getAllRuleName()));
    }

    // ********************************************************************
   
 
}
