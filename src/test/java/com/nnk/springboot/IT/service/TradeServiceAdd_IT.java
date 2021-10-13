package com.nnk.springboot.IT.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nnk.springboot.dto.TradeDTO;
import com.nnk.springboot.service.TradeService;

@DisplayName("INTEGRATION TESTS - Service ==> Trade Add")
@SpringBootTest
@ActiveProfiles("test")
class TradeServiceAdd_IT {


	@Autowired
    private TradeService tradeService;

    private ObjectMapper objectMapper;
	
    private static TradeDTO tradeToAddDTO;
    

  
    // ***********************************************************************************

    	
        @BeforeEach
        public void init() {
	        
	        tradeToAddDTO = TradeDTO.builder()
        			.tradeId(7)
        			.account("Account")
        			.type("Type")
        			.buyQuantity(10.0)
	        		.build();


        }


	// *******************************************************************	

    
    @DisplayName("Check <NotNull>"
		+ " - Given a new Trade,"
		+ " when ADD TRADE action request,"
		+ " then TRADE should not be null")	    
    @Test
    public void testAddNewTradeNotNullCheck() {
		

        TradeDTO tradeSaved = tradeService.addTrade(tradeToAddDTO);

        assertNotNull(tradeSaved);
    }

	// *******************************************************************			
    
    @Test
    @DisplayName("Check <Validate> match of both same record instance "
    		+ " - Given a new Trade,"
    		+ " when ADD TRADE action request,"
    		+ " then TRADE added should be added and same as test record")
    public void testAddNewTradeReturnResultMatch() {
   			

   	        TradeDTO tradeSaved = tradeService.addTrade(tradeToAddDTO);

   	        assertEquals(tradeToAddDTO.toString(), tradeSaved.toString());
   	        assertThat(tradeSaved).usingRecursiveComparison().isEqualTo(tradeToAddDTO);
   	    }

    
   	// *******************************************************************	


}