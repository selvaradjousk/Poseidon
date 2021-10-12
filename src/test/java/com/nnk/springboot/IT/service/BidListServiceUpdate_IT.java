package com.nnk.springboot.IT.service;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.dto.BidListDTO;
import com.nnk.springboot.exception.DataNotFoundException;
import com.nnk.springboot.repository.BidListRepository;
import com.nnk.springboot.service.BidListService;
import com.nnk.springboot.util.BidListMapper;

@DisplayName("INTEGRATION TESTS - Service ==>  BidList Update")
@SpringBootTest
@ActiveProfiles("test")
class BidListServiceUpdate_IT {


    @Autowired
    private BidListService bidListService;

    private ObjectMapper objectMapper;
	
    private static BidListDTO bidListUpdatedDTO;

    
    
    @BeforeEach
    public void setUp() {
        objectMapper = new ObjectMapper();

	        
		       bidListUpdatedDTO = BidListDTO.builder()
		       			.bidListId(2)
		       			.account("Account updated")
		       			.type("Type")
		       			.bidQuantity(10.0)
			        		.build();
	             
    }
  
    // ***********************************************************************************

    
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

	
    @DisplayName("ERROR UPDATE BIDLIST for non existing BIDLIST data"
    		+ " - Given a non existing BIDLIST,"
    		+ " when UPDATE BIDLISTaction request,"
    		+ " then BIDLIST entry should respond"
    		+ " with Data Not Found Exception")
	@Test
	public void testGetBidListByIdNonExistingBidListData() throws Exception {
    
    	// WHEN // THEN
    	assertThrows(DataNotFoundException.class, ()
        		-> bidListService.updateBidList(40, bidListUpdatedDTO));
	}
    
	// *******************************************************************	   
  
}