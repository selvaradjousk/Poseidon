package com.nnk.springboot.UnitTests.controller;

import static org.assertj.core.api.Assertions.assertThat;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
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
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nnk.springboot.controller.RatingController;
import com.nnk.springboot.dto.RatingDTO;
import com.nnk.springboot.service.RatingService;

	@DisplayName("Controller < RATING > - UPDATE UNIT TESTS")
	@ExtendWith(SpringExtension.class)
	@WebMvcTest(RatingController.class)
	class RatingControllerPostUpdateTest {


	    @MockBean
	    private RatingService ratingService;

	    @MockBean
	    private UserDetailsService userDetailsService;

	    @Autowired
	    private MockMvc mockMvc;

	    @Autowired
	    private WebApplicationContext context;

	    private ObjectMapper objectMapper;
		
	    private static RatingDTO testRatingDTO1, testRatingDTO2, ratingUpdateDTO;
	 
	    private static List<RatingDTO> ratingDTOList;

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
	        
	        testRatingDTO2 = RatingDTO.builder()
	        		.id(2)
	        		.moodysRating("MoodyRating2")
	        		.sandPRating("SandPRating2")
	        		.fitchRating("FitchRating2")
	        		.orderNumber(2)
	        		.build();
	        
	        ratingDTOList = Arrays.asList(testRatingDTO1, testRatingDTO2); 

	        ratingUpdateDTO = RatingDTO.builder()
	        		.id(2)
	        		.moodysRating("MoodyRating2")
	        		.sandPRating("SandPRating2")
	        		.fitchRating("FitchRating2")
	        		.orderNumber(2)
	        		.build();
	        
	        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
	    }

	  	// ********************************************************************


	    @DisplayName(" Url request /rating/update/{id} - VALID"
	    		+ " - Given a Rating,"
	    		+ " when POST /rating/update/{id} action request,"
	    		+ " then returns redirect /rating/update/{id} page")    
	    @Test
	    public void testPostRatingValidate() throws Exception {

	    	when(ratingService
	    			.updateRating(anyInt(), any(RatingDTO.class)))
	    	.thenReturn(testRatingDTO1);
	        
	        mockMvc.perform(MockMvcRequestBuilders.post("/rating/update/1")
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

	        verify(ratingService, times(1)).updateRating(anyInt(), any(RatingDTO.class));
	    }

	    // ********************************************************************



	    @DisplayName(" Url request /rating/update/{id} - Id Negative "
	    		+ " - Given a Rating, Id Negative"
	    		+ " when POST /rating/update/{id} action request,"
	    		+ " then returns error & redirect /rating/add page")    
	    @Test
	    public void testPostRatingValidateIdNegative() throws Exception {

	    	when(ratingService
	    			.updateRating(anyInt(), any(RatingDTO.class)))
	    	.thenReturn(testRatingDTO1);
	        
	        mockMvc.perform(MockMvcRequestBuilders.post("/rating/update/1")
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

	        verify(ratingService, times(1)).updateRating(anyInt(), any(RatingDTO.class));
	    }


	    // ********************************************************************


	    @DisplayName(" Url request /rating/update/{id} - EmptyMoodysRating "
	    		+ " - Given a Rating - EmptyMoodysRating,"
	    		+ " when POST /rating/update/{id} action request,"
	    		+ " then returns error & redirect /rating/add page")    
	    @Test
	    public void testPostRatingValidateEmptyMoodysRating() throws Exception {

	    	when(ratingService
	    			.updateRating(anyInt(), any(RatingDTO.class)))
	    	.thenReturn(testRatingDTO1);
	        
	    	MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/rating/update/1")
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

	        verify(ratingService, times(0)).updateRating(anyInt(), any(RatingDTO.class));
	        
	        String content = result.getResponse().getContentAsString();
	        
	        assertThat(content).contains("Moodys Rating is mandatory");
	        assertThat(content).contains("Should be alphanumeric and minimum more than 2 characters");
	        
	    }

	    // ********************************************************************



	    @DisplayName(" Url request /rating/update/{id} - MoodysRating With Symbols "
	    		+ " - Given a Rating - MoodysRating With Symols,"
	    		+ " when POST /rating/update/{id} action request,"
	    		+ " then returns error & redirect /rating/add page")    
	    @Test
	    public void testPostRatingValidateMoodysRatingWithSymbols() throws Exception {

	    	when(ratingService.getAllRating()).thenReturn(ratingDTOList);

	    	when(ratingService
	    			.updateRating(anyInt(), any(RatingDTO.class)))
	    	.thenReturn(testRatingDTO1);
	        
	        
	    	MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/rating/update/1")
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

	    	
	        verify(ratingService, times(0)).getAllRating();
	        verify(ratingService, times(0)).updateRating(anyInt(), any(RatingDTO.class));

	        String content = result.getResponse().getContentAsString();
	        
	        assertThat(content).contains("Should be alphanumeric and minimum more than 2 characters");
	    }

	    // ********************************************************************

	    @DisplayName(" Url request /rating/update/{id} - MoodysRating length > 125 "
	    		+ " - Given a Rating - MoodysRating length > 125,"
	    		+ " when POST /rating/update/{id} action request,"
	    		+ " then returns error & redirect /rating/add page")    
	    @Test
	    public void testPostRatingValidateMoodysRatingWith125MoreCharecters() throws Exception {

	    	when(ratingService.getAllRating()).thenReturn(ratingDTOList);


	    	when(ratingService
	    			.updateRating(anyInt(), any(RatingDTO.class)))
	    	.thenReturn(testRatingDTO1);
	        
	        
	    	MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/rating/update/1")
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


	        verify(ratingService, times(0)).getAllRating();
	        verify(ratingService, times(0)).updateRating(anyInt(), any(RatingDTO.class));

	        String content = result.getResponse().getContentAsString();
	        
	        assertThat(content).contains("The maximum length for moodysRating can be 125 characters");
	    }

	    // ********************************************************************


		@DisplayName(" Url request /rating/update/{id} - EmptySandPRating "
				+ " - Given a Rating - EmptySandPRating,"
				+ " when POST /rating/update/{id} action request,"
				+ " then returns error & redirect /rating/add page")    
		@Test
		public void testPostRatingValidateEmptySandPRating() throws Exception {

			when(ratingService.getAllRating()).thenReturn(ratingDTOList);

	    	when(ratingService
	    			.updateRating(anyInt(), any(RatingDTO.class)))
	    	.thenReturn(testRatingDTO1);
	        
		    
			MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/rating/update/1")
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

		
		    verify(ratingService, times(0)).getAllRating();
		    verify(ratingService, times(0)).updateRating(anyInt(), any(RatingDTO.class));
		
		    String content = result.getResponse().getContentAsString();
		    
		    assertThat(content).contains("SandPRating is mandatory");
		    assertThat(content).contains("Should be alphanumeric and minimum more than 2 characters");
		}

		// ********************************************************************



	    @DisplayName(" Url request /rating/update/{id} - SandPRating With Symbols "
	    		+ " - Given a Rating - SandPRating With Symols,"
	    		+ " when POST /rating/update/{id} action request,"
	    		+ " then returns error & redirect /rating/add page")    
	    @Test
	    public void testPostRatingValidateSandPRatingWithSymbols() throws Exception {

	    	when(ratingService.getAllRating()).thenReturn(ratingDTOList);

	    	when(ratingService
	    			.updateRating(anyInt(), any(RatingDTO.class)))
	    	.thenReturn(testRatingDTO1);
	        
	        
	    	MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/rating/update/1")
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


	        verify(ratingService, times(0)).getAllRating();
	        verify(ratingService, times(0)).updateRating(anyInt(), any(RatingDTO.class));

	        String content = result.getResponse().getContentAsString();
	        
	        assertThat(content).contains("Should be alphanumeric and minimum more than 2 characters");
	    }

	    // ********************************************************************

}
