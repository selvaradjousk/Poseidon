package com.nnk.springboot.UnitTests.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
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
import com.nnk.springboot.exception.DataNotFoundException;
import com.nnk.springboot.repository.RatingRepository;
import com.nnk.springboot.service.RatingService;
import com.nnk.springboot.util.RatingMapper;

@DisplayName("Rating Service GET BY ID - UNIT TESTS")
@ExtendWith(MockitoExtension.class)
class RatingServiceGetByIdTest {

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
        		.fitchRating("MoodyRating1")
        		.orderNumber(1)
        		.build();
        
       
        testRating1 = Rating.builder()
        		.id(1)
        		.moodysRating("MoodyRating1")
        		.sandPRating("MoodyRating1")
        		.fitchRating("MoodyRating1")
        		.orderNumber(1)
        		.build();
        
       
    }
  
    // ***********************************************************************************

    @DisplayName("Test GET USER BY IDENTITY")
    @Nested
    class TestGetRatingById  {  
    	
        @BeforeEach
        public void init() {
        	
            when(ratingRepository
            		.findById(anyInt()))
            .thenReturn(java.util.Optional.ofNullable(testRating1));
            
            when(ratingMapper
            		.toRatingDTO(any(Rating.class)))
            .thenReturn(testRatingDTO1);  

        }

	// *******************************************************************	
    
    @DisplayName("Check <Execution Order>"
    		+ " - Given a Rating,"
    		+ " when GET RATING BY ID action request,"
    		+ " then all steps are executed in correct order and number of expected times")    
    @Test
    public void testGetRatingByIdExecutionOrderCheck() throws Exception {
		

    	ratingService.getRatingById(1);

        InOrder inOrder = inOrder(ratingRepository, ratingMapper);
        inOrder.verify(ratingRepository).findById(anyInt());
        inOrder.verify(ratingMapper).toRatingDTO(any(Rating.class));
        
        verify(ratingRepository, times(1)).findById(anyInt());
        verify(ratingMapper, times(1)).toRatingDTO(any(Rating.class));
    }

    
	// *******************************************************************	
 
  }

  

}