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

@DisplayName("INTEGRATION TESTS - Service ==> RuleName GET BY ID")
@SpringBootTest
@ActiveProfiles("test")
class RuleNameServiceGetById_IT {

	@Autowired
    private RuleNameService ruleNameService;

    private ObjectMapper objectMapper;
	
    private static RuleNameDTO testRuleNameDTO1;

    
    @BeforeEach
    public void setUp() {
        objectMapper = new ObjectMapper();
        testRuleNameDTO1 = RuleNameDTO.builder()
        		.id(1)
        		.name("name1")
        		.description("description1")
        		.json("json1")
        		.template("template1")
        		.sqlStr("sql_str1")
        		.sqlPart("sql_part1")
        		.build();
        
      
    }
  

	// *******************************************************************	
    
    @DisplayName("Check <NotNull>"
    		+ " - Given a existing RuleName,"
    		+ " when GET RULENAME By ID action request,"
    		+ " then RULENAME should not be null")	    
	    @Test
	    public void testRuleNameByIdNotNullCheck() {
			

    		RuleNameDTO result = ruleNameService
    				.getRuleNameById(1);

	        assertNotNull(result);
	    }

	// ******************************************************************		

    
    @Test
    @DisplayName("Check <Validate> match of both same record instance "
    		+ " - Given a existing RuleName,"
    		+ " when GET RULENAME By ID action request,"
    		+ " then RULENAME ID same as test record")
    public void testGetRuleNameByIdReturnResultMatch() {
			
    	RuleNameDTO result = ruleNameService
    			.getRuleNameById(1);

    	assertEquals(testRuleNameDTO1.toString(), result.toString());
	    assertThat(result).usingRecursiveComparison().isEqualTo(testRuleNameDTO1);
	    assertEquals("name1", result.getName());
	    assertEquals("description1", result.getDescription());
    }  	   

    
	
   	// *******************************************************************	
   	
       @DisplayName("ERROR GET EXISTING RULENAME by ID for non existing RULENAME data"
       		+ " - Given a non existing RULENAME,"
       		+ " when GET RULENAME By ID action request,"
       		+ " then RULENAME entry should respond"
       		+ " with Data Not Found Exception")
   	@Test
   	public void testGetRuleNameByIdNonExistingRuleNameData() throws Exception {

       
       	// WHEN // THEN
       	assertThrows(DataNotFoundException.class, ()
           		-> ruleNameService.getRuleNameById(100));
   	}
       
   	// *******************************************************************	  

}