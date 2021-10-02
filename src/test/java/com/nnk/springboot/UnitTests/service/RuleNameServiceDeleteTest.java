package com.nnk.springboot.UnitTests.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.dto.RuleNameDTO;
import com.nnk.springboot.exception.DataNotFoundException;
import com.nnk.springboot.repository.RuleNameRepository;
import com.nnk.springboot.service.RuleNameService;
import com.nnk.springboot.util.RuleNameMapper;

@DisplayName("RuleName Service DELETE - UNIT TESTS")
@ExtendWith(MockitoExtension.class)
class RuleNameServiceDeleteTest {

    @InjectMocks
    private RuleNameService ruleNameService;

    @Mock
    private RuleNameRepository ruleNameRepository;
    
    @Mock
    private RuleNameMapper ruleNameMapper;

    @Mock
    private ObjectMapper objectMapper;
	
    private static RuleNameDTO testRuleNameDTO1;
    
    private static RuleName testRuleName1;
    
    
    @BeforeEach
    public void setUp() {
        objectMapper = new ObjectMapper();
        testRuleNameDTO1 = RuleNameDTO.builder()
        		.name("RuleName1")
        		.description("Description1")
        		.json("JSON1")
        		.template("template1")
        		.sqlStr("SqlStr1")
        		.sqlPart("SqlPart1")
        		.build();
        
       
        testRuleName1 = RuleName.builder()
        		.name("RuleName1")
        		.description("Description1")
        		.json("JSON1")
        		.template("template1")
        		.sqlStr("SqlStr1")
        		.sqlPart("SqlPart1")
        		.build();
        
       
    }

	// *******************************************************************	
    
	@DisplayName("Delete RuleName - "
			+ "GIVEN a RULENAME  "
			+ "WHEN Requested DELETE RuleName"
			+ "THEN returns expected ruleName deleted")	    
    @Test
    public void testDeleteRuleName() throws Exception {
	
        // GIVEN
		 when(ruleNameRepository
				 .findById(anyInt()))
		 .thenReturn(java.util.Optional.ofNullable(testRuleName1));
		 
        // WHEN
		 ruleNameService.deleteRuleName(1);
        
        // THEN
	       InOrder inOrder = inOrder(ruleNameRepository);
	       inOrder.verify(ruleNameRepository).findById(anyInt());
	       inOrder.verify(ruleNameRepository).deleteById(anyInt());
	       
	       verify(ruleNameRepository, times(1)).findById(anyInt());
	       verify(ruleNameRepository, times(1)).deleteById(anyInt());
    
	}


	// *******************************************************************	
	
    @DisplayName("Check <Exception>"
		+ "GIVEN a RuleName not exist "
		+ "WHEN Requested DELETE RuleName "
		+ "THEN throws Exception")	    
	@Test
	public void testDeleteRuleNameNotExists() throws Exception {

    	when(ruleNameRepository
    			.findById(anyInt()))
    	.thenReturn(java.util.Optional.empty());
    
    	// WHEN // THEN
    	assertThrows(DataNotFoundException.class, ()
        		-> ruleNameService.deleteRuleName(anyInt()));
	} 

	// *******************************************************************	
	

}
