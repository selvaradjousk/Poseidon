package com.nnk.springboot.UnitTests.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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
import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.dto.BidListDTO;
import com.nnk.springboot.repository.BidListRepository;
import com.nnk.springboot.service.BidListService;
import com.nnk.springboot.util.BidListMapper;

@DisplayName("BidList Service Add BidList- UNIT TESTS")
@ExtendWith(MockitoExtension.class)
class BidListServiceAddTest {


    @InjectMocks
    private BidListService bidListService;

    @Mock
    private BidListRepository bidListRepository;
    
    @Mock
    private BidListMapper bidListMapper;
	
    @Mock
    private ObjectMapper objectMapper;
	
    private static BidListDTO testBidListDTO1, bidListToAddDTO;
    
    private static BidList testBidList1, bidListToAdd;
    
    
    @BeforeEach
    public void setUp() {
        objectMapper = new ObjectMapper();
        testBidListDTO1 = BidListDTO.builder()
        		.bidListId(2)
        		.account("Account")
        		.type("Type")
        		.bidQuantity(10.0)
        		.build();
 
        
        testBidList1 = BidList.builder()
         		.bidListId(2)
        		.account("Account")
        		.type("Type")
        		.bidQuantity(10.0)
        		.build();
        
       
    }
  
    // ***********************************************************************************

    @DisplayName("Test ADD New BIDLIST")
    @Nested
    class TestAddNewBidList {  
    	
        @BeforeEach
        public void init() {
        	
 	       bidListToAdd = BidList.builder()
        			.bidListId(2)
        			.account("Account")
        			.type("Type")
        			.bidQuantity(10.0)
	        		.build();
	        
	        bidListToAddDTO = BidListDTO.builder()
        			.bidListId(2)
        			.account("Account")
        			.type("Type")
        			.bidQuantity(10.0)
	        		.build();
       
       when(bidListMapper
    		   .toBidList(any(BidListDTO.class)))
       .thenReturn(bidListToAdd);
       
       when(bidListRepository
    		   .save(any(BidList.class)))
       .thenReturn(testBidList1);
       
       when(bidListMapper
    		   .toBidListDTO(any(BidList.class)))
       .thenReturn(testBidListDTO1);

        }

	// *******************************************************************	
    
    @DisplayName("Check <Execution Order>"
    		+ " - Given a new BidList,"
    		+ " when ADD BIDLIST action request,"
    		+ " then all steps are executed in correct order and number of expected times")    
    @Test
    public void testAddNewBidListExecutionOrderCheck() throws Exception {
		

    	bidListService.addBidList(bidListToAddDTO);

        InOrder inOrder = inOrder(bidListMapper, bidListRepository, bidListMapper);
        inOrder.verify(bidListMapper).toBidList(any(BidListDTO.class));
        inOrder.verify(bidListRepository).save(any(BidList.class));
        inOrder.verify(bidListMapper).toBidListDTO(any(BidList.class));
        
        verify(bidListMapper, times(1)).toBidList(any(BidListDTO.class));
        verify(bidListRepository, times(1)).save(any(BidList.class));
        verify(bidListMapper, times(1)).toBidListDTO(any(BidList.class));
    }
	

	// *******************************************************************	


    @DisplayName("Check <NotNull>"
		+ " - Given a new BidList,"
		+ " when ADD BIDLIST action request,"
		+ " then BIDLIST should not be null")	    
    @Test
    public void testAddNewBidListNotNullCheck() {
		

        BidListDTO bidListSaved = bidListService
        		.addBidList(bidListToAddDTO);

        assertNotNull(bidListSaved);
    }

	// *******************************************************************			
       

    @Test
    @DisplayName("Check <Validate> match of both same record instance "
    		+ " - Given a new BidList,"
    		+ " when ADD BIDLIST action request,"
    		+ " then BIDLIST added should be added and same as test record")
    public void testAddNewBidListReturnResultMatch() {
   			

   	        BidListDTO bidListSaved = bidListService
   	        		.addBidList(bidListToAddDTO);

   	        assertEquals(bidListToAddDTO.toString(), bidListSaved.toString());
   	        assertThat(bidListSaved).usingRecursiveComparison().isEqualTo(bidListToAddDTO);
   	    }
   
   	// *******************************************************************	
    
    
    }



}