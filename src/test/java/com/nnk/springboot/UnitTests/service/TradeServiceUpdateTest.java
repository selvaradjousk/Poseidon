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
import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.dto.TradeDTO;
import com.nnk.springboot.exception.DataNotFoundException;
import com.nnk.springboot.repository.TradeRepository;
import com.nnk.springboot.service.TradeService;
import com.nnk.springboot.util.TradeMapper;

@DisplayName("Trade Service Update Trade- UNIT TESTS")
@ExtendWith(MockitoExtension.class)
class TradeServiceUpdateTest {


    @InjectMocks
    private TradeService tradeService;

    @Mock
    private TradeRepository tradeRepository;
    
    @Mock
    private TradeMapper tradeMapper;
	
    @Mock
    private ObjectMapper objectMapper;
	
    private static TradeDTO testTradeDTO1, tradeToUpdateDTO, tradeUpdatedDTO;
    
    private static Trade testTrade1, tradeToUpdate, tradeUpdated;
    
    
    @BeforeEach
    public void setUp() {
        objectMapper = new ObjectMapper();
        testTradeDTO1 = TradeDTO.builder()
        		.tradeId(2)
        		.account("Account")
        		.type("Type")
        		.buyQuantity(10.0)
        		.build();
 
        
        testTrade1 = Trade.builder()
         		.tradeId(2)
        		.account("Account")
        		.type("Type")
        		.buyQuantity(10.0)
        		.build();
        
       
    }
  
    // ***********************************************************************************

    @DisplayName("Test UPDATE Existing TRADE")
    @Nested
    class TestUpdateExistingTrade {  
    	
        @BeforeEach
        public void init() {
        	
 	       tradeToUpdate = Trade.builder()
        			.tradeId(2)
        			.account("Account")
        			.type("Type")
        			.buyQuantity(10.0)
	        		.build();
 	       
	       tradeUpdated = Trade.builder()
       			.tradeId(2)
       			.account("Account updated")
       			.type("Type")
       			.buyQuantity(10.0)
	        		.build();
	        
	        tradeToUpdateDTO = TradeDTO.builder()
        			.tradeId(2)
        			.account("Account")
        			.type("Type")
        			.buyQuantity(10.0)
	        		.build();
	        
		       tradeUpdatedDTO = TradeDTO.builder()
		       			.tradeId(2)
		       			.account("Account updated")
		       			.type("Type")
		       			.buyQuantity(10.0)
			        		.build();
	        
       when(tradeRepository
    		   .findById(anyInt()))
       .thenReturn(java.util.Optional.ofNullable(testTrade1));
	               
       when(tradeMapper
    		   .toTrade(any(TradeDTO.class)))
       .thenReturn(tradeToUpdate);
       
       when(tradeRepository
    		   .save(any(Trade.class)))
       .thenReturn(tradeUpdated);
       
       when(tradeMapper
    		   .toTradeDTO(any(Trade.class)))
       .thenReturn(tradeUpdatedDTO);

        }

	// *******************************************************************	
    
    @DisplayName("Check <Execution Order>"
    		+ " - Given a existing Trade,"
    		+ " when UPDATE TRADE action request,"
    		+ " then all steps are executed in correct order and number of expected times")    
    @Test
    public void testUpdateExistingTradeExecutionOrderCheck() throws Exception {
		

    	tradeService.updateTrade(2, tradeUpdatedDTO);

        InOrder inOrder = inOrder(tradeRepository, tradeMapper, tradeMapper);
        inOrder.verify(tradeRepository).findById(anyInt());
        inOrder.verify(tradeMapper).toTrade(any(TradeDTO.class));
        inOrder.verify(tradeRepository).save(any(Trade.class));
        inOrder.verify(tradeMapper).toTradeDTO(any(Trade.class));
        
        verify(tradeRepository, times(1)).findById(anyInt());
        verify(tradeMapper, times(1)).toTrade(any(TradeDTO.class));
        verify(tradeRepository, times(1)).save(any(Trade.class));
        verify(tradeMapper, times(1)).toTradeDTO(any(Trade.class));
        
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
    public void testUpdateExistingPersonReturnResultMatch() {
   			

   	        TradeDTO tradeSaved = tradeService.updateTrade(2, tradeUpdatedDTO);

   	        assertEquals(tradeUpdatedDTO.toString(), tradeSaved.toString());
   	        assertThat(tradeSaved).usingRecursiveComparison().isEqualTo(tradeUpdatedDTO);
   	    }

   	// *******************************************************************	

 
  }


	
    @DisplayName("ERROR UPDATE TRADE for non existing TRADE data"
    		+ " - Given a non existing TRADE,"
    		+ " when UPDATE TRADEaction request,"
    		+ " then USER entry should respond"
    		+ " with Data Not Found Exception")
	@Test
	public void testGetUserByIdNonExistingUserData() throws Exception {

    	when(tradeRepository
    			.findById(anyInt()))
    	.thenReturn(java.util.Optional.empty());
    
    	// WHEN // THEN
    	assertThrows(DataNotFoundException.class, ()
        		-> tradeService.updateTrade(5, tradeUpdatedDTO));
	}
    
	// *******************************************************************	   
}