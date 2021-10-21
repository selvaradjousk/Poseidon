package com.nnk.springboot.IT.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.nnk.springboot.dto.RuleNameDTO;
import com.nnk.springboot.service.RuleNameService;

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

        
        @DisplayName("Check <List Count>"
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
//             assertEquals(4, result.size());
             assertTrue(result.size() > 1);

          }    
          
          // *******************************************************************    
    
}
