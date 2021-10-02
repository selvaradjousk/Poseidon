package com.nnk.springboot.UnitTests.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
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
import com.nnk.springboot.repository.RuleNameRepository;
import com.nnk.springboot.service.RuleNameService;
import com.nnk.springboot.util.RuleNameMapper;

@DisplayName("RuleName Service Add RuleName- UNIT TESTS")
@ExtendWith(MockitoExtension.class)
class RuleNameServiceAddTest {


    @InjectMocks
    private RuleNameService ruleNameService;

    @Mock
    private RuleNameRepository ruleNameRepository;
    
    @Mock
    private RuleNameMapper ruleNameMapper;
	
    @Mock
    private ObjectMapper objectMapper;
	
    private static RuleNameDTO testRuleNameDTO1, ruleNameToAddDTO;
    
    private static RuleName testRuleName1, ruleNameToAdd;
    
    
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

    @DisplayName("Test ADD New RULENAME")
    @Nested
    class TestAddNewRuleName {  
    	
        @BeforeEach
        public void init() {
        	
 	       ruleNameToAdd = RuleName.builder()
 	        		.name("RuleName1")
 	        		.description("Description1")
 	        		.json("JSON1")
 	        		.template("template1")
 	        		.sqlStr("SqlStr1")
 	        		.sqlPart("SqlPart1")
 	        		.build();
	        
	        ruleNameToAddDTO = RuleNameDTO.builder()
	        		.name("RuleName1")
	        		.description("Description1")
	        		.json("JSON1")
	        		.template("template1")
	        		.sqlStr("SqlStr1")
	        		.sqlPart("SqlPart1")
	        		.build();
       
       when(ruleNameMapper
    		   .toRuleName(any(RuleNameDTO.class)))
       .thenReturn(ruleNameToAdd);
       
       when(ruleNameRepository
    		   .save(any(RuleName.class)))
       .thenReturn(testRuleName1);
       
       when(ruleNameMapper
    		   .toRuleNameDTO(any(RuleName.class)))
       .thenReturn(testRuleNameDTO1);

        }

	// *******************************************************************	
    
    @DisplayName("Check <Execution Order>"
    		+ " - Given a new RuleName,"
    		+ " when ADD RULENAME action request,"
    		+ " then all steps are executed in correct order and number of expected times")    
    @Test
    public void testAddNewRuleNameExecutionOrderCheck() throws Exception {
		

    	ruleNameService.addRuleName(ruleNameToAddDTO);

        InOrder inOrder = inOrder(ruleNameMapper, ruleNameRepository, ruleNameMapper);
        inOrder.verify(ruleNameMapper).toRuleName(any(RuleNameDTO.class));
        inOrder.verify(ruleNameRepository).save(any(RuleName.class));
        inOrder.verify(ruleNameMapper).toRuleNameDTO(any(RuleName.class));
        
        verify(ruleNameMapper, times(1)).toRuleName(any(RuleNameDTO.class));
        verify(ruleNameRepository, times(1)).save(any(RuleName.class));
        verify(ruleNameMapper, times(1)).toRuleNameDTO(any(RuleName.class));
    }
	

	// *******************************************************************	


    
    }
    
 


}