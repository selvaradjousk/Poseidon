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
import com.nnk.springboot.dto.TradeDTO;

@DisplayName("INTEGRATION TESTS - Controller < TRADE > - VALIDATE")
@AutoConfigureMockMvc
@SpringBootTest
@ActiveProfiles("test")
class TradeControllerPostValidate_IT {


    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper;
	
    private static TradeDTO testTradeDTO1;


    @BeforeEach
    public void setUp() {

        objectMapper = new ObjectMapper();
        testTradeDTO1 = TradeDTO.builder()
        		.tradeId(1)
        		.account("Account")
        		.type("Type")
        		.buyQuantity(10.0)
        		.build();
       

    }
  
  	// ********************************************************************

    @DisplayName(" Url request POST /trade/validate - Without Authentication"
    		+ " - Given a Trade,"
    		+ " when POST /trade/validate action request,"
    		+ " then returns Error Authentication required")   
    @Test
    public void testPostTradeValidateWithoutAuthentication() throws Exception {


        mockMvc.perform(MockMvcRequestBuilders.post("/trade/validate")
	        .sessionAttr("tradeDTO", testTradeDTO1))
	        .andExpect(status().is(401))
	        .andDo(MockMvcResultHandlers.print())
	        .andExpect(status().isUnauthorized())
	        .andExpect(status().reason(containsString("Full authentication is required to access this resource")))
	        .andExpect(unauthenticated());
    }

    
  	// ********************************************************************

    @WithMockUser(username = "admin", authorities = { "ADMIN", "USER"})
    @DisplayName(" Url request /trade/validate - With Authentication"
    		+ " - Given a Trade,"
    		+ " when POST /trade/validate action request,"
    		+ " then returns redirect /trade/validate page")    
    @Test
    public void testPostTradeValidateWithAuthentication() throws Exception {


        mockMvc.perform(MockMvcRequestBuilders.post("/trade/validate")
	        .sessionAttr("tradeDTO", testTradeDTO1)
	        .param("account", testTradeDTO1.getAccount())
	        .param("type", testTradeDTO1.getType())
	        .param("buyQuantity", testTradeDTO1.getBuyQuantity().toString()))
	        .andExpect(model().hasNoErrors())
	        .andExpect(model().size(0))
	        .andExpect(model().attributeDoesNotExist("tradeDTO"))
	        .andExpect(redirectedUrl("/trade/list"))
	        .andExpect(status().is(302));

    }

    // ********************************************************************

    @WithMockUser(username = "admin", authorities = { "ADMIN", "USER"})
    @DisplayName(" Url request /trade/validate - EmptyAccount "
    		+ " - Given a Trade - EmptyAccount,"
    		+ " when POST /trade/validate action request,"
    		+ " then returns error & redirect /trade/add page")    
    @Test
    public void testPostTradeValidateEmptyAccount() throws Exception {

    	MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/trade/validate")
	        .sessionAttr("tradeDTO", testTradeDTO1)
	        .param("account", "")
	        .param("type", testTradeDTO1.getType())
	        .param("buyQuantity", testTradeDTO1.getBuyQuantity().toString()))
	        .andExpect(model().hasErrors())
	        .andExpect(model().size(1))
	        .andExpect(model().attributeExists("tradeDTO"))
	        .andExpect(view().name("trade/add"))
	        .andExpect(status().is(200))
	        .andReturn();

        String content = result.getResponse().getContentAsString();
        
        assertThat(content).contains("Account is mandatory");
        assertThat(content).contains("Should be alphanumeric and minimum more than 2 characters");
    }

    // ********************************************************************


    @WithMockUser(username = "admin", authorities = { "ADMIN", "USER"})
    @DisplayName(" Url request /trade/validate - AccountMoreThanThiryCharacters "
    		+ " - Given a Trade - AccountMoreThanThiryCharacters,"
    		+ " when POST /trade/validate action request,"
    		+ " then returns error & redirect /trade/add page")    
    @Test
    public void testPostTradeValidateWithAccountMoreThanThiryCharacters() throws Exception {

    	MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/trade/validate")
	        .sessionAttr("tradeDTO", testTradeDTO1)
	        .param("account", "AccountAccountAccountAccountAccount")
	        .param("type", testTradeDTO1.getType())
	        .param("buyQuantity", testTradeDTO1.getBuyQuantity().toString()))
	        .andExpect(model().hasErrors())
	        .andExpect(model().size(1))
	        .andExpect(model().attributeExists("tradeDTO"))
	        .andExpect(view().name("trade/add"))
	        .andExpect(status().is(200))
	        .andReturn();

        String content = result.getResponse().getContentAsString();
        
//        assertThat(content).contains("Account is mandatory");
        assertThat(content).contains("The maximum length for account should be 30 characters");
    }

    // ********************************************************************


    @WithMockUser(username = "admin", authorities = { "ADMIN", "USER"})
    @DisplayName("Url request /trade/validate - Account  Non Alphanumeric characters - "
    		+ " - Given a Trade - Account with Non Alphanumeric characters,"
    		+ " when POST /trade/validate action request,"
    		+ " then returns error & redirect /trade/add page")    
    @Test
    public void testPostTradeValidateWithTradeAccountWithSymbols() throws Exception {

    	MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/trade/validate")
	        .sessionAttr("tradeDTO", testTradeDTO1)
	        .param("account", "Account!&&&")
	        .param("type", testTradeDTO1.getType())
	        .param("buyQuantity", testTradeDTO1.getBuyQuantity().toString()))
	        .andExpect(model().hasErrors())
	        .andExpect(model().size(1))
	        .andExpect(model().attributeExists("tradeDTO"))
	        .andExpect(view().name("trade/add"))
	        .andExpect(status().is(200))
	        .andReturn();


        String content = result.getResponse().getContentAsString();
        
        assertThat(content).contains("Should be alphanumeric and minimum more than 2 characters");
    }

    // ********************************************************************

    @WithMockUser(username = "admin", authorities = { "ADMIN", "USER"})
    @DisplayName("Url request /trade/validate - TypeEmpty - "
    		+ " - Given a Trade - TypeEmpty,"
    		+ " when POST /trade/validate action request,"
    		+ " then returns error & redirect /trade/add page")    
    @Test
    public void testPostTradeValidateWithTypeEmpty() throws Exception {

    	MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/trade/validate")
	        .sessionAttr("tradeDTO", testTradeDTO1)
	        .param("account", testTradeDTO1.getAccount())
	        .param("type", "")
	        .param("buyQuantity", testTradeDTO1.getBuyQuantity().toString()))
	        .andExpect(model().hasErrors())
	        .andExpect(model().size(1))
	        .andExpect(model().attributeExists("tradeDTO"))
	        .andExpect(view().name("trade/add"))
	        .andExpect(status().is(200))
	        .andReturn();

        String content = result.getResponse().getContentAsString();
        
      assertThat(content).contains("Type is mandatory");
      assertThat(content).contains("Should be alphanumeric and minimum more than 2 characters");

    }

    // ********************************************************************


    @WithMockUser(username = "admin", authorities = { "ADMIN", "USER"})
    @DisplayName("Url request /trade/validate - Type MoreThanThiryCharacters - "
    		+ " - Given a Trade - Type MoreThanThiryCharacters,"
    		+ " when POST /trade/validate action request,"
    		+ " then returns error & redirect /trade/add page")    
    @Test
    public void testPostTradeValidateWithMoreThanThiryCharacters() throws Exception {

    	MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/trade/validate")
	        .sessionAttr("tradeDTO", testTradeDTO1)
	        .param("account", testTradeDTO1.getAccount())
	        .param("type", "TypeTypeTypeTypeTypeTypeTypeType")
	        .param("buyQuantity", testTradeDTO1.getBuyQuantity().toString()))
	        .andExpect(model().hasErrors())
	        .andExpect(model().size(1))
	        .andExpect(model().attributeExists("tradeDTO"))
	        .andExpect(view().name("trade/add"))
	        .andExpect(status().is(200))
	        .andReturn();

        String content = result.getResponse().getContentAsString();
        
      assertThat(content).contains("The maximum length for type should be 30 characters");

    }

    // ********************************************************************


    @WithMockUser(username = "admin", authorities = { "ADMIN", "USER"})
    @DisplayName("Url request /trade/validate - TypeWithSymbols - "
    		+ " - Given a Trade - TypeWithSymbols-,"
    		+ " when POST /trade/validate action request,"
    		+ " then returns error & redirect /trade/add page")    
    @Test
    public void testPostTradeValidateTypeWithSymbols() throws Exception {

    	MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/trade/validate")
	        .sessionAttr("tradeDTO", testTradeDTO1)
	        .param("account", testTradeDTO1.getAccount())
	        .param("type", "Type!!&&")
	        .param("buyQuantity", testTradeDTO1.getBuyQuantity().toString()))
	        .andExpect(model().hasErrors())
	        .andExpect(model().size(1))
	        .andExpect(model().attributeExists("tradeDTO"))
	        .andExpect(view().name("trade/add"))
	        .andExpect(status().is(200))
	        .andReturn();

        String content = result.getResponse().getContentAsString();
        
        assertThat(content).contains("Should be alphanumeric and minimum more than 2 characters");
        
    }

    // ********************************************************************

    @WithMockUser(username = "admin", authorities = { "ADMIN", "USER"})
    @DisplayName("Url request /trade/validate - BuyQuantityNegative - "
    		+ " - Given a Trade - BuyQuantityNegative-,"
    		+ " when POST /trade/validate action request,"
    		+ " then returns error & redirect /trade/add page")    
    @Test
    public void testPostTradeValidateWithBuyQuantityNegative() throws Exception {

    	MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/trade/validate")
	        .sessionAttr("tradeDTO", testTradeDTO1)
	        .param("account", testTradeDTO1.getAccount())
	        .param("type", testTradeDTO1.getType())
	        .param("buyQuantity", "-1000"))
	        .andExpect(model().hasErrors())
	        .andExpect(model().size(1))
	        .andExpect(model().attributeExists("tradeDTO"))
	        .andExpect(view().name("trade/add"))
	        .andExpect(status().is(200))
	        .andReturn();

        String content = result.getResponse().getContentAsString();
        
        assertThat(content).contains("The value must be positive");
              
    }

    // ********************************************************************


    @WithMockUser(username = "admin", authorities = { "ADMIN", "USER"})
    @DisplayName("Url request /trade/validate - BuyQuantityMoreThan10Digits - "
    		+ " - Given a Trade - BuyQuantityMoreThan10Digits -,"
    		+ " when POST /trade/validate action request,"
    		+ " then returns error & redirect /trade/add page")    
    @Test
    public void testPostTradeValidateWithBuyQuantityMoreThan10Digits() throws Exception {

    	MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/trade/validate")
	        .sessionAttr("tradeDTO", testTradeDTO1)
	        .param("account", testTradeDTO1.getAccount())
	        .param("type", testTradeDTO1.getType())
	        .param("buyQuantity", "100000000000000.00"))
	        .andExpect(model().hasErrors())
	        .andExpect(model().size(1))
	        .andExpect(model().attributeExists("tradeDTO"))
	        .andExpect(view().name("trade/add"))
	        .andExpect(status().is(200))
	        .andReturn();

        String content = result.getResponse().getContentAsString();
        
        assertThat(content).contains("Invalid number input value : Maximum digits allowed are 10 and with 2 decimals fractions");
              
    }

    // ********************************************************************


    @WithMockUser(username = "admin", authorities = { "ADMIN", "USER"})
    @DisplayName("Url request /trade/validate - BuyQuantityWithSymbols - "
    		+ " - Given a Trade - BuyQuantityWithSymbols -,"
    		+ " when POST /trade/validate action request,"
    		+ " then returns error & redirect /trade/add page")    
    @Test
    public void testPostTradeValidateWithBuyQuantityWithSymbols() throws Exception {

    	MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/trade/validate")
	        .sessionAttr("tradeDTO", testTradeDTO1)
	        .param("account", testTradeDTO1.getAccount())
	        .param("type", testTradeDTO1.getType())
	        .param("buyQuantity", "100000&!-."))
	        .andExpect(model().hasErrors())
	        .andExpect(model().size(1))
	        .andExpect(model().attributeExists("tradeDTO"))
	        .andExpect(view().name("trade/add"))
	        .andExpect(status().is(200))
	        .andReturn();

        String content = result.getResponse().getContentAsString();
        
        assertThat(content).contains("Failed to convert property value of type java.lang.String to required type java.lang.Double for property buyQuantity");
              
    }

    // ********************************************************************

        
    
}
