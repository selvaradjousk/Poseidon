package com.nnk.springboot.UnitTests.controller;

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

import com.nnk.springboot.config.AuthTokenFilter;
import com.nnk.springboot.config.MyUserDetailsService;
import com.nnk.springboot.controller.RuleNameController;
import com.nnk.springboot.service.RuleNameService;

@DisplayName("Controller < RULENAME > GET ADD - UNIT TESTS")
@ExtendWith(SpringExtension.class)
@WebMvcTest(RuleNameController.class)
class RuleNameControllerGetAddTest {


	@MockBean
	AuthTokenFilter authenticationJwtTokenFilter;
	
    @MockBean
    private RuleNameService ruleNameService;

    @MockBean
    private MyUserDetailsService userDetailsService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

	
 
    @BeforeEach
    public void setUp() {

        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }
 
    // ********************************************************************



    @DisplayName(" Url request /ruleName/add - "
    		+ " - Given a RuleName,"
    		+ " when GET /ruleName/add action request,"
    		+ " then returns ruleName ADD page")    
    @Test
    public void testGetRuleNameAdd() throws Exception {
//    	 when(ruleNameService.getAllRuleName()).thenReturn(ruleNameDTOList);
        mockMvc.perform(MockMvcRequestBuilders.get("/ruleName/add"))
                .andExpect(model().attributeExists("ruleNameDTO"))
                .andExpect(model().size(1))
                .andExpect(view().name("ruleName/add"))
                .andExpect(status().isOk());

    }

    // ********************************************************************

}
