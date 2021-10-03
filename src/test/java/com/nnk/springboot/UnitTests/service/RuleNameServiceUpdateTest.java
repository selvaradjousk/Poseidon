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

@DisplayName("Service ==> RuleName Update - UNIT TESTS")
@ExtendWith(MockitoExtension.class)
class RuleNameServiceUpdateTest {


    @InjectMocks
    private RuleNameService ruleNameService;

    @Mock
    private RuleNameRepository ruleNameRepository;
    
    @Mock
    private RuleNameMapper ruleNameMapper;
	
    @Mock
    private ObjectMapper objectMapper;
	
    private static RuleNameDTO testRuleNameDTO1, ruleNameToUpdateDTO, ruleNameUpdatedDTO;
    
    private static RuleName testRuleName1, ruleNameToUpdate, ruleNameUpdated;
    
    
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

    @DisplayName("Test UPDATE Existing RULENAME")
    @Nested
    class TestUpdateExistingRuleName {  
    	
        @BeforeEach
        public void init() {
        	
 	       ruleNameToUpdate = RuleName.builder()
	        		.name("RuleName1")
	        		.description("Description1")
	        		.json("JSON1")
	        		.template("template1")
	        		.sqlStr("SqlStr1")
	        		.sqlPart("SqlPart1")
	        		.build();
 	       
	       ruleNameUpdated = RuleName.builder()
	        		.name("RuleName1")
	        		.description("Description update")
	        		.json("JSON1")
	        		.template("template1")
	        		.sqlStr("SqlStr1")
	        		.sqlPart("SqlPart1")
	        		.build();
	        
	        ruleNameToUpdateDTO = RuleNameDTO.builder()
 	        		.name("RuleName1")
 	        		.description("Description1")
 	        		.json("JSON1")
 	        		.template("template1")
 	        		.sqlStr("SqlStr1")
 	        		.sqlPart("SqlPart1")
 	        		.build();
	        
		       ruleNameUpdatedDTO = RuleNameDTO.builder()
		        		.name("RuleName1")
		        		.description("Description update")
		        		.json("JSON1")
		        		.template("template1")
		        		.sqlStr("SqlStr1")
		        		.sqlPart("SqlPart1")
		        		.build();
	        
       when(ruleNameRepository
    		   .findById(anyInt()))
       .thenReturn(java.util.Optional.ofNullable(testRuleName1));
	               
       when(ruleNameMapper
    		   .toRuleName(any(RuleNameDTO.class)))
       .thenReturn(ruleNameToUpdate);
       
       when(ruleNameRepository
    		   .save(any(RuleName.class)))
       .thenReturn(ruleNameUpdated);
       
       when(ruleNameMapper
    		   .toRuleNameDTO(any(RuleName.class)))
       .thenReturn(ruleNameUpdatedDTO);

        }

	// *******************************************************************	
    
    @DisplayName("Check <Execution Order>"
    		+ " - Given a existing RuleName,"
    		+ " when UPDATE RULENAME action request,"
    		+ " then all steps are executed in correct order and number of expected times")    
    @Test
    public void testUpdateExistingRuleNameExecutionOrderCheck() throws Exception {
		

    	ruleNameService.updateRuleName(2, ruleNameUpdatedDTO);

        InOrder inOrder = inOrder(ruleNameRepository, ruleNameMapper, ruleNameMapper);
        inOrder.verify(ruleNameRepository).findById(anyInt());
        inOrder.verify(ruleNameMapper).toRuleName(any(RuleNameDTO.class));
        inOrder.verify(ruleNameRepository).save(any(RuleName.class));
        inOrder.verify(ruleNameMapper).toRuleNameDTO(any(RuleName.class));
        
        verify(ruleNameRepository, times(1)).findById(anyInt());
        verify(ruleNameMapper, times(1)).toRuleName(any(RuleNameDTO.class));
        verify(ruleNameRepository, times(1)).save(any(RuleName.class));
        verify(ruleNameMapper, times(1)).toRuleNameDTO(any(RuleName.class));
        
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
 
 
  }

	
    @DisplayName("ERROR UPDATE RULENAME for non existing RULENAME data"
    		+ " - Given a non existing RULENAME,"
    		+ " when UPDATE RULENAMEaction request,"
    		+ " then RULENAME entry should respond"
    		+ " with Data Not Found Exception")
	@Test
	public void testGetRuleNameByIdNonExistingRuleNameData() throws Exception {

    	when(ruleNameRepository
    			.findById(anyInt()))
    	.thenReturn(java.util.Optional.empty());
    
    	// WHEN // THEN
    	assertThrows(DataNotFoundException.class, ()
        		-> ruleNameService.updateRuleName(5, ruleNameUpdatedDTO));
	}
    
	// *******************************************************************	 
	   
}