package com.nnk.springboot.UnitTests.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
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

@DisplayName("Trade Service DELETE - UNIT TESTS")
@ExtendWith(MockitoExtension.class)
class TradeServiceDeleteTest {

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

	// *******************************************************************	
    
	@DisplayName("Delete Trade - "
			+ "GIVEN a TRADE  "
			+ "WHEN Requested DELETE Trade"
			+ "THEN returns expected trade deleted")	    
    @Test
    public void testDeleteTrade() throws Exception {
	
        // GIVEN
		 when(tradeRepository
				 .findById(anyInt()))
		 .thenReturn(java.util.Optional.ofNullable(testTrade1));
		 
        // WHEN
		 tradeService.deleteTrade(1);
        
        // THEN
	       InOrder inOrder = inOrder(tradeRepository);
	       inOrder.verify(tradeRepository).findById(anyInt());
	       inOrder.verify(tradeRepository).deleteById(anyInt());
	       
	       verify(tradeRepository, times(1)).findById(anyInt());
	       verify(tradeRepository, times(1)).deleteById(anyInt());
    
	}
   

	// *******************************************************************	
	
    @DisplayName("Check <Exception>"
		+ "GIVEN a Trade not exist "
		+ "WHEN Requested DELETE Trade "
		+ "THEN throws Exception")	    
	@Test
	public void testDeleteTradeNotExists() throws Exception {

    	when(tradeRepository
    			.findById(anyInt()))
    	.thenReturn(java.util.Optional.empty());
    
    	// WHEN // THEN
    	assertThrows(DataNotFoundException.class, ()
        		-> tradeService.deleteTrade(anyInt()));
	} 

	// *******************************************************************	

}
