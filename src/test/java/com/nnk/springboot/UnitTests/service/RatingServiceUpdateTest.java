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

@DisplayName("Rating Service Update Rating- UNIT TESTS")
@ExtendWith(MockitoExtension.class)
class RatingServiceUpdateTest {


    @InjectMocks
    private RatingService ratingService;

    @Mock
    private RatingRepository ratingRepository;
    
    @Mock
    private RatingMapper ratingMapper;
	
    @Mock
    private ObjectMapper objectMapper;
	
    private static RatingDTO testRatingDTO1, ratingToUpdateDTO, ratingUpdatedDTO;
    
    private static Rating testRating1, ratingToUpdate, ratingUpdated;
    
    
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

    @DisplayName("Test UPDATE Existing RATING")
    @Nested
    class TestUpdateExistingRating {  
    	
        @BeforeEach
        public void init() {
        	
 	       ratingToUpdate = Rating.builder()
	        		.id(1)
	        		.moodysRating("MoodyRating1")
	        		.sandPRating("MoodyRating1")
	        		.fitchRating("FitchRating1")
	        		.orderNumber(1)
	        		.build();
 	       
	       ratingUpdated = Rating.builder()
	        		.id(2)
	        		.moodysRating("MoodyRating2")
	        		.sandPRating("MoodyRating2")
	        		.fitchRating("FitchRating2")
	        		.orderNumber(2)
	        		.build();
	        
	        ratingToUpdateDTO = RatingDTO.builder()
 	        		.id(1)
 	        		.moodysRating("MoodyRating1")
 	        		.sandPRating("MoodyRating1")
 	        		.fitchRating("FitchRating1")
 	        		.orderNumber(1)
 	        		.build();
	        
		       ratingUpdatedDTO = RatingDTO.builder()
	 	        		.id(2)
	 	        		.moodysRating("MoodyRating2")
	 	        		.sandPRating("MoodyRating2")
	 	        		.fitchRating("FitchRating2")
	 	        		.orderNumber(2)
	 	        		.build();
	        
       when(ratingRepository
    		   .findById(anyInt()))
       .thenReturn(java.util.Optional.ofNullable(testRating1));
	               
       when(ratingMapper
    		   .toRating(any(RatingDTO.class)))
       .thenReturn(ratingToUpdate);
       
       when(ratingRepository
    		   .save(any(Rating.class)))
       .thenReturn(ratingUpdated);
       
       when(ratingMapper
    		   .toRatingDTO(any(Rating.class)))
       .thenReturn(ratingUpdatedDTO);

        }

	// *******************************************************************	
    
    @DisplayName("Check <Execution Order>"
    		+ " - Given a existing Rating,"
    		+ " when UPDATE RATING action request,"
    		+ " then all steps are executed in correct order and number of expected times")    
    @Test
    public void testUpdateExistingRatingExecutionOrderCheck() throws Exception {
		

    	ratingService.updateRating(2, ratingUpdatedDTO);

        InOrder inOrder = inOrder(ratingRepository, ratingMapper, ratingMapper);
        inOrder.verify(ratingRepository).findById(anyInt());
        inOrder.verify(ratingMapper).toRating(any(RatingDTO.class));
        inOrder.verify(ratingRepository).save(any(Rating.class));
        inOrder.verify(ratingMapper).toRatingDTO(any(Rating.class));
        
        verify(ratingRepository, times(1)).findById(anyInt());
        verify(ratingMapper, times(1)).toRating(any(RatingDTO.class));
        verify(ratingRepository, times(1)).save(any(Rating.class));
        verify(ratingMapper, times(1)).toRatingDTO(any(Rating.class));
        
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
    
  }


	
    @DisplayName("ERROR UPDATE RATING for non existing RATING data"
    		+ " - Given a non existing RATING,"
    		+ " when UPDATE RATINGaction request,"
    		+ " then RATING entry should respond"
    		+ " with Data Not Found Exception")
	@Test
	public void testGetRatingByIdNonExistingRatingData() throws Exception {

    	when(ratingRepository
    			.findById(anyInt()))
    	.thenReturn(java.util.Optional.empty());
    
    	// WHEN // THEN
    	assertThrows(DataNotFoundException.class, ()
        		-> ratingService.updateRating(5, ratingUpdatedDTO));
	}
    
	// *******************************************************************	

}