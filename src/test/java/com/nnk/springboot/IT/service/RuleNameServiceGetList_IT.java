package com.nnk.springboot.IT.service;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.dto.RuleNameDTO;
import com.nnk.springboot.repository.RuleNameRepository;
import com.nnk.springboot.service.RuleNameService;
import com.nnk.springboot.util.RuleNameMapper;

@DisplayName("INTEGRATION TESTS - Service ==> RuleName Get List")
@SpringBootTest
@ActiveProfiles("test")
class RuleNameServiceGetList_IT {

	@Autowired
    private RuleNameService RuleNameService;

  
        
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

        
        @DisplayName("Check <Execution Order>"
          		+ " - Given a RuleName List,"
          		+ " when Get RuleName List action request,"
          		+ " then return expected Number of RuleNames")    
          @Test
          public void testGetRuleNamesListRecordsNumberMatchCheck() throws Exception {
     	
             // GIVEN

             // WHEN
             List<RuleNameDTO> result = RuleNameService
            		 .getAllRuleName();
             
             // THEN
             assertEquals(5, result.size());

          }    
          
          // *******************************************************************    
    
}
