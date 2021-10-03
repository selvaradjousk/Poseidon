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
import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.dto.RatingDTO;
import com.nnk.springboot.exception.DataNotFoundException;
import com.nnk.springboot.repository.RatingRepository;
import com.nnk.springboot.service.RatingService;
import com.nnk.springboot.util.RatingMapper;

@DisplayName("Service ==> Rating DELETE - UNIT TESTS")
@ExtendWith(MockitoExtension.class)
class RatingServiceDeleteTest {

    @InjectMocks
    private RatingService ratingService;

    @Mock
    private RatingRepository ratingRepository;
    
    @Mock
    private RatingMapper ratingMapper;

    @Mock
    private ObjectMapper objectMapper;
	
    private static RatingDTO testRatingDTO1;
    
    private static Rating testRating1;
    
    
    @BeforeEach
    public void setUp() {
        objectMapper = new ObjectMapper();
        testRatingDTO1 = RatingDTO.builder()
        		.id(1)
        		.moodysRating("MoodyRating1")
        		.sandPRating("MoodyRating1")
        		.fitchRating("FitchRating1")
        		.orderNumber(1)
        		.build();
        
       
        testRating1 = Rating.builder()
        		.id(1)
        		.moodysRating("MoodyRating1")
        		.sandPRating("MoodyRating1")
        		.fitchRating("FitchRating1")
        		.orderNumber(1)
        		.build();
        
       
    }

	// *******************************************************************	
    
	@DisplayName("Delete Rating - "
			+ "GIVEN a RATING  "
			+ "WHEN Requested DELETE Rating"
			+ "THEN returns expected rating deleted")	    
    @Test
    public void testDeleteRating() throws Exception {
	
        // GIVEN
		 when(ratingRepository
				 .findById(anyInt()))
		 .thenReturn(java.util.Optional.ofNullable(testRating1));
		 
        // WHEN
		 ratingService.deleteRating(1);
        
        // THEN
	       InOrder inOrder = inOrder(ratingRepository);
	       inOrder.verify(ratingRepository).findById(anyInt());
	       inOrder.verify(ratingRepository).deleteById(anyInt());
	       
	       verify(ratingRepository, times(1)).findById(anyInt());
	       verify(ratingRepository, times(1)).deleteById(anyInt());
    
	}
   

	// *******************************************************************	

	
    @DisplayName("Check <Exception>"
		+ "GIVEN a Rating not exist "
		+ "WHEN Requested DELETE Rating "
		+ "THEN throws Exception")	    
	@Test
	public void testDeleteRatingNotExists() throws Exception {

    	when(ratingRepository
    			.findById(anyInt()))
    	.thenReturn(java.util.Optional.empty());
    
    	// WHEN // THEN
    	assertThrows(DataNotFoundException.class, ()
        		-> ratingService.deleteRating(anyInt()));
	} 

	// *******************************************************************	

}
