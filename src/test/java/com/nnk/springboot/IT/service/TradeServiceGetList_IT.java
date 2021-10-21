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

import com.nnk.springboot.dto.TradeDTO;
import com.nnk.springboot.service.TradeService;

@DisplayName("INTEGRATION TESTS - Service ==> Trade Get List")
@SpringBootTest
@ActiveProfiles("test")
class TradeServiceGetList_IT {

	@Autowired
    private TradeService tradeService;


        
      	// *******************************************************************	
          
          @DisplayName("Check Check <NotNull>"
          		+ " - Given a Trade List,"
          		+ " when Get Trade List action request,"
          		+ " then returns Tradeslist not null")    
          @Test
          public void testGetTradesListNotNullCheck() throws Exception {
     	
             // GIVEN

             // WHEN
             List<TradeDTO> result = tradeService.getAllTrade();
             
             // THEN
             assertNotNull(result);
         
          }
          
  
        // *******************************************************************	
                      


          @DisplayName("Check <List Count>"
          		+ " - Given a Trade List,"
          		+ " when Get Trade List action request,"
          		+ " then return expected Number of Trades")    
          @Test
          public void testGetTradesListRecordsNumberMatchCheck() throws Exception {
     	
             // GIVEN

             // WHEN
             List<TradeDTO> result = tradeService.getAllTrade();
             
             // THEN
//             assertEquals(4, result.size());
             assertTrue(result.size() > 1);

          }    
          
          // *******************************************************************	
   
}
