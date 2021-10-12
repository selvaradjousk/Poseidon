package com.nnk.springboot.IT.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nnk.springboot.dto.BidListDTO;
import com.nnk.springboot.exception.DataNotFoundException;
import com.nnk.springboot.service.BidListService;

@DisplayName("INTEGRATION TESTS - Service ==>  BidList GET BY ID")
@SpringBootTest
@ActiveProfiles("test")
class BidListServiceGetById_IT {

    @Autowired
    private BidListService bidListService;

	    
    private ObjectMapper objectMapper;
	
    private static BidListDTO testBidListDTO1;
    
    
    
    @BeforeEach
    public void setUp() {
        objectMapper = new ObjectMapper();
        testBidListDTO1 = BidListDTO.builder()
        		.bidListId(1)
        		.account("account1")
        		.type("type1")
        		.bidQuantity(10.0)
        		.build();
 

        
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

    	assertEquals(result.toString(), testBidListDTO1.toString());
	    assertThat(result).usingRecursiveComparison().isEqualTo(testBidListDTO1);
	    assertEquals("account1", result.getAccount());
	    assertEquals(1, result.getBidListId());
    }  

	// ******************************************************************		  

   	
      @DisplayName("ERROR GET EXISTING BIDLIST by ID for non existing BIDLIST data"
      		+ " - Given a non existing BIDLIST,"
      		+ " when GET BIDLIST By ID action request,"
      		+ " then BIDLIST entry should respond"
      		+ " with Data Not Found Exception")
  	@Test
  	public void testGetBidListByIdNonExistingBidListData() throws Exception {

      	// WHEN // THEN
      	assertThrows(DataNotFoundException.class, ()
          		-> bidListService.getBidListById(100));
  	}
      
  	// *******************************************************************	  



}
