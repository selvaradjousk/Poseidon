package com.nnk.springboot.IT.controller;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import com.nnk.springboot.service.RatingService;

@DisplayName("INTEGRATION TESTS - Controller < RATING > GET LIST")
@AutoConfigureMockMvc
@SpringBootTest
@ActiveProfiles("test")
class RatingControllerGetList_IT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RatingService ratingService;

    @BeforeEach
    public void setUp() {

    }
    
  	// ********************************************************************


    @DisplayName(" Url request /rating/list - Without Authentication "
    		+ " - Given a Rating List,"
    		+ " when GET /rating/list action request,"
    		+ " then returns Error Authentication required") 
    @Test
    public void testGetRatingListWithoutAuthentication() throws Exception {

        mockMvc.perform(get("/rating/list"))
	        .andExpect(status().is(401))
	        .andDo(MockMvcResultHandlers.print())
	        .andExpect(status().isUnauthorized())
	        .andExpect(status().reason(containsString("Full authentication is required to access this resource")))
	        .andExpect(unauthenticated());

    }

    // ********************************************************************

    @WithMockUser(username="admin",roles={"ADMIN"})
    @DisplayName(" Url request /rating/list - With Authentication"
    		+ " - Given a Rating List,"
    		+ " when GET /rating/list action request,"
    		+ " then returns ratingslist page")
    @Test
    public void testGetRatingListWithAuthenticaiton() throws Exception {

        mockMvc.perform(get("/rating/list"))
                .andExpect(model().attributeExists("ratings"))
                .andExpect(model().size(1))
                .andExpect(view().name("rating/list"))
                .andExpect(status().isOk());


        assertTrue((ratingService.getAllRating()).size() > 1);

    }
    // ********************************************************************

}
