package com.nnk.springboot.UnitTests.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

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

@DisplayName("RuleName Service GetRuleName List - UNIT TESTS")
@ExtendWith(MockitoExtension.class)
class RuleNameServiceGetListTest {

    @InjectMocks
    private RuleNameService RuleNameService;

    @Mock
    private RuleNameRepository RuleNameRepository;
    
    @Mock
    private RuleNameMapper RuleNameMapper;
    
    private ObjectMapper objectMapper;
	
    private static RuleNameDTO testRuleNameDTO1, testRuleNameDTO2;
    
    private static RuleName testRuleName1, testRuleName2;
    
    private static List<RuleNameDTO> RuleNameDTOList;
    
    private static List<RuleName> RuleNameList;

    
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
        
        testRuleNameDTO2 = RuleNameDTO.builder()
        		.name("RuleName2")
        		.description("Description2")
        		.json("JSON2")
        		.template("template2")
        		.sqlStr("SqlStr2")
        		.sqlPart("SqlPart2")
        		.build();
        
        RuleNameDTOList = Arrays.asList(testRuleNameDTO1, testRuleNameDTO2);   
        
        testRuleName1 = RuleName.builder()
        		.name("RuleName1")
        		.description("Description1")
        		.json("JSON1")
        		.template("template1")
        		.sqlStr("SqlStr1")
        		.sqlPart("SqlPart1")
        		.build();
        
        testRuleName2 = RuleName.builder()
        		.name("RuleName2")
        		.description("Description2")
        		.json("JSON2")
        		.template("template2")
        		.sqlStr("SqlStr2")
        		.sqlPart("SqlPart2")
        		.build();
        
        RuleNameList = Arrays.asList(testRuleName1, testRuleName2);   
        
    }
    


   	// *******************************************************************	
    @DisplayName("Test List RuleNames")
    @Nested
    class TestGetListRuleName {  
    	
        @BeforeEach
        public void init() {
        	
            when(RuleNameRepository
            		.findAll())
            .thenReturn(RuleNameList);
            
            when(RuleNameMapper
            		.toRuleNameDTO(testRuleName1))
            .thenReturn(testRuleNameDTO1);
            
            when(RuleNameMapper
            		.toRuleNameDTO(testRuleName2))
            .thenReturn(testRuleNameDTO2);
            
        }
        
        
    	// *******************************************************************	
        
        @DisplayName("Check <Execution Order>"
        		+ " - Given a RuleName List,"
        		+ " when Get RuleName List action request,"
        		+ " then all steps are executed in correct order and number of expected times")    
        @Test
        public void testGetRuleNamesListExecutionOrderCheck() throws Exception {
   	
           // GIVEN

           // WHEN
           RuleNameService.getAllRuleName();
           
           // THEN
           InOrder inOrder = inOrder(RuleNameRepository, RuleNameMapper);
           inOrder.verify(RuleNameRepository).findAll();
           inOrder.verify(RuleNameMapper).toRuleNameDTO(testRuleName1);
           inOrder.verify(RuleNameMapper).toRuleNameDTO(testRuleName2);
           
           verify(RuleNameRepository, times(1)).findAll();
           verify(RuleNameMapper, times(1)).toRuleNameDTO(testRuleName1);
           verify(RuleNameMapper, times(1)).toRuleNameDTO(testRuleName2);
       
        }
   	
        
      	// *******************************************************************

        
        @DisplayName("Check Check <NotNull>"
        		+ " - Given a RuleName List,"
        		+ " when Get RuleName List action request,"
        		+ " then returns RuleNameslist not null")    
        @Test
        public void testGetRuleNamesListNotNullCheck() throws Exception {
   	
           // GIVEN

           // WHEN
           List<RuleNameDTO> result = RuleNameService
          		 .getAllRuleName();
           
           // THEN
           assertNotNull(result);
       
        }
        

      // *******************************************************************               

    
    
    
    } 
   
}
