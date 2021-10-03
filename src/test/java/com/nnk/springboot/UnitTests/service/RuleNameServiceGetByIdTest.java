package com.nnk.springboot.UnitTests.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
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

@DisplayName("Service ==> RuleName GET BY ID - UNIT TESTS")
@ExtendWith(MockitoExtension.class)
class RuleNameServiceGetByIdTest {

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
  
    // ***********************************************************************************

    @DisplayName("Test GET RULENAME BY IDENTITY")
    @Nested
    class TestGetRuleNameById  {  
    	
        @BeforeEach
        public void init() {
        	
            when(ruleNameRepository
            		.findById(anyInt()))
            .thenReturn(java.util.Optional.ofNullable(testRuleName1));
            
            when(ruleNameMapper
            		.toRuleNameDTO(any(RuleName.class)))
            .thenReturn(testRuleNameDTO1);  

        }

	// *******************************************************************	
    
    @DisplayName("Check <Execution Order>"
    		+ " - Given a RuleName,"
    		+ " when GET RULENAME BY ID action request,"
    		+ " then all steps are executed in correct order and number of expected times")    
    @Test
    public void testGetRuleNameByIdExecutionOrderCheck() throws Exception {
		

    	ruleNameService.getRuleNameById(1);

        InOrder inOrder = inOrder(ruleNameRepository, ruleNameMapper);
        inOrder.verify(ruleNameRepository).findById(anyInt());
        inOrder.verify(ruleNameMapper).toRuleNameDTO(any(RuleName.class));
        
        verify(ruleNameRepository, times(1)).findById(anyInt());
        verify(ruleNameMapper, times(1)).toRuleNameDTO(any(RuleName.class));
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

    	assertEquals(result, testRuleNameDTO1);
	    assertThat(result).usingRecursiveComparison().isEqualTo(testRuleNameDTO1);
	    assertEquals("RuleName1", result.getName());
	    assertEquals("Description1", result.getDescription());
    }  	   
  }

    
	
   	// *******************************************************************	
   	
       @DisplayName("ERROR GET EXISTING RULENAME by ID for non existing RULENAME data"
       		+ " - Given a non existing RULENAME,"
       		+ " when GET RULENAME By ID action request,"
       		+ " then RULENAME entry should respond"
       		+ " with Data Not Found Exception")
   	@Test
   	public void testGetRuleNameByIdNonExistingRuleNameData() throws Exception {

       	when(ruleNameRepository
       			.findById(anyInt()))
       	.thenReturn(java.util.Optional.empty());
       
       	// WHEN // THEN
       	assertThrows(DataNotFoundException.class, ()
           		-> ruleNameService.getRuleNameById(1));
   	}
       
   	// *******************************************************************	  

}