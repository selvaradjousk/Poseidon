package com.nnk.springboot.IT.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
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
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nnk.springboot.dto.RatingDTO;

	@DisplayName("INTEGRATION TESTS - Controller < RATING > - UPDATE")
	@AutoConfigureMockMvc
	@SpringBootTest
	@ActiveProfiles("test")
	class RatingControllerPostUpdate_IT {

	    @Autowired
	    private MockMvc mockMvc;


	    private ObjectMapper objectMapper;
		
	    private static RatingDTO testRatingDTO1;


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


	    }

	  	// ********************************************************************

	    @DisplayName(" Url request /rating/update/{id} - Without Authentication"
	    		+ " - Given a Rating,"
	    		+ " when POST /rating/update/{id} action request,"
	    		+ " then returns Error Authentication required")
	    @Test
	    public void testPostRatingValidateWithoutAuthentication() throws Exception {

	        mockMvc.perform(MockMvcRequestBuilders.post("/rating/update/2")
	        	.sessionAttr("ratingDTO", testRatingDTO1))
		        .andExpect(status().is(401))
		        .andDo(MockMvcResultHandlers.print())
		        .andExpect(status().isUnauthorized())
		        .andExpect(status().reason(containsString("Full authentication is required to access this resource")))
		        .andExpect(unauthenticated());

	    }

	    // ********************************************************************

	    @WithMockUser(username = "admin", authorities = { "ADMIN", "USER"})
	    @DisplayName(" Url request /rating/update/{id} - With Authentication"
	    		+ " - Given a Rating,"
	    		+ " when POST /rating/update/{id} action request,"
	    		+ " then returns redirect /rating/update/{id} page")
	    @Test
	    public void testPostRatingValidateWithAuthentication() throws Exception {
       
	        mockMvc.perform(MockMvcRequestBuilders.post("/rating/update/2")
	        .sessionAttr("ratingDTO", testRatingDTO1)
			        .param("id", testRatingDTO1.getId().toString())
			        .param("moodysRating", testRatingDTO1.getMoodysRating())
			        .param("sandPRating", testRatingDTO1.getSandPRating())
			        .param("fitchRating", testRatingDTO1.getFitchRating()))
			        .andExpect(model().hasNoErrors())
			        .andExpect(model().size(0))
			        .andExpect(model().attributeDoesNotExist("ratingDTO"))
			        .andExpect(redirectedUrl("/rating/list"))
			        .andExpect(status().is(302));

	    }

	    // ********************************************************************


	    @WithMockUser(username = "admin", authorities = { "ADMIN", "USER"})
	    @DisplayName(" Url request /rating/update/{id} - Id Negative "
	    		+ " - Given a Rating, Id Negative"
	    		+ " when POST /rating/update/{id} action request,"
	    		+ " then returns error & redirect /rating/add page")
	    @Test
	    public void testPostRatingValidateIdNegative() throws Exception {
	        
	        mockMvc.perform(MockMvcRequestBuilders.post("/rating/update/2")
	        .sessionAttr("ratingDTO", testRatingDTO1)
	        .param("id", "-1")
	        .param("moodysRating", testRatingDTO1.getMoodysRating())
	        .param("sandPRating", testRatingDTO1.getSandPRating())
	        .param("fitchRating", testRatingDTO1.getFitchRating()))
			        .andExpect(model().hasNoErrors())
			        .andExpect(model().size(0))
			        .andExpect(model().attributeDoesNotExist("ratingDTO"))
			        .andExpect(redirectedUrl("/rating/list"))
			        .andExpect(status().is(302));

	    }


	    // ********************************************************************

	    @WithMockUser(username = "admin", authorities = { "ADMIN", "USER"})
	    @DisplayName(" Url request /rating/update/{id} - EmptyMoodysRating "
	    		+ " - Given a Rating - EmptyMoodysRating,"
	    		+ " when POST /rating/update/{id} action request,"
	    		+ " then returns error & redirect /rating/add page")    
	    @Test
	    public void testPostRatingValidateEmptyMoodysRating() throws Exception {
	        
	    	MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/rating/update/2")
	        .sessionAttr("ratingDTO", testRatingDTO1)
	        .param("id", testRatingDTO1.getId().toString())
	        .param("moodysRating", "")
	        .param("sandPRating", testRatingDTO1.getSandPRating())
	        .param("fitchRating", testRatingDTO1.getFitchRating()))
			        .andExpect(model().hasErrors())
			        .andExpect(model().size(2))
			        .andExpect(model().attributeExists("ratingDTO"))
			        .andExpect(view().name("rating/update"))
			        .andExpect(status().is(200))
			        .andReturn();

	        String content = result.getResponse().getContentAsString();
	        
	        assertThat(content).contains("Moodys Rating is mandatory");
	        assertThat(content).contains("Should be alphanumeric and minimum more than 2 characters");
	        
	    }

	    // ********************************************************************


	    @WithMockUser(username = "admin", authorities = { "ADMIN", "USER"})
	    @DisplayName(" Url request /rating/update/{id} - MoodysRating With Symbols "
	    		+ " - Given a Rating - MoodysRating With Symols,"
	    		+ " when POST /rating/update/{id} action request,"
	    		+ " then returns error & redirect /rating/add page")    
	    @Test
	    public void testPostRatingValidateMoodysRatingWithSymbols() throws Exception {
	        
	        
	    	MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/rating/update/2")
			        .sessionAttr("ratingDTO", testRatingDTO1)
			        .param("id", testRatingDTO1.getId().toString())
			        .param("moodysRating", "&&&aaa")
			        .param("sandPRating", testRatingDTO1.getSandPRating())
			        .param("fitchRating", testRatingDTO1.getFitchRating()))
			        .andExpect(model().hasErrors())
			        .andExpect(model().size(2))
			        .andExpect(model().attributeExists("ratingDTO"))
			        .andExpect(view().name("rating/update"))
			        .andExpect(status().is(200))
			        .andReturn();

	        String content = result.getResponse().getContentAsString();
	        
	        assertThat(content).contains("Should be alphanumeric and minimum more than 2 characters");
	    }

	    // ********************************************************************

	    @WithMockUser(username = "admin", authorities = { "ADMIN", "USER"})
	    @DisplayName(" Url request /rating/update/{id} - MoodysRating length > 125 "
	    		+ " - Given a Rating - MoodysRating length > 125,"
	    		+ " when POST /rating/update/{id} action request,"
	    		+ " then returns error & redirect /rating/add page")    
	    @Test
	    public void testPostRatingValidateMoodysRatingWith125MoreCharecters() throws Exception {
	        
	        
	    	MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/rating/update/2")
			        .sessionAttr("ratingDTO", testRatingDTO1)
			        .param("id", testRatingDTO1.getId().toString())
			        .param("moodysRating", "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
			        		+ "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
			        		+ "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa")
			        .param("sandPRating", testRatingDTO1.getSandPRating())
			        .param("fitchRating", testRatingDTO1.getFitchRating()))
			        .andExpect(model().hasErrors())
			        .andExpect(model().size(2))
			        .andExpect(model().attributeExists("ratingDTO"))
			        .andExpect(view().name("rating/update"))
			        .andExpect(status().is(200))
			        .andReturn();

	        String content = result.getResponse().getContentAsString();
	        
	        assertThat(content).contains("The maximum length for moodysRating can be 125 characters");
	    }

	    // ********************************************************************

	    @WithMockUser(username = "admin", authorities = { "ADMIN", "USER"})
		@DisplayName(" Url request /rating/update/{id} - EmptySandPRating "
				+ " - Given a Rating - EmptySandPRating,"
				+ " when POST /rating/update/{id} action request,"
				+ " then returns error & redirect /rating/add page")    
		@Test
		public void testPostRatingValidateEmptySandPRating() throws Exception {

		    
			MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/rating/update/2")
			        .sessionAttr("ratingDTO", testRatingDTO1)
			        .param("id", testRatingDTO1.getId().toString())
			        .param("moodysRating", testRatingDTO1.getMoodysRating())
			        .param("sandPRating", "")
			        .param("fitchRating", testRatingDTO1.getFitchRating()))
			        .andExpect(model().hasErrors())
			        .andExpect(model().size(2))
			        .andExpect(model().attributeExists("ratingDTO"))
			        .andExpect(view().name("rating/update"))
			        .andExpect(status().is(200))
			        .andReturn();

		    String content = result.getResponse().getContentAsString();
		    
		    assertThat(content).contains("SandPRating is mandatory");
		    assertThat(content).contains("Should be alphanumeric and minimum more than 2 characters");
		}

		// ********************************************************************


		@WithMockUser(username = "admin", authorities = { "ADMIN", "USER"})
	    @DisplayName(" Url request /rating/update/{id} - SandPRating With Symbols "
	    		+ " - Given a Rating - SandPRating With Symols,"
	    		+ " when POST /rating/update/{id} action request,"
	    		+ " then returns error & redirect /rating/add page")    
	    @Test
	    public void testPostRatingValidateSandPRatingWithSymbols() throws Exception {

	        
	    	MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/rating/update/2")
			        .sessionAttr("ratingDTO", testRatingDTO1)
			        .param("id", testRatingDTO1.getId().toString())
			        .param("moodysRating", testRatingDTO1.getMoodysRating())
			        .param("sandPRating", "&&&aaa")
			        .param("fitchRating", testRatingDTO1.getFitchRating()))
			        .andExpect(model().hasErrors())
			        .andExpect(model().size(2))
			        .andExpect(model().attributeExists("ratingDTO"))
			        .andExpect(view().name("rating/update"))
			        .andExpect(status().is(200))
			        .andReturn();


	        String content = result.getResponse().getContentAsString();
	        
	        assertThat(content).contains("Should be alphanumeric and minimum more than 2 characters");
	    }

	    // ********************************************************************

	    @WithMockUser(username = "admin", authorities = { "ADMIN", "USER"})
	    @DisplayName(" Url request /rating/update/{id} - SandPRating length > 125 "
	    		+ " - Given a Rating - SandPRating length > 125,"
	    		+ " when POST /rating/update/{id} action request,"
	    		+ " then returns error & redirect /rating/add page")    
	    @Test
	    public void testPostRatingValidateSandPRatingWith125MoreCharecters() throws Exception {
   
	        
	    	MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/rating/update/2")
			        .sessionAttr("ratingDTO", testRatingDTO1)
			        .param("id", testRatingDTO1.getId().toString())
			        .param("moodysRating", testRatingDTO1.getMoodysRating())
			        .param("sandPRating", "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
			        		+ "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
			        		+ "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa")
			        .param("fitchRating", testRatingDTO1.getFitchRating()))
			        .andExpect(model().hasErrors())
			        .andExpect(model().size(2))
			        .andExpect(model().attributeExists("ratingDTO"))
			        .andExpect(view().name("rating/update"))
			        .andExpect(status().is(200))
			        .andReturn();

	        String content = result.getResponse().getContentAsString();
	        
	        assertThat(content).contains("The maximum length for sandPRating can be 125 characters");
	    }

	    // ********************************************************************

	    @WithMockUser(username = "admin", authorities = { "ADMIN", "USER"})
		@DisplayName(" Url request /rating/update/{id} - EmptyFitchRating "
				+ " - Given a Rating - EmptyFitchRating,"
				+ " when POST /rating/update/{id} action request,"
				+ " then returns error & redirect /rating/add page")    
		@Test
		public void testPostRatingValidateEmptyFitchRating() throws Exception {

		    
			MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/rating/update/2")
			        .sessionAttr("ratingDTO", testRatingDTO1)
			        .param("id", testRatingDTO1.getId().toString())
			        .param("moodysRating", testRatingDTO1.getMoodysRating())
			        .param("SandPRating", testRatingDTO1.getSandPRating())
			        .param("fitchRating", ""))
			        .andExpect(model().hasErrors())
			        .andExpect(model().size(2))
			        .andExpect(model().attributeExists("ratingDTO"))
			        .andExpect(view().name("rating/update"))
			        .andExpect(status().is(200))
			        .andReturn();

		    String content = result.getResponse().getContentAsString();
		    
		    assertThat(content).contains("FitchRating is mandatory");
		    assertThat(content).contains("Should be alphanumeric and minimum more than 2 characters");
		}

		// ********************************************************************

		@WithMockUser(username = "admin", authorities = { "ADMIN", "USER"})
	    @DisplayName(" Url request /rating/update/{id} - FitchRating With Symbols "
	    		+ " - Given a Rating - FitchRating With Symols,"
	    		+ " when POST /rating/update/{id} action request,"
	    		+ " then returns error & redirect /rating/add page")    
	    @Test
	    public void testPostRatingValidateFitchRatingWithSymbols() throws Exception {

	        
	    	MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/rating/update/2")
			        .sessionAttr("ratingDTO", testRatingDTO1)
			        .param("id", testRatingDTO1.getId().toString())
			        .param("moodysRating", testRatingDTO1.getMoodysRating())
			        .param("sandPRating", testRatingDTO1.getSandPRating())
			        .param("fitchRating", "&&&aaa"))
			        .andExpect(model().hasErrors())
			        .andExpect(model().size(2))
			        .andExpect(model().attributeExists("ratingDTO"))
			        .andExpect(view().name("rating/update"))
			        .andExpect(status().is(200))
			        .andReturn();


	        String content = result.getResponse().getContentAsString();
	        
	        assertThat(content).contains("Should be alphanumeric and minimum more than 2 characters");
	    }

	    // ********************************************************************

	    @WithMockUser(username = "admin", authorities = { "ADMIN", "USER"})
	    @DisplayName(" Url request /rating/update/{id} - FitchRating length > 125 "
	    		+ " - Given a Rating - FitchRating length > 125,"
	    		+ " when POST /rating/update/{id} action request,"
	    		+ " then returns error & redirect /rating/add page")    
	    @Test
	    public void testPostRatingValidateFitchRatingWith125MoreCharecters() throws Exception {
	        
	        
	    	MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/rating/update/2")
			        .sessionAttr("ratingDTO", testRatingDTO1)
			        .param("id", testRatingDTO1.getId().toString())
			        .param("moodysRating", testRatingDTO1.getMoodysRating())
			        .param("sandPRating", testRatingDTO1.getSandPRating())
			        .param("fitchRating", "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
			        		+ "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
			        		+ "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"))
			        .andExpect(model().hasErrors())
			        .andExpect(model().size(2))
			        .andExpect(model().attributeExists("ratingDTO"))
			        .andExpect(view().name("rating/update"))
			        .andExpect(status().is(200))
			        .andReturn();


	        String content = result.getResponse().getContentAsString();
	        
	        assertThat(content).contains("The maximum length for fitchRating can be 125 characters");
	    }

	    // ********************************************************************

}
