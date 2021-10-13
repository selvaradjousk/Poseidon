package com.nnk.springboot.IT.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nnk.springboot.dto.RuleNameDTO;
import com.nnk.springboot.exception.DataNotFoundException;
import com.nnk.springboot.service.RuleNameService;

@DisplayName("INTEGRATION TESTS - Service ==> RuleName Update")
@SpringBootTest
@ActiveProfiles("test")
class RuleNameServiceUpdate_IT {


	@Autowired
    private RuleNameService ruleNameService;

    private ObjectMapper objectMapper;
	
    private static RuleNameDTO ruleNameUpdatedDTO;

        @BeforeEach
        public void init() {
 	        
		       ruleNameUpdatedDTO = RuleNameDTO.builder()
	    		   	.id(2)
	        		.name("RuleName1")
	        		.description("Description update")
	        		.json("JSON1")
	        		.template("template1")
	        		.sqlStr("SqlStr1")
	        		.sqlPart("SqlPart1")
	        		.build();


        }



	// *******************************************************************	
    

    
    
    @DisplayName("Check <NotNull>"
		+ " - Given a new RuleName,"
		+ " when UPDATE RULENAME action request,"
		+ " then RULENAME should not be null")	    
    @Test
    public void testUpdateExistingRuleNameNotNullCheck() {
		

        RuleNameDTO ruleNameSaved = ruleNameService.updateRuleName(2, ruleNameUpdatedDTO);

        assertNotNull(ruleNameSaved);
    }

	// *******************************************************************			
    


    @Test
    @DisplayName("Check <Validate> match of both same record instance "
    		+ " - Given a new RuleName,"
    		+ " when UPDATE RULENAME action request,"
    		+ " then RULENAME updateed should be updateed and same as test record")
    public void testUpdateExistingRuleNameReturnResultMatch() {
   			

   	        RuleNameDTO ruleNameSaved = ruleNameService
   	        		.updateRuleName(2, ruleNameUpdatedDTO);

   	        assertEquals(ruleNameUpdatedDTO.toString(), ruleNameSaved.toString());
   	        assertThat(ruleNameSaved).usingRecursiveComparison().isEqualTo(ruleNameUpdatedDTO);
   	    }

   	// *******************************************************************	


	
    @DisplayName("ERROR UPDATE RULENAME for non existing RULENAME data"
    		+ " - Given a non existing RULENAME,"
    		+ " when UPDATE RULENAMEaction request,"
    		+ " then RULENAME entry should respond"
    		+ " with Data Not Found Exception")
	@Test
	public void testGetRuleNameByIdNonExistingRuleNameData() throws Exception {

    
    	// WHEN // THEN
    	assertThrows(DataNotFoundException.class, ()
        		-> ruleNameService.updateRuleName(100, ruleNameUpdatedDTO));
	}
    
	// *******************************************************************	 
	   
}