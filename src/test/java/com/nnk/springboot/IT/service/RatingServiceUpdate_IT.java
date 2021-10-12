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

@DisplayName("INTEGRATION TESTS - Service ==> Rating Update")
@SpringBootTest
@ActiveProfiles("test")
class RatingServiceUpdate_IT {


    @Autowired
    private RatingService ratingService;

    private ObjectMapper objectMapper;
	
    private static RatingDTO ratingUpdatedDTO;

        @BeforeEach
        public void init() {

	        
	       ratingUpdatedDTO = RatingDTO.builder()
 	        		.id(1)
 	        		.moodysRating("MoodyRating2")
 	        		.sandPRating("MoodyRating2")
 	        		.fitchRating("FitchRating2")
 	        		.orderNumber(2)
 	        		.build();
        

        }

	// *******************************************************************	
 
    
    @DisplayName("Check <NotNull>"
		+ " - Given a new Rating,"
		+ " when UPDATE RATING action request,"
		+ " then RATING should not be null")	    
    @Test
    public void testUpdateExistingRatingNotNullCheck() {
		

        RatingDTO ratingSaved = ratingService
        		.updateRating(1, ratingUpdatedDTO);

        assertNotNull(ratingSaved);
    }

	// *******************************************************************			
    


    @Test
    @DisplayName("Check <Validate> match of both same record instance "
    		+ " - Given a new Rating,"
    		+ " when UPDATE RATING action request,"
    		+ " then RATING updateed should be updateed and same as test record")
    public void testUpdateExistingRatingReturnResultMatch() {
   			

   	        RatingDTO ratingSaved = ratingService
   	        		.updateRating(1, ratingUpdatedDTO);

   	        assertEquals(ratingUpdatedDTO.toString(), ratingSaved.toString());
   	        assertThat(ratingSaved).usingRecursiveComparison().isEqualTo(ratingUpdatedDTO);
   	    }

   	// *******************************************************************	


	
    @DisplayName("ERROR UPDATE RATING for non existing RATING data"
    		+ " - Given a non existing RATING,"
    		+ " when UPDATE RATINGaction request,"
    		+ " then RATING entry should respond"
    		+ " with Data Not Found Exception")
	@Test
	public void testGetRatingByIdNonExistingRatingData() throws Exception {

    
    	// WHEN // THEN
    	assertThrows(DataNotFoundException.class, ()
        		-> ratingService.updateRating(100, ratingUpdatedDTO));
	}
    
	// *******************************************************************	

}