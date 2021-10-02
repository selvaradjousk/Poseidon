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
import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.dto.BidListDTO;
import com.nnk.springboot.exception.DataNotFoundException;
import com.nnk.springboot.repository.BidListRepository;
import com.nnk.springboot.service.BidListService;
import com.nnk.springboot.util.BidListMapper;

@DisplayName("BidList Service Update BidList- UNIT TESTS")
@ExtendWith(MockitoExtension.class)
class BidListServiceUpdateTest {


    @InjectMocks
    private BidListService bidListService;

    @Mock
    private BidListRepository bidListRepository;
    
    @Mock
    private BidListMapper bidListMapper;
	
    @Mock
    private ObjectMapper objectMapper;
	
    private static BidListDTO testBidListDTO1, bidListToUpdateDTO, bidListUpdatedDTO;
    
    private static BidList testBidList1, bidListToUpdate, bidListUpdated;
    
    
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

    @DisplayName("Test UPDATE Existing BIDLIST")
    @Nested
    class TestUpdateExistingBidList {  
    	
        @BeforeEach
        public void init() {
        	
 	       bidListToUpdate = BidList.builder()
        			.bidListId(2)
        			.account("Account")
        			.type("Type")
        			.bidQuantity(10.0)
	        		.build();
 	       
	       bidListUpdated = BidList.builder()
       			.bidListId(2)
       			.account("Account updated")
       			.type("Type")
       			.bidQuantity(10.0)
	        		.build();
	        
	        bidListToUpdateDTO = BidListDTO.builder()
        			.bidListId(2)
        			.account("Account")
        			.type("Type")
        			.bidQuantity(10.0)
	        		.build();
	        
		       bidListUpdatedDTO = BidListDTO.builder()
		       			.bidListId(2)
		       			.account("Account updated")
		       			.type("Type")
		       			.bidQuantity(10.0)
			        		.build();
	        
       when(bidListRepository
    		   .findById(anyInt()))
       .thenReturn(java.util.Optional.ofNullable(testBidList1));
	               
       when(bidListMapper
    		   .toBidList(any(BidListDTO.class)))
       .thenReturn(bidListToUpdate);
       
       when(bidListRepository
    		   .save(any(BidList.class)))
       .thenReturn(bidListUpdated);
       
       when(bidListMapper
    		   .toBidListDTO(any(BidList.class)))
       .thenReturn(bidListUpdatedDTO);

        }

	// *******************************************************************	
    
    @DisplayName("Check <Execution Order>"
    		+ " - Given a existing BidList,"
    		+ " when UPDATE BIDLIST action request,"
    		+ " then all steps are executed in correct order and number of expected times")    
    @Test
    public void testUpdateExistingBidListExecutionOrderCheck() throws Exception {
		

    	bidListService.updateBidList(2, bidListUpdatedDTO);

        InOrder inOrder = inOrder(bidListRepository, bidListMapper, bidListMapper);
        inOrder.verify(bidListRepository).findById(anyInt());
        inOrder.verify(bidListMapper).toBidList(any(BidListDTO.class));
        inOrder.verify(bidListRepository).save(any(BidList.class));
        inOrder.verify(bidListMapper).toBidListDTO(any(BidList.class));
        
        verify(bidListRepository, times(1)).findById(anyInt());
        verify(bidListMapper, times(1)).toBidList(any(BidListDTO.class));
        verify(bidListRepository, times(1)).save(any(BidList.class));
        verify(bidListMapper, times(1)).toBidListDTO(any(BidList.class));
        
    }
	

	// *******************************************************************	
    
 
    
    @DisplayName("Check <NotNull>"
		+ " - Given a new BidList,"
		+ " when UPDATE BIDLIST action request,"
		+ " then BIDLIST should not be null")	    
    @Test
    public void testUpdateExistingBidListNotNullCheck() {
		

        BidListDTO bidListSaved = bidListService
        		.updateBidList(2, bidListUpdatedDTO);

        assertNotNull(bidListSaved);
    }

	// *******************************************************************			

    @Test
    @DisplayName("Check <Validate> match of both same record instance "
    		+ " - Given a new BidList,"
    		+ " when UPDATE BIDLIST action request,"
    		+ " then BIDLIST updated should be updateed and same as test record")
    public void testUpdateExistingBidListReturnResultMatch() {
   			

   	        BidListDTO bidListSaved = bidListService
   	        		.updateBidList(2, bidListUpdatedDTO);

   	        assertEquals(bidListUpdatedDTO.toString(), bidListSaved.toString());
   	        assertThat(bidListSaved).usingRecursiveComparison().isEqualTo(bidListUpdatedDTO);
   	    }

   	// *******************************************************************	

    
    
	}
    
	// *******************************************************************	   
}