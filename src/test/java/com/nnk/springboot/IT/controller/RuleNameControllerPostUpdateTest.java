package com.nnk.springboot.IT.controller;


import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
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
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nnk.springboot.dto.RuleNameDTO;

@DisplayName("INTEGRATION TESTS - Controller < RULENAME > - UPDATE")
@AutoConfigureMockMvc
@SpringBootTest
@ActiveProfiles("test")
class RuleNameControllerPostUpdateTest {

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper;
	
    private static RuleNameDTO testRuleNameDTO1;


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


    }
    
  	// ********************************************************************


    @DisplayName(" Url request POST /ruleName/update/{id} - Without Authentication"
    		+ " - Given a RuleName,"
    		+ " when POST /ruleName/update/{id} action request,"
    		+ " then returns Error Authentication required") 
    @Test
    public void testPostRuleNameValidateWithoutAuthentication() throws Exception {

        
        mockMvc.perform(post("/ruleName/update/2")
	        .sessionAttr("ruleNameDTO", testRuleNameDTO1))
	        .andExpect(status().is(401))
	        .andDo(MockMvcResultHandlers.print())
	        .andExpect(status().isUnauthorized())
//	        .andExpect(status().reason(containsString("Full authentication is required to access this resource")))
	        .andExpect(unauthenticated());

    }

    // ********************************************************************

    @WithMockUser(username = "admin", authorities = { "ADMIN", "USER"})
    @DisplayName(" Url request POST /ruleName/update/{id} - With Authentication"
    		+ " - Given a RuleName,"
    		+ " when POST /ruleName/update/{id} action request,"
    		+ " then returns redirect /ruleName/update/{id} page")    
    @Test
    public void testPostRuleNameValidateWithAuthentication() throws Exception {

        
        mockMvc.perform(post("/ruleName/update/2")
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

    }

    // ********************************************************************

    @WithMockUser(username = "admin", authorities = { "ADMIN", "USER"})
    @DisplayName(" Url request POST /ruleName/update/{id} - Id Negative "
    		+ " - Given a RuleName, Id Negative"
    		+ " when POST /ruleName/update/{id} action request,"
    		+ " then returns error & redirect /ruleName/update page")    
    @Test
    public void testPostRuleNameValidateIdNegative() throws Exception {

        
        mockMvc.perform(post("/ruleName/update/2")
        .sessionAttr("ruleNameDTO", testRuleNameDTO1)
		        .param("id", "-1")
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

    }

    // ********************************************************************

    @WithMockUser(username = "admin", authorities = { "ADMIN", "USER"})
    @DisplayName(" Url request POST /ruleName/update/{id} - EmptyName "
    		+ " - Given a RuleName - EmptyName,"
    		+ " when POST /ruleName/update/{id} action request,"
    		+ " then returns error & redirect /ruleName/update page")    
    @Test
    public void testPostRuleNameValidateEmptyName() throws Exception {

        
    	MvcResult result = mockMvc.perform(post("/ruleName/update/2")
		        .sessionAttr("ruleNameDTO", testRuleNameDTO1)
		        .param("id", testRuleNameDTO1.getId().toString())
		        .param("name", "")
		        .param("description", testRuleNameDTO1.getDescription())
		        .param("json", testRuleNameDTO1.getJson())
		        .param("template", testRuleNameDTO1.getTemplate())
		        .param("sqlStr", testRuleNameDTO1.getSqlStr())
		        .param("sqlPart", testRuleNameDTO1.getSqlPart()))
		        .andExpect(model().hasErrors())
		        .andExpect(model().size(2))
		        .andExpect(model().attributeExists("ruleNameDTO"))
		        .andExpect(view().name("ruleName/update"))
		        .andExpect(status().is(200))
		        .andReturn();

        String content = result.getResponse().getContentAsString();
        
        assertThat(content).contains("Name is mandatory");
        assertThat(content).contains("Should be alphanumeric and minimum more than 2 characters");
    }

    // ********************************************************************

    @WithMockUser(username = "admin", authorities = { "ADMIN", "USER"})
    @DisplayName(" Url request /ruleName/update/{id} - Name With Symbols "
    		+ " - Given a RuleName - Name With Symols,"
    		+ " when POST /ruleName/update/{id} action request,"
    		+ " then returns error & redirect /ruleName/update page")    
    @Test
    public void testPostRuleNameValidateNameWithSymbols() throws Exception {

    	MvcResult result = mockMvc.perform(post("/ruleName/update/2")
		        .sessionAttr("ruleNameDTO", testRuleNameDTO1)
		        .param("id", testRuleNameDTO1.getId().toString())
		        .param("name", "&&&aaa")
		        .param("description", testRuleNameDTO1.getDescription())
		        .param("json", testRuleNameDTO1.getJson())
		        .param("template", testRuleNameDTO1.getTemplate())
		        .param("sqlStr", testRuleNameDTO1.getSqlStr())
		        .param("sqlPart", testRuleNameDTO1.getSqlPart()))
		        .andExpect(model().hasErrors())
		        .andExpect(model().size(2))
		        .andExpect(model().attributeExists("ruleNameDTO"))
		        .andExpect(view().name("ruleName/update"))
		        .andExpect(status().is(200))
		        .andReturn();

        String content = result.getResponse().getContentAsString();
        
        assertThat(content).contains("Should be alphanumeric and minimum more than 2 characters");
    }

    // ********************************************************************



    @WithMockUser(username = "admin", authorities = { "ADMIN", "USER"})
    @DisplayName(" Url request POST /ruleName/update/{id} - Name length > 125 "
    		+ " - Given a RuleName - Name length > 125,"
    		+ " when POST /ruleName/update/{id} action request,"
    		+ " then returns error & redirect /ruleName/update page")    
    @Test
    public void testPostRuleNameValidateNameWith125MoreCharecters() throws Exception {
        
    	MvcResult result = mockMvc.perform(post("/ruleName/update/2")
		        .sessionAttr("ruleNameDTO", testRuleNameDTO1)
		        .param("id", testRuleNameDTO1.getId().toString())
		        .param("name", "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
		        		+ "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
		        		+ "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa")
		        .param("description", testRuleNameDTO1.getDescription())
		        .param("json", testRuleNameDTO1.getJson())
		        .param("template", testRuleNameDTO1.getTemplate())
		        .param("sqlStr", testRuleNameDTO1.getSqlStr())
		        .param("sqlPart", testRuleNameDTO1.getSqlPart()))
		        .andExpect(model().hasErrors())
		        .andExpect(model().size(2))
		        .andExpect(model().attributeExists("ruleNameDTO"))
		        .andExpect(view().name("ruleName/update"))
		        .andExpect(status().is(200))
		        .andReturn();

        String content = result.getResponse().getContentAsString();
        
        assertThat(content).contains("The maximum length for name can be 125 characters");
    }

    // ********************************************************************
    @WithMockUser(username = "admin", authorities = { "ADMIN", "USER"})
    @DisplayName(" Url request POST /ruleName/update/{id} - Empty Description "
    		+ " - Given a RuleName - Empty Description,"
    		+ " when POST /ruleName/update/{id} action request,"
    		+ " then returns error & redirect /ruleName/update page")    
    @Test
    public void testPostRuleNameValidateEmptyDescription() throws Exception {
        
    	MvcResult result = mockMvc.perform(post("/ruleName/update/2")
		        .sessionAttr("ruleNameDTO", testRuleNameDTO1)
		        .param("id", testRuleNameDTO1.getId().toString())
		        .param("name", testRuleNameDTO1.getName())
		        .param("description", "")
		        .param("json", testRuleNameDTO1.getJson())
		        .param("template", testRuleNameDTO1.getTemplate())
		        .param("sqlStr", testRuleNameDTO1.getSqlStr())
		        .param("sqlPart", testRuleNameDTO1.getSqlPart()))
		        .andExpect(model().hasErrors())
		        .andExpect(model().size(2))
		        .andExpect(model().attributeExists("ruleNameDTO"))
		        .andExpect(view().name("ruleName/update"))
		        .andExpect(status().is(200))
		        .andReturn();

        String content = result.getResponse().getContentAsString();
        
        assertThat(content).contains("Description is mandatory");
        assertThat(content).contains("Should be alphanumeric and minimum more than 2 characters");
    }

    // ********************************************************************


    @WithMockUser(username = "admin", authorities = { "ADMIN", "USER"})
    @DisplayName(" Url request POST /ruleName/update/{id} - Description With Symbols "
    		+ " - Given a RuleName - Description With Symols,"
    		+ " when POST /ruleName/update/{id} action request,"
    		+ " then returns error & redirect /ruleName/update page")    
    @Test
    public void testPostRuleNameValidateDescriptionWithSymbols() throws Exception {

    	MvcResult result = mockMvc.perform(post("/ruleName/update/2")
		        .sessionAttr("ruleNameDTO", testRuleNameDTO1)
		        .param("id", testRuleNameDTO1.getId().toString())
			.param("name", testRuleNameDTO1.getName())
		        .param("description", "&&&aaa")
		        .param("json", testRuleNameDTO1.getJson())
		        .param("template", testRuleNameDTO1.getTemplate())
		        .param("sqlStr", testRuleNameDTO1.getSqlStr())
		        .param("sqlPart", testRuleNameDTO1.getSqlPart()))
		        .andExpect(model().hasErrors())
		        .andExpect(model().size(2))
		        .andExpect(model().attributeExists("ruleNameDTO"))
		        .andExpect(view().name("ruleName/update"))
		        .andExpect(status().is(200))
		        .andReturn();

        String content = result.getResponse().getContentAsString();
        
        assertThat(content).contains("Should be alphanumeric and minimum more than 2 characters");
    }

    // ********************************************************************


    @WithMockUser(username = "admin", authorities = { "ADMIN", "USER"})
    @DisplayName(" Url request POST /ruleName/update/{id} - Description length > 125 "
    		+ " - Given a RuleName - Description length > 125,"
    		+ " when POST /ruleName/update/{id} action request,"
    		+ " then returns error & redirect /ruleName/update page")    
    @Test
    public void testPostRuleNameValidateDescriptionWith125MoreCharecters() throws Exception {

    	MvcResult result = mockMvc.perform(post("/ruleName/update/2")
		        .sessionAttr("ruleNameDTO", testRuleNameDTO1)
		        .param("id", testRuleNameDTO1.getId().toString())
			.param("name", testRuleNameDTO1.getName())
		        .param("description", "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
		        		+ "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
		        		+ "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa")
		        .param("json", testRuleNameDTO1.getJson())
		        .param("template", testRuleNameDTO1.getTemplate())
		        .param("sqlStr", testRuleNameDTO1.getSqlStr())
		        .param("sqlPart", testRuleNameDTO1.getSqlPart()))
		        .andExpect(model().hasErrors())
		        .andExpect(model().size(2))
		        .andExpect(model().attributeExists("ruleNameDTO"))
		        .andExpect(view().name("ruleName/update"))
		        .andExpect(status().is(200))
		        .andReturn();

        String content = result.getResponse().getContentAsString();
        
        assertThat(content).contains("The maximum length for description can be 125 characters");
    }

    // ********************************************************************

    @WithMockUser(username = "admin", authorities = { "ADMIN", "USER"})
    @DisplayName(" Url request POST /ruleName/update/{id} - Json length > 125 "
    		+ " - Given a RuleName - Json length > 125,"
    		+ " when POST /ruleName/update/{id} action request,"
    		+ " then returns error & redirect /ruleName/update page")    
    @Test
    public void testPostRuleNameValidateJsonWith125MoreCharacters() throws Exception {

    	MvcResult result = mockMvc.perform(post("/ruleName/update/2")
		        .sessionAttr("ruleNameDTO", testRuleNameDTO1)
		        .param("id", testRuleNameDTO1.getId().toString())
			.param("name", testRuleNameDTO1.getName())
		        .param("description", testRuleNameDTO1.getDescription())
		        .param("json", "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
		        		+ "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
		        		+ "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa")
		        .param("template", testRuleNameDTO1.getTemplate())
		        .param("sqlStr", testRuleNameDTO1.getSqlStr())
		        .param("sqlPart", testRuleNameDTO1.getSqlPart()))
		        .andExpect(model().hasErrors())
		        .andExpect(model().size(2))
		        .andExpect(model().attributeExists("ruleNameDTO"))
		        .andExpect(view().name("ruleName/update"))
		        .andExpect(status().is(200))
		        .andReturn();

        String content = result.getResponse().getContentAsString();
        
        assertThat(content).contains("The maximum length for json can be 125 characters");
    }

    // ********************************************************************

    @WithMockUser(username = "admin", authorities = { "ADMIN", "USER"})
    @DisplayName(" Url request POST /ruleName/update/{id} - Template length > 512 "
    		+ " - Given a RuleName - Template length > 512,"
    		+ " when POST /ruleName/update/{id} action request,"
    		+ " then returns error & redirect /ruleName/update page")    
    @Test
    public void testPostRuleNameValidateTemplateWith512MoreCharacters() throws Exception {
        
    	MvcResult result = mockMvc.perform(post("/ruleName/update/2")
		        .sessionAttr("ruleNameDTO", testRuleNameDTO1)
		        .param("id", testRuleNameDTO1.getId().toString())
			.param("name", testRuleNameDTO1.getName())
		        .param("description", testRuleNameDTO1.getDescription())
		        .param("json", testRuleNameDTO1.getJson())
		        .param("template", "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
		        		+ "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
		        		+ "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
		        		+ "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
		        		+ "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
		        		+ "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
		        		+ "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
		        		+ "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
		        		+ "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
		        		+ "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
		        		+ "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa")
		        .param("sqlStr", testRuleNameDTO1.getSqlStr())
		        .param("sqlPart", testRuleNameDTO1.getSqlPart()))
		        .andExpect(model().hasErrors())
		        .andExpect(model().size(2))
		        .andExpect(model().attributeExists("ruleNameDTO"))
		        .andExpect(view().name("ruleName/update"))
		        .andExpect(status().is(200))
		        .andReturn();

        String content = result.getResponse().getContentAsString();
        
        assertThat(content).contains("The maximum length for template can be 512 characters");
    }

    // ********************************************************************

    @WithMockUser(username = "admin", authorities = { "ADMIN", "USER"})
    @DisplayName(" Url request POST /ruleName/update/{id} - SqlStr length > 125 "
    		+ " - Given a RuleName - SqlStr length > 125,"
    		+ " when POST /ruleName/update/{id} action request,"
    		+ " then returns error & redirect /ruleName/update page")    
    @Test
    public void testPostRuleNameValidateSqlStrWith125MoreCharacters() throws Exception {
        
    	MvcResult result = mockMvc.perform(post("/ruleName/update/2")
		        .sessionAttr("ruleNameDTO", testRuleNameDTO1)
		        .param("id", testRuleNameDTO1.getId().toString())
			.param("name", testRuleNameDTO1.getName())
		        .param("description", testRuleNameDTO1.getDescription())
		        .param("json", testRuleNameDTO1.getJson())
		        .param("template", testRuleNameDTO1.getTemplate())
		        .param("sqlStr", "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
		        		+ "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
		        		+ "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa")
		        .param("sqlPart", testRuleNameDTO1.getSqlPart()))
		        .andExpect(model().hasErrors())
		        .andExpect(model().size(2))
		        .andExpect(model().attributeExists("ruleNameDTO"))
		        .andExpect(view().name("ruleName/update"))
		        .andExpect(status().is(200))
		        .andReturn();


        String content = result.getResponse().getContentAsString();
        
        assertThat(content).contains("The maximum length for sqlStr can be 125 characters");
    }

    // ********************************************************************

    @WithMockUser(username = "admin", authorities = { "ADMIN", "USER"})
    @DisplayName(" Url request POST /ruleName/update/{id} - SqlPart length > 125 "
    		+ " - Given a RuleName - SqlPart length > 125,"
    		+ " when POST /ruleName/update/{id} action request,"
    		+ " then returns error & redirect /ruleName/update page")    
    @Test
    public void testPostRuleNameValidateSqlPartWith125MoreCharacters() throws Exception {
        
        
    	MvcResult result = mockMvc.perform(post("/ruleName/update/2")
		        .sessionAttr("ruleNameDTO", testRuleNameDTO1)
		        .param("id", testRuleNameDTO1.getId().toString())
			.param("name", testRuleNameDTO1.getName())
		        .param("description", testRuleNameDTO1.getDescription())
		        .param("json", testRuleNameDTO1.getJson())
		        .param("template", testRuleNameDTO1.getTemplate())
		        .param("sqlStr", testRuleNameDTO1.getSqlPart())
		        .param("sqlPart", "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
		        		+ "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
		        		+ "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"))
		        .andExpect(model().hasErrors())
		        .andExpect(model().size(2))
		        .andExpect(model().attributeExists("ruleNameDTO"))
		        .andExpect(view().name("ruleName/update"))
		        .andExpect(status().is(200))
		        .andReturn();

        String content = result.getResponse().getContentAsString();
        
        assertThat(content).contains("The maximum length for sqlPart can be 125 characters");
    }

    // ********************************************************************

}
