package com.nnk.springboot.UnitTests.controller;


import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
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
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nnk.springboot.config.AuthTokenFilter;
import com.nnk.springboot.config.MyUserDetailsService;
import com.nnk.springboot.controller.RuleNameController;
import com.nnk.springboot.dto.RuleNameDTO;
import com.nnk.springboot.service.RuleNameService;

@DisplayName("Controller < RULENAME > - UPDATE UNIT TESTS")
@ExtendWith(SpringExtension.class)
@WebMvcTest(RuleNameController.class)
class RuleNameControllerPostUpdateTest {


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


    @DisplayName(" Url request /ruleName/update/{id} - VALID"
    		+ " - Given a RuleName,"
    		+ " when POST /ruleName/update/{id} action request,"
    		+ " then returns redirect /ruleName/update/{id} page")    
    @Test
    public void testPostRuleNameValidate() throws Exception {

    	when(ruleNameService
    			.updateRuleName(anyInt(), any(RuleNameDTO.class)))
    	.thenReturn(testRuleNameDTO1);
        
        mockMvc.perform(MockMvcRequestBuilders.post("/ruleName/update/1")
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

        verify(ruleNameService, times(1)).updateRuleName(anyInt(), any(RuleNameDTO.class));
    }

    // ********************************************************************


    @DisplayName(" Url request /ruleName/update/{id} - Id Negative "
    		+ " - Given a RuleName, Id Negative"
    		+ " when POST /ruleName/update/{id} action request,"
    		+ " then returns error & redirect /ruleName/update page")    
    @Test
    public void testPostRuleNameValidateIdNegative() throws Exception {

    	when(ruleNameService
    			.updateRuleName(anyInt(), any(RuleNameDTO.class)))
     	.thenReturn(testRuleNameDTO1);
        
        mockMvc.perform(MockMvcRequestBuilders.post("/ruleName/update/1")
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

        verify(ruleNameService, times(1)).updateRuleName(anyInt(), any(RuleNameDTO.class));
    }

    // ********************************************************************


    @DisplayName(" Url request /ruleName/update/{id} - EmptyName "
    		+ " - Given a RuleName - EmptyName,"
    		+ " when POST /ruleName/update/{id} action request,"
    		+ " then returns error & redirect /ruleName/update page")    
    @Test
    public void testPostRuleNameValidateEmptyName() throws Exception {
    	when(ruleNameService.getAllRuleName()).thenReturn(ruleNameDTOList);

    	when(ruleNameService
    			.updateRuleName(anyInt(), any(RuleNameDTO.class)))
     	.thenReturn(testRuleNameDTO1);
        
        
    	MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/ruleName/update/1")
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

        verify(ruleNameService, times(0)).getAllRuleName();
        verify(ruleNameService, times(0)).updateRuleName(anyInt(), any(RuleNameDTO.class));

        String content = result.getResponse().getContentAsString();
        
        assertThat(content).contains("Name is mandatory");
        assertThat(content).contains("Should be alphanumeric and minimum more than 2 characters");
    }

    // ********************************************************************


    @DisplayName(" Url request /ruleName/update/{id} - Name With Symbols "
    		+ " - Given a RuleName - Name With Symols,"
    		+ " when POST /ruleName/update/{id} action request,"
    		+ " then returns error & redirect /ruleName/update page")    
    @Test
    public void testPostRuleNameValidateNameWithSymbols() throws Exception {
    	when(ruleNameService.getAllRuleName()).thenReturn(ruleNameDTOList);

    	when(ruleNameService
    			.updateRuleName(anyInt(), any(RuleNameDTO.class)))
     	.thenReturn(testRuleNameDTO1);
        
        
    	MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/ruleName/update/1")
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

        verify(ruleNameService, times(0)).getAllRuleName();
        verify(ruleNameService, times(0)).updateRuleName(anyInt(), any(RuleNameDTO.class));

        String content = result.getResponse().getContentAsString();
        
        assertThat(content).contains("Should be alphanumeric and minimum more than 2 characters");
    }

    // ********************************************************************




    @DisplayName(" Url request /ruleName/update/{id} - Name length > 125 "
    		+ " - Given a RuleName - Name length > 125,"
    		+ " when POST /ruleName/update/{id} action request,"
    		+ " then returns error & redirect /ruleName/update page")    
    @Test
    public void testPostRuleNameValidateNameWith125MoreCharecters() throws Exception {
    	when(ruleNameService.getAllRuleName()).thenReturn(ruleNameDTOList);

    	when(ruleNameService
    			.updateRuleName(anyInt(), any(RuleNameDTO.class)))
     	.thenReturn(testRuleNameDTO1);
        
        
    	MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/ruleName/update/1")
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

        verify(ruleNameService, times(0)).getAllRuleName();
        verify(ruleNameService, times(0)).updateRuleName(anyInt(), any(RuleNameDTO.class));

        String content = result.getResponse().getContentAsString();
        
        assertThat(content).contains("The maximum length for name can be 125 characters");
    }

    // ********************************************************************

    @DisplayName(" Url request /ruleName/update/{id} - Empty Description "
    		+ " - Given a RuleName - Empty Description,"
    		+ " when POST /ruleName/update/{id} action request,"
    		+ " then returns error & redirect /ruleName/update page")    
    @Test
    public void testPostRuleNameValidateEmptyDescription() throws Exception {
    	when(ruleNameService.getAllRuleName()).thenReturn(ruleNameDTOList);

    	when(ruleNameService
    			.updateRuleName(anyInt(), any(RuleNameDTO.class)))
     	.thenReturn(testRuleNameDTO1);
        
        
    	MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/ruleName/update/1")
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

        verify(ruleNameService, times(0)).getAllRuleName();
        verify(ruleNameService, times(0)).updateRuleName(anyInt(), any(RuleNameDTO.class));

        String content = result.getResponse().getContentAsString();
        
        assertThat(content).contains("Description is mandatory");
        assertThat(content).contains("Should be alphanumeric and minimum more than 2 characters");
    }

    // ********************************************************************



    @DisplayName(" Url request /ruleName/update/{id} - Description With Symbols "
    		+ " - Given a RuleName - Description With Symols,"
    		+ " when POST /ruleName/update/{id} action request,"
    		+ " then returns error & redirect /ruleName/update page")    
    @Test
    public void testPostRuleNameValidateDescriptionWithSymbols() throws Exception {
    	when(ruleNameService.getAllRuleName()).thenReturn(ruleNameDTOList);

    	when(ruleNameService
    			.updateRuleName(anyInt(), any(RuleNameDTO.class)))
     	.thenReturn(testRuleNameDTO1);
        
        
    	MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/ruleName/update/1")
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

        verify(ruleNameService, times(0)).getAllRuleName();
        verify(ruleNameService, times(0)).updateRuleName(anyInt(), any(RuleNameDTO.class));

        String content = result.getResponse().getContentAsString();
        
        assertThat(content).contains("Should be alphanumeric and minimum more than 2 characters");
    }

    // ********************************************************************



    @DisplayName(" Url request /ruleName/update/{id} - Description length > 125 "
    		+ " - Given a RuleName - Description length > 125,"
    		+ " when POST /ruleName/update/{id} action request,"
    		+ " then returns error & redirect /ruleName/update page")    
    @Test
    public void testPostRuleNameValidateDescriptionWith125MoreCharecters() throws Exception {
    	when(ruleNameService.getAllRuleName()).thenReturn(ruleNameDTOList);

    	when(ruleNameService
    			.updateRuleName(anyInt(), any(RuleNameDTO.class)))
     	.thenReturn(testRuleNameDTO1);
        
        
    	MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/ruleName/update/1")
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

        verify(ruleNameService, times(0)).getAllRuleName();
        verify(ruleNameService, times(0)).updateRuleName(anyInt(), any(RuleNameDTO.class));

        String content = result.getResponse().getContentAsString();
        
        assertThat(content).contains("The maximum length for description can be 125 characters");
    }

    // ********************************************************************


    @DisplayName(" Url request /ruleName/update/{id} - Json length > 125 "
    		+ " - Given a RuleName - Json length > 125,"
    		+ " when POST /ruleName/update/{id} action request,"
    		+ " then returns error & redirect /ruleName/update page")    
    @Test
    public void testPostRuleNameValidateJsonWith125MoreCharacters() throws Exception {
    	when(ruleNameService.getAllRuleName()).thenReturn(ruleNameDTOList);

    	when(ruleNameService
    			.updateRuleName(anyInt(), any(RuleNameDTO.class)))
     	.thenReturn(testRuleNameDTO1);
        
        
    	MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/ruleName/update/1")
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

        verify(ruleNameService, times(0)).getAllRuleName();
        verify(ruleNameService, times(0)).updateRuleName(anyInt(), any(RuleNameDTO.class));

        String content = result.getResponse().getContentAsString();
        
        assertThat(content).contains("The maximum length for json can be 125 characters");
    }

    // ********************************************************************


    @DisplayName(" Url request /ruleName/update/{id} - Template length > 512 "
    		+ " - Given a RuleName - Template length > 512,"
    		+ " when POST /ruleName/update/{id} action request,"
    		+ " then returns error & redirect /ruleName/update page")    
    @Test
    public void testPostRuleNameValidateTemplateWith512MoreCharacters() throws Exception {
    	when(ruleNameService.getAllRuleName()).thenReturn(ruleNameDTOList);

    	when(ruleNameService
    			.updateRuleName(anyInt(), any(RuleNameDTO.class)))
     	.thenReturn(testRuleNameDTO1);
        
        
    	MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/ruleName/update/1")
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

        verify(ruleNameService, times(0)).getAllRuleName();
        verify(ruleNameService, times(0)).updateRuleName(anyInt(), any(RuleNameDTO.class));

        String content = result.getResponse().getContentAsString();
        
        assertThat(content).contains("The maximum length for template can be 512 characters");
    }

    // ********************************************************************

    @DisplayName(" Url request /ruleName/update/{id} - SqlStr length > 125 "
    		+ " - Given a RuleName - SqlStr length > 125,"
    		+ " when POST /ruleName/update/{id} action request,"
    		+ " then returns error & redirect /ruleName/update page")    
    @Test
    public void testPostRuleNameValidateSqlStrWith125MoreCharacters() throws Exception {
    	when(ruleNameService.getAllRuleName()).thenReturn(ruleNameDTOList);

    	when(ruleNameService
    			.updateRuleName(anyInt(), any(RuleNameDTO.class)))
     	.thenReturn(testRuleNameDTO1);
        
        
    	MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/ruleName/update/1")
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

        verify(ruleNameService, times(0)).getAllRuleName();
        verify(ruleNameService, times(0)).updateRuleName(anyInt(), any(RuleNameDTO.class));

        String content = result.getResponse().getContentAsString();
        
        assertThat(content).contains("The maximum length for sqlStr can be 125 characters");
    }

    // ********************************************************************

    @DisplayName(" Url request /ruleName/update/{id} - SqlPart length > 125 "
    		+ " - Given a RuleName - SqlPart length > 125,"
    		+ " when POST /ruleName/update/{id} action request,"
    		+ " then returns error & redirect /ruleName/update page")    
    @Test
    public void testPostRuleNameValidateSqlPartWith125MoreCharacters() throws Exception {
    	when(ruleNameService.getAllRuleName()).thenReturn(ruleNameDTOList);

    	when(ruleNameService
    			.updateRuleName(anyInt(), any(RuleNameDTO.class)))
     	.thenReturn(testRuleNameDTO1);
        
        
    	MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/ruleName/update/1")
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

        verify(ruleNameService, times(0)).getAllRuleName();
        verify(ruleNameService, times(0)).updateRuleName(anyInt(), any(RuleNameDTO.class));

        String content = result.getResponse().getContentAsString();
        
        assertThat(content).contains("The maximum length for sqlPart can be 125 characters");
    }

    // ********************************************************************

}
