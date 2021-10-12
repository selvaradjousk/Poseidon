package com.nnk.springboot.IT.service;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.nnk.springboot.exception.DataNotFoundException;
import com.nnk.springboot.service.RatingService;

@DisplayName("INTEGRATION TESTS - Service ==> Rating DELETE")
@SpringBootTest
@ActiveProfiles("test")
class RatingServiceDelete_IT {

	@Autowired
    private RatingService ratingService;


	// *******************************************************************	
    
    @DisplayName("Check <Delete Rating>"
		+ "GIVEN a Rating  "
		+ "WHEN Requested DELETE Rating "
		+ "THEN Deletes Rating")	    
	@Test
	public void testRatingDelete() throws Exception {

    	ratingService.deleteRating(3);
    	// WHEN // THEN
    	assertThrows(DataNotFoundException.class, ()
        		-> ratingService.deleteRating(3));
	} 

	// *******************************************************************	
 
	
    @DisplayName("Check <Exception>"
		+ "GIVEN a Rating not exist "
		+ "WHEN Requested DELETE Rating "
		+ "THEN throws Exception")	    
	@Test
	public void testDeleteRatingNotExists() throws Exception {

    	// WHEN // THEN
    	assertThrows(DataNotFoundException.class, ()
        		-> ratingService.deleteRating(100));
	} 

	// *******************************************************************	

}
