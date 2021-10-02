package com.nnk.springboot.UnitTests.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
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
import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.dto.BidListDTO;
import com.nnk.springboot.repository.BidListRepository;
import com.nnk.springboot.service.BidListService;
import com.nnk.springboot.util.BidListMapper;

@DisplayName("BidList Service GET BY ID - UNIT TESTS")
@ExtendWith(MockitoExtension.class)
class BidListServiceGetByIdTest {

	   @InjectMocks
	    private BidListService bidListService;

	    @Mock
	    private BidListRepository bidListRepository;
	    
	    @Mock
	    private BidListMapper bidListMapper;
	    
	    private ObjectMapper objectMapper;
		
	    private static BidListDTO testBidListDTO1;
	    
	    private static BidList testBidList1;
	    
	    
	    @BeforeEach
	    public void setUp() {
	        objectMapper = new ObjectMapper();
	        testBidListDTO1 = BidListDTO.builder()
	        		.bidListId(1)
	        		.account("Account1")
	        		.type("Type1")
	        		.bidQuantity(10.0)
	        		.build();
 
	        
	        testBidList1 = BidList.builder()
	        		.bidListId(1)
	        		.account("Account1")
	        		.type("Type1")
	        		.bidQuantity(10.0)
	        		.build();
	        
	    }
  
    // ***********************************************************************************

    @DisplayName("Test GET BIDLIST BY IDENTITY")
    @Nested
    class TestGetBidListById  {  
    	
        @BeforeEach
        public void init() {
        	
            // GIVEN
            when(bidListRepository
            		.findById(anyInt()))
            .thenReturn(java.util.Optional.ofNullable(testBidList1));
            
            when(bidListMapper
            		.toBidListDTO(any(BidList.class)))
            .thenReturn(testBidListDTO1);  

        }

	// *******************************************************************	
    
    @DisplayName("Check <Execution Order>"
    		+ " - Given a BidList,"
    		+ " when GET BIDLIST BY ID action request,"
    		+ " then all steps are executed in correct order and number of expected times")    
    @Test
    public void testGetBidListByIdExecutionOrderCheck() throws Exception {
		

    	bidListService.getBidListById(1);

        InOrder inOrder = inOrder(bidListRepository, bidListMapper);
        inOrder.verify(bidListRepository).findById(anyInt());
        inOrder.verify(bidListMapper).toBidListDTO(any(BidList.class));
        
        verify(bidListRepository, times(1)).findById(anyInt());
        verify(bidListMapper, times(1)).toBidListDTO(any(BidList.class));
    }

    
	// *******************************************************************	
         
    
    @DisplayName("Check <NotNull>"
    		+ " - Given a existing BidList,"
    		+ " when GET BIDLIST By ID action request,"
    		+ " then BIDLIST should not be null")	    
	    @Test
	    public void testBidListByIdNotNullCheck() {
			

    		BidListDTO result = bidListService
    				.getBidListById(1);

	        assertNotNull(result);
	    }

	// ******************************************************************		
	   

    @Test
    @DisplayName("Check <Validate> match of both same record instance "
    		+ " - Given a existing BidList,"
    		+ " when GET BIDLIST By ID action request,"
    		+ " then BIDLIST ID same as test record")
    public void testAddNewPersonReturnResultMatch() {
			
    	BidListDTO result = bidListService
    			.getBidListById(1);

    	assertEquals(result, testBidListDTO1);
	    assertThat(result).usingRecursiveComparison().isEqualTo(testBidListDTO1);
	    assertEquals("Account1", result.getAccount());
	    assertEquals(1, result.getBidListId());
    }  

  } 
	// ******************************************************************		  


}
