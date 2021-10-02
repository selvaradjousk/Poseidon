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
import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.dto.BidListDTO;
import com.nnk.springboot.repository.BidListRepository;
import com.nnk.springboot.service.BidListService;
import com.nnk.springboot.util.BidListMapper;

@DisplayName("BidList Service GetBidList List - UNIT TESTS")
@ExtendWith(MockitoExtension.class)
class BidListServiceGetListTest {

    @InjectMocks
    private BidListService BidListService;

    @Mock
    private BidListRepository BidListRepository;
    
    @Mock
    private BidListMapper BidListMapper;
    
    private ObjectMapper objectMapper;
	
    private static BidListDTO testBidListDTO1, testBidListDTO2, bidListUpdatedDTO;
    
    private static BidList testBidList1, testBidList2, bidListUpdated;
    
    private static List<BidListDTO> BidListDTOList;
    
    private static List<BidList> BidListList;

    
    @BeforeEach
    public void setUp() {
        objectMapper = new ObjectMapper();
        testBidListDTO1 = BidListDTO.builder()
        		.bidListId(1)
        		.account("Account1")
        		.type("Type1")
        		.bidQuantity(10.0)
        		.build();
        
        testBidListDTO2 = BidListDTO.builder()
        		.bidListId(2)
        		.account("Account2")
        		.type("Type2")
        		.bidQuantity(20.0)
        		.build();
        
        bidListUpdatedDTO = BidListDTO.builder()
        		.bidListId(2)
        		.account("Account updated")
        		.type("Type updated")
        		.bidQuantity(50.0)
        		.build();
                
        BidListDTOList = Arrays.asList(testBidListDTO1, testBidListDTO2);   
        
        testBidList1 = BidList.builder()
        		.bidListId(1)
        		.account("Account1")
        		.type("Type1")
        		.bidQuantity(10.0)
        		.build();
        
        testBidList2 = BidList.builder()
        		.bidListId(2)
        		.account("Account2")
        		.type("Type2")
        		.bidQuantity(20.0)
        		.build();
        
        bidListUpdated = BidList.builder()
        		.bidListId(2)
        		.account("Account updated")
        		.type("Type updated")
        		.bidQuantity(50.0)
        		.build();
            
        BidListList = Arrays.asList(testBidList1, testBidList2);   
        
    }
    


   	// *******************************************************************
    @DisplayName("Test List BidLists")
    @Nested
    class TestAddNewBidList {  
    	
        @BeforeEach
        public void init() {
        	
            when(BidListRepository
            		.findAll())
            .thenReturn(BidListList);
            
            when(BidListMapper
            		.toBidListDTO(testBidList1))
            .thenReturn(testBidListDTO1);
            
            when(BidListMapper
            		.toBidListDTO(testBidList2))
            .thenReturn(testBidListDTO2);
            
        }
        
        
    	// *******************************************************************	
        
        @DisplayName("Check <Execution Order>"
        		+ " - Given a BidList List,"
        		+ " when Get BidList List action request,"
        		+ " then all steps are executed in correct order and number of expected times")    
        @Test
        public void testGetBidListsListExecutionOrderCheck() throws Exception {
   	
           // GIVEN

           // WHEN
           BidListService.getAllBidList();
           
           // THEN
           InOrder inOrder = inOrder(BidListRepository, BidListMapper);
           inOrder.verify(BidListRepository).findAll();
           inOrder.verify(BidListMapper).toBidListDTO(testBidList1);
           inOrder.verify(BidListMapper).toBidListDTO(testBidList2);
           
           verify(BidListRepository, times(1)).findAll();
           verify(BidListMapper, times(1)).toBidListDTO(testBidList1);
           verify(BidListMapper, times(1)).toBidListDTO(testBidList2);
       
        }
        
    	// *******************************************************************	
     	
                   
    } 
   
}
