package com.nnk.springboot.UnitTests.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

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

@DisplayName("Service ==> Trade Get List - UNIT TESTS")
@ExtendWith(MockitoExtension.class)
class TradeServiceGetListTest {

    @InjectMocks
    private TradeService TradeService;

    @Mock
    private TradeRepository TradeRepository;
    
    @Mock
    private TradeMapper TradeMapper;
    
    private ObjectMapper objectMapper;
	
    private static TradeDTO testTradeDTO1, testTradeDTO2;
    
    private static Trade testTrade1, testTrade2;
    
    private static List<TradeDTO> tradeDTOList;
    
    private static List<Trade> tradeList;

    
    @BeforeEach
    public void setUp() {
        objectMapper = new ObjectMapper();
        testTradeDTO1 = TradeDTO.builder()
        		.account("Account")
        		.type("Type")
        		.buyQuantity(10.0)
        		.build();
        
        testTradeDTO2 = TradeDTO.builder()
        		.account("Account")
        		.type("Type")
        		.buyQuantity(10.0)
        		.build();
        
        tradeDTOList = Arrays.asList(testTradeDTO1, testTradeDTO2);   
        
        testTrade1 = Trade.builder()
        		.account("Account")
        		.type("Type")
        		.buyQuantity(10.0)
        		.build();
        
        testTrade2 = Trade.builder()
        		.account("Account")
        		.type("Type")
        		.buyQuantity(10.0)
        		.build();
        
        tradeList = Arrays.asList(testTrade1, testTrade2);   
        
    }
    


   	// *******************************************************************	
    @DisplayName("Test List Trades")
    @Nested
    class TestGetListTrade {  
    	
        @BeforeEach
        public void init() {
        	
            when(TradeRepository
            		.findAll())
            .thenReturn(tradeList);
            
            when(TradeMapper
            		.toTradeDTO(testTrade1))
            .thenReturn(testTradeDTO1);
            
            when(TradeMapper
            		.toTradeDTO(testTrade2))
            .thenReturn(testTradeDTO2);
            
        }
        
        
    	// *******************************************************************	
        
        @DisplayName("Check <Execution Order>"
        		+ " - Given a Trade List,"
        		+ " when Get Trade List action request,"
        		+ " then all steps are executed in correct order and number of expected times")    
        @Test
        public void testGetTradesListExecutionOrderCheck() throws Exception {
   	
           // GIVEN

           // WHEN
           TradeService.getAllTrade();
           
           // THEN
           InOrder inOrder = inOrder(TradeRepository, TradeMapper);
           inOrder.verify(TradeRepository).findAll();
           inOrder.verify(TradeMapper).toTradeDTO(testTrade1);
           inOrder.verify(TradeMapper).toTradeDTO(testTrade2);
           
           verify(TradeRepository, times(1)).findAll();
           verify(TradeMapper, times(1)).toTradeDTO(testTrade1);
           verify(TradeMapper, times(1)).toTradeDTO(testTrade2);
       
        }
   	
        
      	// *******************************************************************	
          
          @DisplayName("Check Check <NotNull>"
          		+ " - Given a Trade List,"
          		+ " when Get Trade List action request,"
          		+ " then returns Tradeslist not null")    
          @Test
          public void testGetTradesListNotNullCheck() throws Exception {
     	
             // GIVEN

             // WHEN
             List<TradeDTO> result = TradeService.getAllTrade();
             
             // THEN
             assertNotNull(result);
         
          }
          
  
        // *******************************************************************	          
   
          
          @DisplayName("Check <Validate> match of both same record instance "
          		+ " - Given a Trade List,"
          		+ " when Get Trade List action request,"
          		+ " then Trade added should be added and same as test record")   
          @Test
          public void testGetTradesListREsultMatchCheck() throws Exception {
     	
             // GIVEN

             // WHEN
             List<TradeDTO> result = TradeService.getAllTrade();
             
             // THEN
             assertEquals(tradeDTOList, result);
         
          }
     	
          
        // *******************************************************************	
                      


          @DisplayName("Check <Execution Order>"
          		+ " - Given a Trade List,"
          		+ " when Get Trade List action request,"
          		+ " then return expected Number of Trades")    
          @Test
          public void testGetTradesListRecordsNumberMatchCheck() throws Exception {
     	
             // GIVEN

             // WHEN
             List<TradeDTO> result = TradeService.getAllTrade();
             
             // THEN
             assertEquals(tradeDTOList.size(), result.size());
             assertEquals(2, result.size());

          }    
          
          // *******************************************************************	
                        
    } 
   
}
