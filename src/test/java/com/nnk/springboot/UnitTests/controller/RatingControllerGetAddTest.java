package com.nnk.springboot.UnitTests.controller;

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
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.nnk.springboot.controller.RatingController;
import com.nnk.springboot.service.RatingService;

@DisplayName("Controller < RATING > GET ADD - UNIT TESTS")
@ExtendWith(SpringExtension.class)
@WebMvcTest(RatingController.class)
class RatingControllerGetAddTest {


    @MockBean
    private RatingService ratingService;

    @MockBean
    private UserDetailsService userDetailsService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

	
 
    @BeforeEach
    public void setUp() {

        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }
 
    // ********************************************************************


    
    @DisplayName(" Url request /rating/add - "
    		+ " - Given a Rating,"
    		+ " when GET /rating/add action request,"
    		+ " then returns rating ADD page")    
    @Test
    public void testGetRatingAdd() throws Exception {
//    	 when(ratingService.getAllRating()).thenReturn(ratingDTOList);
        mockMvc.perform(MockMvcRequestBuilders.get("/rating/add"))
                .andExpect(model().attributeExists("ratingDTO"))
                .andExpect(model().size(1))
                .andExpect(view().name("rating/add"))
                .andExpect(status().isOk());

    }

    // ********************************************************************

    
    
}
