package com.nnk.springboot.UnitTests.controller;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nnk.springboot.config.AuthTokenFilter;
import com.nnk.springboot.config.JwtUtils;
import com.nnk.springboot.config.MyUserDetailsService;
import com.nnk.springboot.controller.RatingController;
import com.nnk.springboot.dto.RatingDTO;
import com.nnk.springboot.service.RatingService;

@DisplayName("Controller < RATING > DELETE - UNIT TESTS")
@ExtendWith(SpringExtension.class)
@WebMvcTest(RatingController.class)
class RatingControllerGetDeleteTest {


	@MockBean
	AuthTokenFilter authenticationJwtTokenFilter;
	
    @MockBean
    private RatingService ratingService;

    @MockBean
    private MyUserDetailsService userDetailsService;

    @MockBean
    private JwtUtils jwtUtils;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;


    private ObjectMapper objectMapper;
	
    private static RatingDTO testRatingDTO1;
    


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
   
    
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }
    
  	// ********************************************************************

    
    @DisplayName(" Url request rating/delete/{id} valid id- "
    		+ " - Given a rating/delete/{id} valid id, "
    		+ " when GET rating/delete action request,"
    		+ " then returns delete page")    
    @Test
    public void testGetRatingDelete() throws Exception {
    	when(ratingService.getRatingById(1)).thenReturn(testRatingDTO1);
    	mockMvc.perform(MockMvcRequestBuilders.get("/rating/delete/1"))
    				.andExpect(redirectedUrl("/rating/list"))
            		.andExpect(status().isFound())
            		.andExpect(model().hasNoErrors())
            		.andExpect(status().is(302));

        verify(ratingService, times(1)).deleteRating(1);
       
    }

    // ********************************************************************

    
    @DisplayName(" Url request rating/delete/{id} invalid id- "
    		+ " - Given a Rating rating/delete/{id} invalid id,"
    		+ " when GET rating/delete action request,"
    		+ " then returns delete page")    
    @Test
    public void testGetRatingDeleteNull() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/rating/delete/"))
        		.andExpect(redirectedUrl(null))
                .andExpect(status().is(404))
        		.andExpect(status().isNotFound());

        verify(ratingService, times(0)).deleteRating(1);
    }

    // ********************************************************************
   
   


}
