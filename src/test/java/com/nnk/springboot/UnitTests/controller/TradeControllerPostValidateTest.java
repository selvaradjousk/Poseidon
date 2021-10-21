package com.nnk.springboot.UnitTests.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
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
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nnk.springboot.config.AuthTokenFilter;
import com.nnk.springboot.config.MyUserDetailsService;
import com.nnk.springboot.controller.TradeController;
import com.nnk.springboot.dto.TradeDTO;
import com.nnk.springboot.service.TradeService;

@DisplayName("Controller < TRADE > -VALIDATE UNIT TESTS")
@ExtendWith(SpringExtension.class)
@WebMvcTest(TradeController.class)
class TradeControllerPostValidateTest {


	@MockBean
	AuthTokenFilter authenticationJwtTokenFilter;
	
    @MockBean
    private TradeService tradeService;

    @MockBean
    private MyUserDetailsService userDetailsService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    private ObjectMapper objectMapper;
	
    private static TradeDTO testTradeDTO1, testTradeDTO2;
 
    private static List<TradeDTO> tradeDTOList;

    @BeforeEach
    public void setUp() {

        objectMapper = new ObjectMapper();
        testTradeDTO1 = TradeDTO.builder()
        		.tradeId(1)
        		.account("Account")
        		.type("Type")
        		.buyQuantity(10.0)
        		.build();
        
        testTradeDTO2 = TradeDTO.builder()
        		.tradeId(2)
        		.account("Account")
        		.type("Type")
        		.buyQuantity(10.0)
        		.build();
        
        tradeDTOList = Arrays.asList(testTradeDTO1, testTradeDTO2); 
        
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }
    
  	// ********************************************************************


    @DisplayName(" Url request /trade/validate - "
    		+ " - Given a Trade,"
    		+ " when POST /trade/validate action request,"
    		+ " then returns redirect /trade/validate page")    
    @Test
    public void testPostTradeValidate() throws Exception {

    	when(tradeService
    			.addTrade(any(TradeDTO.class)))
    	.thenReturn(any(TradeDTO.class));
        
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

        verify(tradeService, times(1)).addTrade(any(TradeDTO.class));
    }

    // ********************************************************************


    @DisplayName(" Url request /trade/validate - EmptyAccount "
    		+ " - Given a Trade - EmptyAccount,"
    		+ " when POST /trade/validate action request,"
    		+ " then returns error & redirect /trade/add page")    
    @Test
    public void testPostTradeValidateEmptyAccount() throws Exception {
    	when(tradeService.getAllTrade()).thenReturn(tradeDTOList);
//    	when(tradeService.addTrade(any(TradeDTO.class))).thenReturn(any(TradeDTO.class));
        
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

        verify(tradeService, times(0)).getAllTrade();
        verify(tradeService, times(0)).addTrade(any(TradeDTO.class));

        String content = result.getResponse().getContentAsString();
        
        assertThat(content).contains("Account is mandatory");
        assertThat(content).contains("Should be alphanumeric and minimum more than 2 characters");
    }

    // ********************************************************************



    @DisplayName(" Url request /trade/validate - AccountMoreThanThiryCharacters "
    		+ " - Given a Trade - AccountMoreThanThiryCharacters,"
    		+ " when POST /trade/validate action request,"
    		+ " then returns error & redirect /trade/add page")    
    @Test
    public void testPostTradeValidateWithAccountMoreThanThiryCharacters() throws Exception {
    	when(tradeService.getAllTrade()).thenReturn(tradeDTOList);
//    	when(tradeService.addTrade(any(TradeDTO.class))).thenReturn(any(TradeDTO.class));
        
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

        verify(tradeService, times(0)).getAllTrade();
        verify(tradeService, times(0)).addTrade(any(TradeDTO.class));

        String content = result.getResponse().getContentAsString();
        
//        assertThat(content).contains("Account is mandatory");
        assertThat(content).contains("The maximum length for account should be 30 characters");
    }

    // ********************************************************************



    @DisplayName("Url request /trade/validate - Account  Non Alphanumeric characters - "
    		+ " - Given a Trade - Account with Non Alphanumeric characters,"
    		+ " when POST /trade/validate action request,"
    		+ " then returns error & redirect /trade/add page")    
    @Test
    public void testPostTradeValidateWithTradeAccountWithSymbols() throws Exception {
    	when(tradeService.getAllTrade()).thenReturn(tradeDTOList);
//    	when(tradeService.addTrade(any(TradeDTO.class))).thenReturn(any(TradeDTO.class));
        
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

        verify(tradeService, times(0)).getAllTrade();
        verify(tradeService, times(0)).addTrade(any(TradeDTO.class));
        

        String content = result.getResponse().getContentAsString();
        
        assertThat(content).contains("Should be alphanumeric and minimum more than 2 characters");
    }

    // ********************************************************************


    @DisplayName("Url request /trade/validate - TypeEmpty - "
    		+ " - Given a Trade - TypeEmpty,"
    		+ " when POST /trade/validate action request,"
    		+ " then returns error & redirect /trade/add page")    
    @Test
    public void testPostTradeValidateWithTypeEmpty() throws Exception {
    	when(tradeService.getAllTrade()).thenReturn(tradeDTOList);
//    	when(tradeService.addTrade(any(TradeDTO.class))).thenReturn(any(TradeDTO.class));
        
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

        verify(tradeService, times(0)).getAllTrade();
        verify(tradeService, times(0)).addTrade(any(TradeDTO.class));
        

        String content = result.getResponse().getContentAsString();
        
      assertThat(content).contains("Type is mandatory");
      assertThat(content).contains("Should be alphanumeric and minimum more than 2 characters");

    }

    // ********************************************************************



    @DisplayName("Url request /trade/validate - Type MoreThanThiryCharacters - "
    		+ " - Given a Trade - Type MoreThanThiryCharacters,"
    		+ " when POST /trade/validate action request,"
    		+ " then returns error & redirect /trade/add page")    
    @Test
    public void testPostTradeValidateWithMoreThanThiryCharacters() throws Exception {
    	when(tradeService.getAllTrade()).thenReturn(tradeDTOList);
//    	when(tradeService.addTrade(any(TradeDTO.class))).thenReturn(any(TradeDTO.class));
        
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

        verify(tradeService, times(0)).getAllTrade();
        verify(tradeService, times(0)).addTrade(any(TradeDTO.class));
        

        String content = result.getResponse().getContentAsString();
        
      assertThat(content).contains("The maximum length for type should be 30 characters");

    }

    // ********************************************************************



    @DisplayName("Url request /trade/validate - TypeWithSymbols - "
    		+ " - Given a Trade - TypeWithSymbols-,"
    		+ " when POST /trade/validate action request,"
    		+ " then returns error & redirect /trade/add page")    
    @Test
    public void testPostTradeValidateTypeWithSymbols() throws Exception {
    	when(tradeService.getAllTrade()).thenReturn(tradeDTOList);
//    	when(tradeService.addTrade(any(TradeDTO.class))).thenReturn(any(TradeDTO.class));
        
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

        verify(tradeService, times(0)).getAllTrade();
        verify(tradeService, times(0)).addTrade(any(TradeDTO.class));
        

        String content = result.getResponse().getContentAsString();
        
        assertThat(content).contains("Should be alphanumeric and minimum more than 2 characters");
        
    }

    // ********************************************************************


    @DisplayName("Url request /trade/validate - BuyQuantityNegative - "
    		+ " - Given a Trade - BuyQuantityNegative-,"
    		+ " when POST /trade/validate action request,"
    		+ " then returns error & redirect /trade/add page")    
    @Test
    public void testPostTradeValidateWithBuyQuantityNegative() throws Exception {
    	when(tradeService.getAllTrade()).thenReturn(tradeDTOList);
//    	when(tradeService.addTrade(any(TradeDTO.class))).thenReturn(any(TradeDTO.class));
        
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

        verify(tradeService, times(0)).getAllTrade();
        verify(tradeService, times(0)).addTrade(any(TradeDTO.class));
        

        String content = result.getResponse().getContentAsString();
        
        assertThat(content).contains("The value must be positive");
              
    }

    // ********************************************************************



    @DisplayName("Url request /trade/validate - BuyQuantityMoreThan10Digits - "
    		+ " - Given a Trade - BuyQuantityMoreThan10Digits -,"
    		+ " when POST /trade/validate action request,"
    		+ " then returns error & redirect /trade/add page")    
    @Test
    public void testPostTradeValidateWithBuyQuantityMoreThan10Digits() throws Exception {
    	when(tradeService.getAllTrade()).thenReturn(tradeDTOList);
//    	when(tradeService.addTrade(any(TradeDTO.class))).thenReturn(any(TradeDTO.class));
        
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

        verify(tradeService, times(0)).getAllTrade();
        verify(tradeService, times(0)).addTrade(any(TradeDTO.class));
        

        String content = result.getResponse().getContentAsString();
        
        assertThat(content).contains("Invalid number input value : Maximum digits allowed are 10 and with 2 decimals fractions");
              
    }

    // ********************************************************************



    @DisplayName("Url request /trade/validate - BuyQuantityWithSymbols - "
    		+ " - Given a Trade - BuyQuantityWithSymbols -,"
    		+ " when POST /trade/validate action request,"
    		+ " then returns error & redirect /trade/add page")    
    @Test
    public void testPostTradeValidateWithBuyQuantityWithSymbols() throws Exception {
    	when(tradeService.getAllTrade()).thenReturn(tradeDTOList);
//    	when(tradeService.addTrade(any(TradeDTO.class))).thenReturn(any(TradeDTO.class));
        
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

        verify(tradeService, times(0)).getAllTrade();
        verify(tradeService, times(0)).addTrade(any(TradeDTO.class));
        

        String content = result.getResponse().getContentAsString();
        
        assertThat(content).contains("Failed to convert property value of type java.lang.String to required type java.lang.Double for property buyQuantity");
              
    }

    // ********************************************************************

        
    
}
