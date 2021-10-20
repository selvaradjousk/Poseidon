package com.nnk.springboot.IT.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nnk.springboot.controller.BidListController;
import com.nnk.springboot.dto.BidListDTO;

@DisplayName("INTEGRATION TESTS - Controller < BIDLIST > POST UPDATE")
@AutoConfigureMockMvc
@SpringBootTest
@ActiveProfiles("test")
class BidListControllerPostUpdate_IT {


	@Autowired
	BidListController bidListController;

    @Autowired
    private MockMvc mockMvc;


    private ObjectMapper objectMapper;
	
    private static BidListDTO testBidListDTO1;
 

    @BeforeEach
    public void setUp() {

        objectMapper = new ObjectMapper();
        testBidListDTO1 = BidListDTO.builder()
        		.bidListId(1)
        		.account("Account")
        		.type("Type")
        		.bidQuantity(10.0)
        		.build();

        
    }
    
  	// ********************************************************************


    @DisplayName(" Url request /bidList/update/{id} - VALID WithoutAuthentication"
    		+ " - Given a BidList,"
    		+ " when POST /bidList/update/{id} action request,"
    		+ " then returns Error Authentication required")    
    @Test
    public void testPostBidListUpdateWithoutAuthentication() throws Exception {

        
        mockMvc.perform(post("/bidList/update/1"))
    	        .andExpect(status().is(401))
    	        .andDo(MockMvcResultHandlers.print())
    	        .andExpect(status().isUnauthorized())
//    	        .andExpect(status().reason(containsString("Full authentication is required to access this resource")))
    	        .andExpect(unauthenticated());

    }
    
   	// ********************************************************************

    @WithMockUser(username = "admin", authorities = { "ADMIN", "USER"})
     @DisplayName(" Url request /bidList/update/{id} - VALID "
     		+ " - Given a BidList,"
     		+ " when POST /bidList/update/{id} action request,"
     		+ " then returns redirect /bidList/update/{id} page")    
     @Test
     public void testPostBidListUpdateWithAuthentication() throws Exception {

         
         mockMvc.perform(post("/bidList/update/2")
         .sessionAttr("bidListDTO", testBidListDTO1)
         .param("account", testBidListDTO1.getAccount())
         .param("type", testBidListDTO1.getType())
         .param("bidQuantity", testBidListDTO1.getBidQuantity().toString()))
         .andExpect(model().hasNoErrors())
         .andExpect(model().size(0))
         .andExpect(model().attributeDoesNotExist("bidListDTO"))
         .andExpect(redirectedUrl("/bidList/list"))
         .andExpect(status().is(302));

     }

    // ********************************************************************  



    @WithMockUser(username = "admin", authorities = { "ADMIN", "USER"})
    @DisplayName(" Url request /bidList/update/{id} - EmptyAccount "
    		+ " - Given a BidList - EmptyAccount,"
    		+ " when POST /bidList/update/{id} action request,"
    		+ " then returns error & redirect /bidList/update/{id} page")    
    @Test
    public void testPostBidListUpdateEmptyAccount() throws Exception {

    	MvcResult result = mockMvc.perform(post("/bidList/update/1")
        .sessionAttr("bidListDTO", testBidListDTO1)
        .param("account", "")
        .param("type", testBidListDTO1.getType())
        .param("bidQuantity", testBidListDTO1.getBidQuantity().toString()))
        .andExpect(model().hasErrors())
        .andExpect(model().size(2))
        .andExpect(model().attributeExists("bidListDTO"))
        .andExpect(view().name("bidList/update"))
        .andExpect(status().is(200))
        .andReturn();

        String content = result.getResponse().getContentAsString();
        
        assertThat(content).contains("Account is mandatory");
        assertThat(content).contains("Should be alphanumeric and minimum more than 2 characters");
    }

    // ********************************************************************


    @WithMockUser(username = "admin", authorities = { "ADMIN", "USER"})
    @DisplayName(" Url request /bidList/update/{id} - AccountMoreThanThiryCharacters "
    		+ " - Given a BidList - AccountMoreThanThiryCharacters,"
    		+ " when POST /bidList/update/{id} action request,"
    		+ " then returns error & redirect /bidList/update/{id} page")    
    @Test
    public void testPostBidListUpdateWithAccountMoreThanThiryCharacters() throws Exception {

    	MvcResult result = mockMvc.perform(post("/bidList/update/1")
        .sessionAttr("bidListDTO", testBidListDTO1)
        .param("account", "AccountAccountAccountAccountAccount")
        .param("type", testBidListDTO1.getType())
        .param("bidQuantity", testBidListDTO1.getBidQuantity().toString()))
        .andExpect(model().hasErrors())
        .andExpect(model().size(2))
        .andExpect(model().attributeExists("bidListDTO"))
        .andExpect(view().name("bidList/update"))
        .andExpect(status().is(200))
        .andReturn();

        String content = result.getResponse().getContentAsString();
        
//        assertThat(content).contains("Account is mandatory");
        assertThat(content).contains("The maximum length for account should be 30 characters");
    }

    // ********************************************************************

    @WithMockUser(username = "admin", authorities = { "ADMIN", "USER"})
    @DisplayName("Url request /bidList/update/{id} - Account  Non Alphanumeric characters - "
    		+ " - Given a BidList - Account with Non Alphanumeric characters,"
    		+ " when POST /bidList/update/{id} action request,"
    		+ " then returns error & redirect /bidList/update/{id} page")    
    @Test
    public void testPostBidListUpdateWithBidListAccountWithSymbols() throws Exception {

    	MvcResult result = mockMvc.perform(post("/bidList/update/1")
        .sessionAttr("bidListDTO", testBidListDTO1)
        .param("account", "Account!&&&")
        .param("type", testBidListDTO1.getType())
        .param("bidQuantity", testBidListDTO1.getBidQuantity().toString()))
        .andExpect(model().hasErrors())
        .andExpect(model().size(2))
        .andExpect(model().attributeExists("bidListDTO"))
        .andExpect(view().name("bidList/update"))
        .andExpect(status().is(200))
        .andReturn();


        String content = result.getResponse().getContentAsString();
        
        assertThat(content).contains("Should be alphanumeric and minimum more than 2 characters");
    }

    // ********************************************************************


    @WithMockUser(username = "admin", authorities = { "ADMIN", "USER"})
    @DisplayName("Url request /bidList/update/{id} - TypeEmpty - "
    		+ " - Given a BidList - TypeEmpty,"
    		+ " when POST /bidList/update/{id} action request,"
    		+ " then returns error & redirect /bidList/update/{id} page")    
    @Test
    public void testPostBidListUpdateWithTypeEmpty() throws Exception {

    	MvcResult result = mockMvc.perform(post("/bidList/update/1")
        .sessionAttr("bidListDTO", testBidListDTO1)
        .param("account", testBidListDTO1.getAccount())
        .param("type", "")
        .param("bidQuantity", testBidListDTO1.getBidQuantity().toString()))
        .andExpect(model().hasErrors())
        .andExpect(model().size(2))
        .andExpect(model().attributeExists("bidListDTO"))
        .andExpect(view().name("bidList/update"))
        .andExpect(status().is(200))
        .andReturn();


        String content = result.getResponse().getContentAsString();
        
      assertThat(content).contains("Type is mandatory");
      assertThat(content).contains("Should be alphanumeric and minimum more than 2 characters");

    }

    // ********************************************************************

    @WithMockUser(username = "admin", authorities = { "ADMIN", "USER"})
    @DisplayName("Url request /bidList/update/{id} - Type MoreThanThiryCharacters - "
    		+ " - Given a BidList - Type MoreThanThiryCharacters,"
    		+ " when POST /bidList/update/{id} action request,"
    		+ " then returns error & redirect /bidList/update/{id} page")    
    @Test
    public void testPostBidListUpdateWithMoreThanThiryCharacters() throws Exception {

    	MvcResult result = mockMvc.perform(post("/bidList/update/1")
        .sessionAttr("bidListDTO", testBidListDTO1)
        .param("account", testBidListDTO1.getAccount())
        .param("type", "TypeTypeTypeTypeTypeTypeTypeType")
        .param("bidQuantity", testBidListDTO1.getBidQuantity().toString()))
        .andExpect(model().hasErrors())
        .andExpect(model().size(2))
        .andExpect(model().attributeExists("bidListDTO"))
        .andExpect(view().name("bidList/update"))
        .andExpect(status().is(200))
        .andReturn();


        String content = result.getResponse().getContentAsString();
        
      assertThat(content).contains("The maximum length for type should be 30 characters");

    }

    // ********************************************************************

    @WithMockUser(username = "admin", authorities = { "ADMIN", "USER"})
    @DisplayName("Url request /bidList/update/{id} - TypeWithSymbols - "
    		+ " - Given a BidList - TypeWithSymbols-,"
    		+ " when POST /bidList/update/{id} action request,"
    		+ " then returns error & redirect /bidList/update/{id} page")    
    @Test
    public void testPostBidListUpdateTypeWithSymbols() throws Exception {
        
    	MvcResult result = mockMvc.perform(post("/bidList/update/1")
        .sessionAttr("bidListDTO", testBidListDTO1)
        .param("account", testBidListDTO1.getAccount())
        .param("type", "Type!!&&")
        .param("bidQuantity", testBidListDTO1.getBidQuantity().toString()))
        .andExpect(model().hasErrors())
        .andExpect(model().size(2))
        .andExpect(model().attributeExists("bidListDTO"))
        .andExpect(view().name("bidList/update"))
        .andExpect(status().is(200))
        .andReturn();


        String content = result.getResponse().getContentAsString();
        
        assertThat(content).contains("Should be alphanumeric and minimum more than 2 characters");
        
    }

    // ********************************************************************


    @WithMockUser(username = "admin", authorities = { "ADMIN", "USER"})
    @DisplayName("Url request /bidList/update/{id} - BidQuantityNegative - "
    		+ " - Given a BidList - BidQuantityNegative-,"
    		+ " when POST /bidList/update/{id} action request,"
    		+ " then returns error & redirect /bidList/update/{id} page")    
    @Test
    public void testPostBidListUpdateWithBidQuantityNegative() throws Exception {
        
    	MvcResult result = mockMvc.perform(post("/bidList/update/1")
        .sessionAttr("bidListDTO", testBidListDTO1)
        .param("account", testBidListDTO1.getAccount())
        .param("type", testBidListDTO1.getType())
        .param("bidQuantity", "-1000"))
        .andExpect(model().hasErrors())
        .andExpect(model().size(2))
        .andExpect(model().attributeExists("bidListDTO"))
        .andExpect(view().name("bidList/update"))
        .andExpect(status().is(200))
        .andReturn();


        String content = result.getResponse().getContentAsString();
        
        assertThat(content).contains("The bidQuantity must be positive");
              
    }

    // ********************************************************************


    @WithMockUser(username = "admin", authorities = { "ADMIN", "USER"})
    @DisplayName("Url request /bidList/update/{id} - BidQuantityMoreThan10Digits - "
    		+ " - Given a BidList - BidQuantityMoreThan10Digits -,"
    		+ " when POST /bidList/update/{id} action request,"
    		+ " then returns error & redirect /bidList/update/{id} page")    
    @Test
    public void testPostBidListUpdateWithBidQuantityMoreThan10Digits() throws Exception {
        
    	MvcResult result = mockMvc.perform(post("/bidList/update/1")
        .sessionAttr("bidListDTO", testBidListDTO1)
        .param("account", testBidListDTO1.getAccount())
        .param("type", testBidListDTO1.getType())
        .param("bidQuantity", "100000000000000.00"))
        .andExpect(model().hasErrors())
        .andExpect(model().size(2))
        .andExpect(model().attributeExists("bidListDTO"))
        .andExpect(view().name("bidList/update"))
        .andExpect(status().is(200))
        .andReturn();


        String content = result.getResponse().getContentAsString();
        
        assertThat(content).contains("Invalid number input value : Maximum digits allowed are 10 and with 2 decimals fractions");
              
    }

    // ********************************************************************

    @WithMockUser(username = "admin", authorities = { "ADMIN", "USER"})
    @DisplayName("Url request /bidList/update/{id} - BidQuantityWithSymbols - "
    		+ " - Given a BidList - BidQuantityWithSymbols -,"
    		+ " when POST /bidList/update/{id} action request,"
    		+ " then returns error & redirect /bidList/update/{id} page")    
    @Test
    public void testPostBidListUpdateWithBidQuantityWithSymbols() throws Exception {
        
    	MvcResult result = mockMvc.perform(post("/bidList/update/1")
        .sessionAttr("bidListDTO", testBidListDTO1)
        .param("account", testBidListDTO1.getAccount())
        .param("type", testBidListDTO1.getType())
        .param("bidQuantity", "100000&!-."))
        .andExpect(model().hasErrors())
        .andExpect(model().size(2))
        .andExpect(model().attributeExists("bidListDTO"))
        .andExpect(view().name("bidList/update"))
        .andExpect(status().is(200))
        .andReturn();


        String content = result.getResponse().getContentAsString();
        
        assertThat(content).contains("Failed to convert property value of type java.lang.String to required type java.lang.Double for property bidQuantity");
              
    }

    // ********************************************************************




}
