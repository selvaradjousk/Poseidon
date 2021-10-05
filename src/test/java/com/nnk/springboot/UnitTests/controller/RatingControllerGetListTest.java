package com.nnk.springboot.UnitTests.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nnk.springboot.controller.RatingController;
import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.dto.RatingDTO;
import com.nnk.springboot.service.RatingService;

@DisplayName("Controller < RATING > GET LIST - UNIT TESTS")
@ExtendWith(SpringExtension.class)
@WebMvcTest(RatingController.class)
class RatingControllerGetListTest {


    @MockBean
    private RatingService ratingService;

    @MockBean
    private UserDetailsService userDetailsService;
    
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    private ObjectMapper objectMapper;
	
    private static RatingDTO testRatingDTO1, testRatingDTO2;
    
   
    private static List<RatingDTO> ratingDTOList;
    

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
        
        ratingDTOList = Arrays.asList(testRatingDTO1, testRatingDTO2);   
        
    
    
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }
    
  	// ********************************************************************

    
    @DisplayName(" Url request /rating/list - "
    		+ " - Given a Rating List,"
    		+ " when GET /rating/list action request,"
    		+ " then returns ratingslist page")
    @Test
    public void testGetRatingList() throws Exception {
        when(ratingService.getAllRating()).thenReturn(ratingDTOList);
        mockMvc.perform(MockMvcRequestBuilders.get("/rating/list"))
                .andExpect(model().attributeExists("ratings"))
                .andExpect(model().size(1))
                .andExpect(view().name("rating/list"))
                .andExpect(status().isOk());

        verify(ratingService, times(1)).getAllRating();
        assertEquals(2, (ratingService.getAllRating()).size());
        assertThat(ratingDTOList).usingRecursiveComparison().isEqualTo(ratingService.getAllRating()).toString();
    }

    // ********************************************************************

}
