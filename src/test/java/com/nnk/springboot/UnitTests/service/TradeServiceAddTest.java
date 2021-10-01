package com.nnk.springboot.UnitTests.service;

import static org.mockito.ArgumentMatchers.any;
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

@DisplayName("Trade Service Add Trade- UNIT TESTS")
@ExtendWith(MockitoExtension.class)
class TradeServiceAddTest {


    @InjectMocks
    private TradeService tradeService;

    @Mock
    private TradeRepository tradeRepository;
    
    @Mock
    private TradeMapper tradeMapper;
	
    @Mock
    private ObjectMapper objectMapper;
	
    private static TradeDTO testTradeDTO1, tradeToAddDTO;
    
    private static Trade testTrade1, tradeToAdd;
    
    
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

    @DisplayName("Test ADD New TRADE")
    @Nested
    class TestAddNewTrade {  
    	
        @BeforeEach
        public void init() {
        	
 	       tradeToAdd = Trade.builder()
        			.tradeId(1)
        			.account("Account")
        			.type("Type")
        			.buyQuantity(10.0)
	        		.build();
	        
	        tradeToAddDTO = TradeDTO.builder()
        			.tradeId(2)
        			.account("Account")
        			.type("Type")
        			.buyQuantity(10.0)
	        		.build();
       
       when(tradeMapper
    		   .toTrade(any(TradeDTO.class)))
       .thenReturn(tradeToAdd);
       
       when(tradeRepository
    		   .save(any(Trade.class)))
       .thenReturn(testTrade1);
       
       when(tradeMapper
    		   .toTradeDTO(any(Trade.class)))
       .thenReturn(testTradeDTO1);

        }

	// *******************************************************************	
    
    @DisplayName("Check <Execution Order>"
    		+ " - Given a new Trade,"
    		+ " when ADD TRADE action request,"
    		+ " then all steps are executed in correct order and number of expected times")    
    @Test
    public void testAddNewTradeExecutionOrderCheck() throws Exception {
		

    	tradeService.addTrade(tradeToAddDTO);

        InOrder inOrder = inOrder(tradeRepository, tradeMapper, tradeMapper);
        inOrder.verify(tradeMapper).toTrade(any(TradeDTO.class));
        inOrder.verify(tradeRepository).save(any(Trade.class));
        inOrder.verify(tradeMapper).toTradeDTO(any(Trade.class));
        
        verify(tradeMapper, times(1)).toTrade(any(TradeDTO.class));
        inOrder.verify(tradeRepository, times(1)).save(any(Trade.class));
        inOrder.verify(tradeMapper, times(1)).toTradeDTO(any(Trade.class));
    }
	

	// *******************************************************************	
    }


}