package com.nnk.springboot.UnitTests.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.dto.RatingDTO;
import com.nnk.springboot.repository.RatingRepository;
import com.nnk.springboot.service.RatingService;
import com.nnk.springboot.util.RatingMapper;

@DisplayName("Rating Service Add Rating- UNIT TESTS")
@ExtendWith(MockitoExtension.class)
class RatingServiceAddTest {


    @InjectMocks
    private RatingService ratingService;

    @Mock
    private RatingRepository ratingRepository;
    
    @Mock
    private RatingMapper ratingMapper;
	
    @Mock
    private ObjectMapper objectMapper;
	
    private static RatingDTO testRatingDTO1, ratingToAddDTO;
    
    private static Rating testRating1, ratingToAdd;
    
    
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
  
    // ***********************************************************************************

    @DisplayName("Test ADD New RATING")
    @Nested
    class TestAddNewRating {  
    	
        @BeforeEach
        public void init() {
        	
 	       ratingToAdd = Rating.builder()
 	        		.id(1)
 	        		.moodysRating("MoodyRating1")
 	        		.sandPRating("MoodyRating1")
 	        		.fitchRating("FitchRating1")
 	        		.orderNumber(1)
 	        		.build();
	        
	        ratingToAddDTO = RatingDTO.builder()
	        		.id(1)
	        		.moodysRating("MoodyRating1")
	        		.sandPRating("MoodyRating1")
	        		.fitchRating("FitchRating1")
	        		.orderNumber(1)
	        		.build();
       
       when(ratingMapper
    		   .toRating(any(RatingDTO.class)))
       .thenReturn(ratingToAdd);
       
       when(ratingRepository
    		   .save(any(Rating.class)))
       .thenReturn(testRating1);
       
       when(ratingMapper
    		   .toRatingDTO(any(Rating.class)))
       .thenReturn(testRatingDTO1);

        }

	// *******************************************************************	
    
    @DisplayName("Check <Execution Order>"
    		+ " - Given a new Rating,"
    		+ " when ADD RATING action request,"
    		+ " then all steps are executed in correct order and number of expected times")    
    @Test
    public void testAddNewRatingExecutionOrderCheck() throws Exception {
		

    	ratingService.addRating(ratingToAddDTO);

        InOrder inOrder = inOrder(ratingMapper, ratingRepository, ratingMapper);
        inOrder.verify(ratingMapper).toRating(any(RatingDTO.class));
        inOrder.verify(ratingRepository).save(any(Rating.class));
        inOrder.verify(ratingMapper).toRatingDTO(any(Rating.class));
        
        verify(ratingMapper, times(1)).toRating(any(RatingDTO.class));
        verify(ratingRepository, times(1)).save(any(Rating.class));
        verify(ratingMapper, times(1)).toRatingDTO(any(Rating.class));
    }
	

    
    }
    
   	// *******************************************************************	


}