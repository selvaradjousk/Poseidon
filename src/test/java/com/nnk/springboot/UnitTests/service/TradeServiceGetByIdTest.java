package com.nnk.springboot.UnitTests.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
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
import com.nnk.springboot.repository.TradeRepository;
import com.nnk.springboot.service.TradeService;
import com.nnk.springboot.util.TradeMapper;

@DisplayName("Trade Service GET BY ID - UNIT TESTS")
@ExtendWith(MockitoExtension.class)
class TradeServiceGetByIdTest {

    @InjectMocks
    private TradeService tradeService;

    @Mock
    private TradeRepository tradeRepository;
    
    @Mock
    private TradeMapper tradeMapper;

    @Mock
    private ObjectMapper objectMapper;
	
    private static TradeDTO testTradeDTO1;
    
    private static Trade testTrade1;
    
    
    @BeforeEach
    public void setUp() {
        objectMapper = new ObjectMapper();
        testTradeDTO1 = TradeDTO.builder()
        		.tradeId(1)
        		.account("Account")
        		.type("Type")
        		.buyQuantity(10.0)
        		.build();
        
       
        testTrade1 = Trade.builder()
        		.tradeId(1)
        		.account("Account")
        		.type("Type")
        		.buyQuantity(10.0)
        		.build();
        
       
    }
  
    // ***********************************************************************************

    @DisplayName("Test GET USER BY IDENTITY")
    @Nested
    class TestGetTradeById  {  
    	
        @BeforeEach
        public void init() {
        	
            when(tradeRepository
            		.findById(anyInt()))
            .thenReturn(java.util.Optional.ofNullable(testTrade1));
            
            when(tradeMapper
            		.toTradeDTO(any(Trade.class)))
            .thenReturn(testTradeDTO1);  

        }

	// *******************************************************************	
    
    @DisplayName("Check <Execution Order>"
    		+ " - Given a Trade,"
    		+ " when GET USER BY ID action request,"
    		+ " then all steps are executed in correct order and number of expected times")    
    @Test
    public void testGetTradeByIdExecutionOrderCheck() throws Exception {
		

    	tradeService.getTradeById(1);

        InOrder inOrder = inOrder(tradeRepository, tradeMapper);
        inOrder.verify(tradeRepository).findById(anyInt());
        inOrder.verify(tradeMapper).toTradeDTO(any(Trade.class));
        
        verify(tradeRepository, times(1)).findById(anyInt());
        verify(tradeMapper, times(1)).toTradeDTO(any(Trade.class));
    }

    
	// *******************************************************************	
    
    @DisplayName("Check <NotNull>"
    		+ " - Given a existing Trade,"
    		+ " when GET USER By ID action request,"
    		+ " then USER should not be null")	    
	    @Test
	    public void testTradeByIdNotNullCheck() {
			

    		TradeDTO result = tradeService
    				.getTradeById(1);

	        assertNotNull(result);
	    }

	// ******************************************************************		
	   
    
    

    }
}