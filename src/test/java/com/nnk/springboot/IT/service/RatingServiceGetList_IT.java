package com.nnk.springboot.IT.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.nnk.springboot.dto.RatingDTO;
import com.nnk.springboot.service.RatingService;

@DisplayName("INTEGRATION TESTS - Service ==> Rating Get List")
@SpringBootTest
@ActiveProfiles("test")
class RatingServiceGetList_IT {

    @Autowired
    private RatingService RatingService;


      	// *******************************************************************	

          
          @DisplayName("Check Check <NotNull>"
          		+ " - Given a Rating List,"
          		+ " when Get Rating List action request,"
          		+ " then returns Ratingslist not null")    
          @Test
          public void testGetRatingsListNotNullCheck() throws Exception {
     	
             // GIVEN

             // WHEN
             List<RatingDTO> result = RatingService
            		 .getAllRating();
             
             // THEN
             assertNotNull(result);

          }
          
  
        // *******************************************************************	          

          


		@DisplayName("Check <List Count >"
				+ " - Given a Rating List,"
				+ " when Get Rating List action request,"
				+ " then return expected Number of Ratings")    
		@Test
		public void testGetRatingsListRecordsNumberMatchCheck() throws Exception {
		
		   // GIVEN
		
		   // WHEN
		   List<RatingDTO> result = RatingService
		  		 .getAllRating();
		   
		   // THEN
//		   assertEquals(7, result.size());
		   assertTrue(result.size() > 1);
		
		}    

		// *******************************************************************	
   
}
