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

@DisplayName("INTEGRATION TESTS - Service ==> Trade GET BY ID")
@SpringBootTest
@ActiveProfiles("test")
class TradeServiceGetById_IT {

	@Autowired
    private TradeService tradeService;

    private ObjectMapper objectMapper;
	
    private static TradeDTO testTradeDTO1;

    
    
    @BeforeEach
    public void setUp() {
        objectMapper = new ObjectMapper();
        testTradeDTO1 = TradeDTO.builder()
        		.tradeId(3)
        		.account("account3")
        		.type("type3")
        		.buyQuantity(30.0)
        		.build();
        
       
    }

    
	// *******************************************************************	
    
    @DisplayName("Check <NotNull>"
    		+ " - Given a existing Trade,"
    		+ " when GET TRADE By ID action request,"
    		+ " then TRADE should not be null")	    
	    @Test
	    public void testTradeByIdNotNullCheck() {
			

    		TradeDTO result = tradeService
    				.getTradeById(3);

	        assertNotNull(result);
	    }

	// ******************************************************************		
	   
    
    @Test
    @DisplayName("Check <Validate> match of both same record instance "
    		+ " - Given a existing Trade,"
    		+ " when GET TRADE By ID action request,"
    		+ " then TRADE ID same as test record")
    public void testGetTradeByIdReturnResultMatch() {
			
    	TradeDTO result = tradeService
    			.getTradeById(3);

    	assertEquals(testTradeDTO1.toString(), result.toString());
	    assertThat(result).usingRecursiveComparison().isEqualTo(testTradeDTO1);
	    assertEquals("account3", result.getAccount());
	    assertEquals(3, result.getTradeId());
    }  
    
	
  	// *******************************************************************	
  	
      @DisplayName("ERROR GET EXISTING TRADE by ID for non existing TRADE data"
      		+ " - Given a non existing TRADE,"
      		+ " when GET TRADE By ID action request,"
      		+ " then TRADE entry should respond"
      		+ " with Data Not Found Exception")
  	@Test
  	public void testGetTradeByIdNonExistingTradeData() throws Exception {

      
      	// WHEN // THEN
      	assertThrows(DataNotFoundException.class, ()
          		-> tradeService.getTradeById(100));
  	}
      
  	// *******************************************************************	  

}