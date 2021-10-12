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
import com.nnk.springboot.dto.RatingDTO;
import com.nnk.springboot.service.RatingService;

@DisplayName("INTEGRATION TESTS - Service ==> Rating Add")
@SpringBootTest
@ActiveProfiles("test")
class RatingServiceAdd_IT {


    @Autowired
    private RatingService ratingService;

    private ObjectMapper objectMapper;
	
    private static RatingDTO ratingToAddDTO;

    
    
    @BeforeEach
    public void setUp() {

    	objectMapper = new ObjectMapper();

    
	        ratingToAddDTO = RatingDTO.builder()
	        		.id(7)
	        		.moodysRating("moodyRating1")
	        		.sandPRating("sandPRating1")
	        		.fitchRating("fitchRating1")
	        		.orderNumber(1)
	        		.build();
    }


	// *******************************************************************	

    
    @DisplayName("Check <NotNull>"
		+ " - Given a new Rating,"
		+ " when ADD RATING action request,"
		+ " then RATING should not be null")	    
    @Test
    public void testAddNewRatingNotNullCheck() {
		

        RatingDTO ratingSaved = ratingService
        		.addRating(ratingToAddDTO);

        assertNotNull(ratingSaved);
    }


	// *******************************************************************			
    
    @Test
    @DisplayName("Check <Validate> match of both same record instance "
    		+ " - Given a new Rating,"
    		+ " when ADD RATING action request,"
    		+ " then RATING added should be added and same as test record")
    public void testAddNewRatingReturnResultMatch() {
   			

   	        RatingDTO ratingSaved = ratingService.addRating(ratingToAddDTO);

   	        assertEquals(ratingToAddDTO.toString(), ratingSaved.toString());
   	        assertThat(ratingSaved).usingRecursiveComparison().isEqualTo(ratingToAddDTO);
   	    }
    

    
   	// *******************************************************************	


}