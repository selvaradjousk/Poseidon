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
import com.nnk.springboot.dto.TradeDTO;
import com.nnk.springboot.exception.DataNotFoundException;
import com.nnk.springboot.service.TradeService;

@DisplayName("INTEGRATION TESTS - Service ==> Trade Update")
@SpringBootTest
@ActiveProfiles("test")
class TradeServiceUpdate_IT {


	@Autowired
    private TradeService tradeService;

    private ObjectMapper objectMapper;
	
    private static TradeDTO tradeUpdatedDTO;

    
    // ***********************************************************************************

        @BeforeEach
        public void init() {

	        
		       tradeUpdatedDTO = TradeDTO.builder()
		       			.tradeId(2)
		       			.account("Account updated")
		       			.type("Type")
		       			.buyQuantity(10.0)
			        		.build();

        }


	

	// *******************************************************************	
    
    
    @DisplayName("Check <NotNull>"
		+ " - Given a new Trade,"
		+ " when UPDATE TRADE action request,"
		+ " then TRADE should not be null")	    
    @Test
    public void testUpdateExistingTradeNotNullCheck() {
		

        TradeDTO tradeSaved = tradeService.updateTrade(2, tradeUpdatedDTO);

        assertNotNull(tradeSaved);
    }

	// *******************************************************************			
    
    

    @Test
    @DisplayName("Check <Validate> match of both same record instance "
    		+ " - Given a new Trade,"
    		+ " when UPDATE TRADE action request,"
    		+ " then TRADE updateed should be updateed and same as test record")
    public void testUpdateExistingTradeReturnResultMatch() {
   			

   	        TradeDTO tradeSaved = tradeService.updateTrade(2, tradeUpdatedDTO);

   	        assertEquals(tradeUpdatedDTO.toString(), tradeSaved.toString());
   	        assertThat(tradeSaved).usingRecursiveComparison().isEqualTo(tradeUpdatedDTO);
   	    }

   	// *******************************************************************	



	
    @DisplayName("ERROR UPDATE TRADE for non existing TRADE data"
    		+ " - Given a non existing TRADE,"
    		+ " when UPDATE TRADEaction request,"
    		+ " then TRADE entry should respond"
    		+ " with Data Not Found Exception")
	@Test
	public void testGetTradeByIdNonExistingTradeData() throws Exception {

    
    	// WHEN // THEN
    	assertThrows(DataNotFoundException.class, ()
        		-> tradeService.updateTrade(100, tradeUpdatedDTO));
	}
    
	// *******************************************************************	   
}