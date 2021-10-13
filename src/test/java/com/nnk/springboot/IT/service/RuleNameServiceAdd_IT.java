package com.nnk.springboot.IT.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nnk.springboot.dto.RuleNameDTO;
import com.nnk.springboot.service.RuleNameService;

@DisplayName("INTEGRATION TESTS - Service ==> RuleName Add")
@SpringBootTest
@ActiveProfiles("test")
class RuleNameServiceAdd_IT {


	@Autowired
    private RuleNameService ruleNameService;

    private ObjectMapper objectMapper;
	
    private static RuleNameDTO ruleNameToAddDTO;

    

  
    // ***********************************************************************************

    	
        @BeforeEach
        public void init() {
        	
	        
	        ruleNameToAddDTO = RuleNameDTO.builder()
	        		.id(6)
	        		.name("RuleName1")
	        		.description("Description1")
	        		.json("JSON1")
	        		.template("template1")
	        		.sqlStr("SqlStr1")
	        		.sqlPart("SqlPart1")
	        		.build();

        }

	// *******************************************************************	


    
    @DisplayName("Check <NotNull>"
		+ " - Given a new RuleName,"
		+ " when ADD RULENAME action request,"
		+ " then RULENAME should not be null")	    
    @Test
    public void testAddNewRuleNameNotNullCheck() {
		

        RuleNameDTO ruleNameSaved = ruleNameService
        		.addRuleName(ruleNameToAddDTO);

        assertNotNull(ruleNameSaved);
    }

	// *******************************************************************			
    

    @Test
    @DisplayName("Check <Validate> match of both same record instance "
    		+ " - Given a new RuleName,"
    		+ " when ADD RULENAME action request,"
    		+ " then RULENAME added should be added and same as test record")
    public void testAddNewRuleNameReturnResultMatch() {
   			

   	        RuleNameDTO ruleNameSaved = ruleNameService.addRuleName(ruleNameToAddDTO);

   	        assertEquals(ruleNameToAddDTO.toString(), ruleNameSaved.toString());
   	        assertThat(ruleNameSaved).usingRecursiveComparison().isEqualTo(ruleNameToAddDTO);
   	    }

    
   	// *******************************************************************	


}