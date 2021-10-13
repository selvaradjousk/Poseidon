package com.nnk.springboot.IT.service;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.nnk.springboot.exception.DataNotFoundException;
import com.nnk.springboot.service.TradeService;

@DisplayName("INTEGRATION TESTS - Service ==> Trade DELETE")
@SpringBootTest
@ActiveProfiles("test")
class TradeServiceDelete_IT {

	@Autowired
    private TradeService tradeService;


	// *******************************************************************	
    
	@DisplayName("Delete Trade - "
			+ "GIVEN a TRADE  "
			+ "WHEN Requested DELETE Trade"
			+ "THEN returns expected trade deleted")	    
    @Test
    public void testDeleteTrade() throws Exception {
	
    
	        // WHEN
			 tradeService.deleteTrade(1);
		    
	    	// THEN
	    	assertThrows(DataNotFoundException.class, ()
	        		-> tradeService.deleteTrade(1));
    
	}
   

	// *******************************************************************	
	
    @DisplayName("Check <Exception>"
		+ "GIVEN a Trade not exist "
		+ "WHEN Requested DELETE Trade "
		+ "THEN throws Exception")	    
	@Test
	public void testDeleteTradeNotExists() throws Exception {

    
    	// WHEN // THEN
    	assertThrows(DataNotFoundException.class, ()
        		-> tradeService.deleteTrade(100));
	} 

	// *******************************************************************	

}
