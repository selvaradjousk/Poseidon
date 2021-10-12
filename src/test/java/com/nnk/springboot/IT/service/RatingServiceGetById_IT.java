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
import com.nnk.springboot.dto.RatingDTO;
import com.nnk.springboot.exception.DataNotFoundException;
import com.nnk.springboot.service.RatingService;

@DisplayName("INTEGRATION TESTS - Service ==> Rating GET BY ID")
@SpringBootTest
@ActiveProfiles("test")
class RatingServiceGetById_IT {

    @Autowired
    private RatingService ratingService;

    private ObjectMapper objectMapper;
	
    private static RatingDTO testRatingDTO1;

    
    @BeforeEach
    public void setUp() {

    	objectMapper = new ObjectMapper();
        testRatingDTO1 = RatingDTO.builder()
        		.id(1)
        		.moodysRating("moodys1")
        		.sandPRating("sandp1")
        		.fitchRating("fitch1")
        		.orderNumber(1)
        		.build();


        }


	// *******************************************************************	

    
    @DisplayName("Check <NotNull>"
    		+ " - Given a existing Rating,"
    		+ " when GET RATING By ID action request,"
    		+ " then RATING should not be null")	    
	    @Test
	    public void testRatingByIdNotNullCheck() {
			

    		RatingDTO result = ratingService
    				.getRatingById(1);

	        assertNotNull(result);
	    }

	// ******************************************************************		
	   
    
	 @Test
	 @DisplayName("Check <Validate> match of both same record instance "
	 		+ " - Given a existing Rating,"
	 		+ " when GET RATING By ID action request,"
	 		+ " then RATING ID same as test record")
	 	public void testRatingByIReturnResultMatch() {
				
	 		RatingDTO result = ratingService
	 			.getRatingById(1);
	
	 		assertEquals(testRatingDTO1.toString(), result.toString());
		    assertThat(result).usingRecursiveComparison().isEqualTo(testRatingDTO1);
		    assertEquals("moodys1", result.getMoodysRating());
		    assertEquals(1, result.getId());
	 }
 
	
  	// *******************************************************************	
  	
      @DisplayName("ERROR GET EXISTING RATING by ID for non existing RATING data"
      		+ " - Given a non existing RATING,"
      		+ " when GET RATING By ID action request,"
      		+ " then RATING entry should respond"
      		+ " with Data Not Found Exception")
  	@Test
  	public void testGetRatingByIdNonExistingRatingData() throws Exception {

      
      	// WHEN // THEN
      	assertThrows(DataNotFoundException.class, ()
          		-> ratingService.getRatingById(100));
  	}
      
  	// *******************************************************************	   

}