package com.nnk.springboot.UnitTests.controller;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

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

@DisplayName("Controller < RATING - GET UPDATE> - UNIT TESTS")
@ExtendWith(SpringExtension.class)
@WebMvcTest(RatingController.class)
class RatingControllerGetUpdateTest {


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
	
    private static RatingDTO testRatingDTO1, testRatingDTO2;
 

    @BeforeEach
    public void setUp() {


    	objectMapper = new ObjectMapper();
        testRatingDTO1 = RatingDTO.builder()
        		.id(1)
        		.moodysRating("MoodyRating1")
        		.sandPRating("SandPRating1")
        		.fitchRating("FitchRating1")
        		.orderNumber(1)
        		.build();
        
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }
    // ********************************************************************


    
    @DisplayName(" Url request /rating/update/{id} - "
    		+ " - Given a Rating,"
    		+ " when GET /rating/update/{id} action request,"
    		+ " then returns rating ADD page")    
    @Test
    public void testGetRatingUpdateById() throws Exception {
    	 when(ratingService.getRatingById(1)).thenReturn(testRatingDTO1);
        mockMvc.perform(MockMvcRequestBuilders.get("/rating/update/1"))
                .andExpect(model().attributeExists("ratingDTO"))
                .andExpect(model().size(1))
                .andExpect(view().name("rating/update"))
                .andExpect(status().isOk());

        verify(ratingService).getRatingById(1);
    }

    // ********************************************************************

    
    
}
