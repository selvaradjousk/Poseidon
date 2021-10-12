package com.nnk.springboot.IT.service;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.nnk.springboot.exception.DataNotFoundException;
import com.nnk.springboot.service.BidListService;

@DisplayName("INTEGRATION TESTS - Service ==>  BidList DELETE")
@SpringBootTest
@ActiveProfiles("test")
class BidListServiceDelete_IT {


    @Autowired
    private BidListService bidListService;

    @BeforeEach
    public void setUp() {

    }
    
    
	// *******************************************************************	
    
    @DisplayName("Check <Delete BidList>"
		+ "GIVEN a BidList  "
		+ "WHEN Requested DELETE BidList "
		+ "THEN Deletes BidList")	    
	@Test
	public void testDeleteBidLis() throws Exception {

    	bidListService.deleteBidList(3);
    	// WHEN // THEN
    	assertThrows(DataNotFoundException.class, ()
        		-> bidListService.deleteBidList(3));
	} 

	// *******************************************************************	
 
    @DisplayName("Check <Exception>"
		+ "GIVEN a BidList not exist "
		+ "WHEN Requested DELETE BidList "
		+ "THEN throws Exception")	    
	@Test
	public void testDeleteBidListNotExists() throws Exception {

    	// WHEN // THEN
    	assertThrows(DataNotFoundException.class, ()
        		-> bidListService.deleteBidList(20));
	} 

	// *******************************************************************	

}
