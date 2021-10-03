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
import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.dto.BidListDTO;
import com.nnk.springboot.exception.DataNotFoundException;
import com.nnk.springboot.repository.BidListRepository;
import com.nnk.springboot.service.BidListService;
import com.nnk.springboot.util.BidListMapper;

@DisplayName("Service ==>  BidList DELETE - UNIT TESTS")
@ExtendWith(MockitoExtension.class)
class BidListServiceDeleteTest {

    @InjectMocks
    private BidListService bidListService;

    @Mock
    private BidListRepository bidListRepository;
    
    @Mock
    private BidListMapper bidListMapper;

    @Mock
    private ObjectMapper objectMapper;
	
    private static BidListDTO testBidListDTO1;
    
    private static BidList testBidList1;
    
    
    @BeforeEach
    public void setUp() {
        objectMapper = new ObjectMapper();
        testBidListDTO1 = BidListDTO.builder()
        		.bidListId(1)
        		.account("Account")
        		.type("Type")
        		.bidQuantity(10.0)
        		.build();
        
       
        testBidList1 = BidList.builder()
        		.bidListId(1)
        		.account("Account")
        		.type("Type")
        		.bidQuantity(10.0)
        		.build();
        
       
    }

	// *******************************************************************	
    
	@DisplayName("Delete BidList - "
			+ "GIVEN a BIDLIST  "
			+ "WHEN Requested DELETE BidList"
			+ "THEN returns expected bidList deleted")	    
    @Test
    public void testDeleteBidList() throws Exception {
	
        // GIVEN
		 when(bidListRepository
				 .findById(anyInt()))
		 .thenReturn(java.util.Optional.ofNullable(testBidList1));
		 
        // WHEN
		 bidListService.deleteBidList(1);
        
        // THEN
	       InOrder inOrder = inOrder(bidListRepository);
	       inOrder.verify(bidListRepository).findById(anyInt());
	       inOrder.verify(bidListRepository).deleteById(anyInt());
	       
	       verify(bidListRepository, times(1)).findById(anyInt());
	       verify(bidListRepository, times(1)).deleteById(anyInt());
    
	}
   

	// *******************************************************************	
	
    @DisplayName("Check <Exception>"
		+ "GIVEN a BidList not exist "
		+ "WHEN Requested DELETE BidList "
		+ "THEN throws Exception")	    
	@Test
	public void testDeleteBidListNotExists() throws Exception {

    	when(bidListRepository
    			.findById(anyInt()))
    	.thenReturn(java.util.Optional.empty());
    
    	// WHEN // THEN
    	assertThrows(DataNotFoundException.class, ()
        		-> bidListService.deleteBidList(anyInt()));
	} 

	// *******************************************************************	

}
