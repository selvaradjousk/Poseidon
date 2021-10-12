package com.nnk.springboot.IT.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nnk.springboot.dto.BidListDTO;
import com.nnk.springboot.service.BidListService;


@DisplayName("INTEGRATION TESTS - Service ==>  BidList Add")
@SpringBootTest
@ActiveProfiles("test")
class BidListServiceAdd_IT {


    @Autowired
    private BidListService bidListService;

    private ObjectMapper objectMapper;
	
    private static BidListDTO testBidListDTOAdded, bidListToAddDTO;

    
    @BeforeEach
    public void setUp() {
        objectMapper = new ObjectMapper();

        bidListToAddDTO = BidListDTO.builder()
        		.account("Account To Add")
        		.type("Type")
        		.bidQuantity(10.0)
        		.build();

        testBidListDTOAdded = BidListDTO.builder()
        		.bidListId(6)
        		.account("Account To Add")
        		.type("Type")
        		.bidQuantity(10.0)
        		.build();
 
    }
  
    // ***********************************************************************************


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

   	        assertEquals(testBidListDTOAdded.toString(), bidListSaved.toString());
   	        assertThat(bidListSaved).usingRecursiveComparison().isEqualTo(testBidListDTOAdded);
   	    }
   
   	// *******************************************************************	

}