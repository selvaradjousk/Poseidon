package com.nnk.springboot.UnitTests.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

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

@DisplayName("Rating Service GetRating List - UNIT TESTS")
@ExtendWith(MockitoExtension.class)
class RatingServiceGetListTest {

    @InjectMocks
    private RatingService RatingService;

    @Mock
    private RatingRepository RatingRepository;
    
    @Mock
    private RatingMapper RatingMapper;
    
    private ObjectMapper objectMapper;
	
    private static RatingDTO testRatingDTO1, testRatingDTO2;
    
    private static Rating testRating1, testRating2;
    
    private static List<RatingDTO> RatingDTOList;
    
    private static List<Rating> RatingList;

    
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
        
        testRatingDTO2 = RatingDTO.builder()
        		.id(2)
        		.moodysRating("MoodyRating2")
        		.sandPRating("MoodyRating2")
        		.fitchRating("MoodyRating2")
        		.orderNumber(2)
        		.build();
        
        RatingDTOList = Arrays.asList(testRatingDTO1, testRatingDTO2);   
        
        testRating1 = Rating.builder()
        		.id(1)
        		.moodysRating("MoodyRating1")
        		.sandPRating("MoodyRating1")
        		.fitchRating("MoodyRating1")
        		.orderNumber(1)
        		.build();
        
        testRating2 = Rating.builder()
        		.id(2)
        		.moodysRating("MoodyRating2")
        		.sandPRating("MoodyRating2")
        		.fitchRating("MoodyRating2")
        		.orderNumber(2)
        		.build();
        
        RatingList = Arrays.asList(testRating1, testRating2);   
        
    }
    


   	// *******************************************************************	
    @DisplayName("Test List Ratings")
    @Nested
    class TestGetListRating {  
    	
        @BeforeEach
        public void init() {
        	
            when(RatingRepository
            		.findAll())
            .thenReturn(RatingList);
            
            when(RatingMapper
            		.toRatingDTO(testRating1))
            .thenReturn(testRatingDTO1);
            
            when(RatingMapper
            		.toRatingDTO(testRating2))
            .thenReturn(testRatingDTO2);
            
        }
        
        
    	// *******************************************************************	
        
        @DisplayName("Check <Execution Order>"
        		+ " - Given a Rating List,"
        		+ " when Get Rating List action request,"
        		+ " then all steps are executed in correct order and number of expected times")    
        @Test
        public void testGetRatingsListExecutionOrderCheck() throws Exception {
   	
           // GIVEN

           // WHEN
           RatingService.getAllRating();
           
           // THEN
           InOrder inOrder = inOrder(RatingRepository, RatingMapper);
           inOrder.verify(RatingRepository).findAll();
           inOrder.verify(RatingMapper).toRatingDTO(testRating1);
           inOrder.verify(RatingMapper).toRatingDTO(testRating2);
           
           verify(RatingRepository, times(1)).findAll();
           verify(RatingMapper, times(1)).toRatingDTO(testRating1);
           verify(RatingMapper, times(1)).toRatingDTO(testRating2);
       
        }
   	
        
      	// *******************************************************************	

          
          @DisplayName("Check Check <NotNull>"
          		+ " - Given a Rating List,"
          		+ " when Get Rating List action request,"
          		+ " then returns Ratingslist not null")    
          @Test
          public void testGetRatingsListNotNullCheck() throws Exception {
     	
             // GIVEN

             // WHEN
             List<RatingDTO> result = RatingService.getAllRating();
             
             // THEN
             assertNotNull(result);
         
          }
          
  
        // *******************************************************************	          
   
               
    } 
   
}
