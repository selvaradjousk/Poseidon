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
    private BidListService bidListService;

    @Mock
    private BidListRepository bidListRepository;
    
    @Mock
    private BidListMapper bidListMapper;
    
    private ObjectMapper objectMapper;
	
    private static BidListDTO testBidListDTO1, testBidListDTO2;
    
    private static BidList testBidList1, testBidList2;
    
    private static List<BidListDTO> bidListDTOList;
    
    private static List<BidList> bidListList;

    
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
                 
        bidListDTOList = Arrays.asList(testBidListDTO1, testBidListDTO2);   
        
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
            
        bidListList = Arrays.asList(testBidList1, testBidList2);   
        
    }
    


   	// *******************************************************************
    @DisplayName("Test List BidLists")
    @Nested
    class TestAddNewBidList {  
    	
        @BeforeEach
        public void init() {
        	
        	
            // GIVEN
            when(bidListRepository
            		.findAll())
            .thenReturn(bidListList);
            
            when(bidListMapper
            		.toBidListDTO(testBidList1))
            .thenReturn(testBidListDTO1);
            
            when(bidListMapper
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
   	
           // WHEN
           bidListService.getAllBidList();
           
           // THEN
           InOrder inOrder = inOrder(bidListRepository, bidListMapper);
           inOrder.verify(bidListRepository).findAll();
           inOrder.verify(bidListMapper).toBidListDTO(testBidList1);
           inOrder.verify(bidListMapper).toBidListDTO(testBidList2);
           
           verify(bidListRepository, times(1)).findAll();
           verify(bidListMapper, times(1)).toBidListDTO(testBidList1);
           verify(bidListMapper, times(1)).toBidListDTO(testBidList2);
       
        }
        
    	// *******************************************************************	
     	

        
        @DisplayName("Check Check <NotNull>"
        		+ " - Given a BidList List,"
        		+ " when Get BidList List action request,"
        		+ " then returns BidListslist not null")    
        @Test
        public void testGetBidListsListNotNullCheck() throws Exception {
   	
           // GIVEN

           // WHEN
           List<BidListDTO> result = bidListService
        		   .getAllBidList();
           
           // THEN
           assertNotNull(result);
       
        }
        

      // *******************************************************************	          
                

        
        @DisplayName("Check <Validate> match of both same record instance "
        		+ " - Given a BidList List,"
        		+ " when Get BidList List action request,"
        		+ " then BidList added should be added and same as test record")   
        @Test
        public void testGetBidListsListREsultMatchCheck() throws Exception {
   	
           // GIVEN

           // WHEN
           List<BidListDTO> result =bidListService
        		   .getAllBidList();
           
           // THEN
           assertEquals(bidListDTOList, result);
       
        }
   	
        
      // *******************************************************************	
                    


        @DisplayName("Check <Execution Order>"
        		+ " - Given a BidList List,"
        		+ " when Get BidList List action request,"
        		+ " then return expected Number of BidLists")    
        @Test
        public void testGetBidListsListRecordsNumberMatchCheck() throws Exception {
   	
           // GIVEN

           // WHEN
           List<BidListDTO> result = bidListService.getAllBidList();
           
           // THEN
           assertEquals(bidListDTOList.size(), result.size());
           assertEquals(2, result.size());

        }    
        
        // *******************************************************************	
            
    
    } 
   
}
