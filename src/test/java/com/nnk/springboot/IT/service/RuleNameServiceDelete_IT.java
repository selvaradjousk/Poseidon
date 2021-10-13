package com.nnk.springboot.IT.service;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.nnk.springboot.exception.DataNotFoundException;
import com.nnk.springboot.service.RuleNameService;

@DisplayName("INTEGRATION TESTS - Service ==> RuleName DELETE")
@SpringBootTest
@ActiveProfiles("test")
class RuleNameServiceDelete_IT {

	@Autowired
    private RuleNameService ruleNameService;


	// *******************************************************************	
    
	@DisplayName("Delete RuleName - "
			+ "GIVEN a RULENAME  "
			+ "WHEN Requested DELETE RuleName"
			+ "THEN returns expected ruleName deleted")	    
    @Test
    public void testDeleteRuleName() throws Exception {
	
        // GIVEN
		 
        // WHEN
		 ruleNameService.deleteRuleName(3);
        
	    // THEN
	    assertThrows(DataNotFoundException.class, ()
	        		-> ruleNameService.deleteRuleName(3));
    
	}


	// *******************************************************************	
	
    @DisplayName("Check <Exception>"
		+ "GIVEN a RuleName not exist "
		+ "WHEN Requested DELETE RuleName "
		+ "THEN throws Exception")	    
	@Test
	public void testDeleteRuleNameNotExists() throws Exception {

    
    	// WHEN // THEN
    	assertThrows(DataNotFoundException.class, ()
        		-> ruleNameService.deleteRuleName(100));
	} 

	// *******************************************************************	
	

}
