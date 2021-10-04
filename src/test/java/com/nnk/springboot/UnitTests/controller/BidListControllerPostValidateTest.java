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
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nnk.springboot.controller.BidListController;
import com.nnk.springboot.dto.BidListDTO;
import com.nnk.springboot.service.BidListService;

@DisplayName("Controller < BIDLIST > VALIDATE - UNIT TESTS")
@ExtendWith(SpringExtension.class)
@WebMvcTest(BidListController.class)
class BidListControllerPostValidateTest {


    @MockBean
    private BidListService bidListService;

    @MockBean
    private UserDetailsService userDetailsService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    private ObjectMapper objectMapper;
	
    private static BidListDTO testBidListDTO1, testBidListDTO2;
 
    private static List<BidListDTO> bidListDTOList;

    @BeforeEach
    public void setUp() {

        objectMapper = new ObjectMapper();
        testBidListDTO1 = BidListDTO.builder()
        		.bidListId(1)
        		.account("Account")
        		.type("Type")
        		.bidQuantity(10.0)
        		.build();
        
        testBidListDTO2 = BidListDTO.builder()
        		.bidListId(2)
        		.account("Account")
        		.type("Type")
        		.bidQuantity(10.0)
        		.build();
        
        bidListDTOList = Arrays.asList(testBidListDTO1, testBidListDTO2); 
        
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }
    
  	// ********************************************************************


    @DisplayName(" Url request /bidList/validate - "
    		+ " - Given a BidList,"
    		+ " when POST /bidList/validate action request,"
    		+ " then returns redirect /bidList/validate page")    
    @Test
    public void testPostBidListValidate() throws Exception {

    	when(bidListService
    			.addBidList(any(BidListDTO.class)))
    	.thenReturn(any(BidListDTO.class));
        
        mockMvc.perform(MockMvcRequestBuilders.post("/bidList/validate")
        .sessionAttr("bidListDTO", testBidListDTO1)
        .param("account", testBidListDTO1.getAccount())
        .param("type", testBidListDTO1.getType())
        .param("bidQuantity", testBidListDTO1.getBidQuantity().toString()))
        .andExpect(model().hasNoErrors())
        .andExpect(model().size(0))
        .andExpect(model().attributeDoesNotExist("bidListDTO"))
        .andExpect(redirectedUrl("/bidList/list"))
        .andExpect(status().is(302));

        verify(bidListService, times(1)).addBidList(any(BidListDTO.class));
    }

    // ********************************************************************



    @DisplayName(" Url request /bidList/validate - EmptyAccount "
    		+ " - Given a BidList - EmptyAccount,"
    		+ " when POST /bidList/validate action request,"
    		+ " then returns error & redirect /bidList/add page")    
    @Test
    public void testPostBidListValidateEmptyAccount() throws Exception {
    	when(bidListService.getAllBidList()).thenReturn(bidListDTOList);
//    	when(bidListService.addBidList(any(BidListDTO.class))).thenReturn(any(BidListDTO.class));
        
    	MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/bidList/validate")
        .sessionAttr("bidListDTO", testBidListDTO1)
        .param("account", "")
        .param("type", testBidListDTO1.getType())
        .param("bidQuantity", testBidListDTO1.getBidQuantity().toString()))
        .andExpect(model().hasErrors())
        .andExpect(model().size(1))
        .andExpect(model().attributeExists("bidListDTO"))
        .andExpect(view().name("bidList/add"))
        .andExpect(status().is(200))
        .andReturn();

        verify(bidListService, times(0)).getAllBidList();
        verify(bidListService, times(0)).addBidList(any(BidListDTO.class));

        String content = result.getResponse().getContentAsString();
        
        assertThat(content).contains("Account is mandatory");
        assertThat(content).contains("Should be alphanumeric and minimum more than 2 characters");
    }

    // ********************************************************************


    @DisplayName(" Url request /bidList/validate - AccountMoreThanThiryCharacters "
    		+ " - Given a BidList - AccountMoreThanThiryCharacters,"
    		+ " when POST /bidList/validate action request,"
    		+ " then returns error & redirect /bidList/add page")    
    @Test
    public void testPostBidListValidateWithAccountMoreThanThiryCharacters() throws Exception {
    	when(bidListService.getAllBidList()).thenReturn(bidListDTOList);
//    	when(bidListService.addBidList(any(BidListDTO.class))).thenReturn(any(BidListDTO.class));
        
    	MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/bidList/validate")
        .sessionAttr("bidListDTO", testBidListDTO1)
        .param("account", "AccountAccountAccountAccountAccount")
        .param("type", testBidListDTO1.getType())
        .param("bidQuantity", testBidListDTO1.getBidQuantity().toString()))
        .andExpect(model().hasErrors())
        .andExpect(model().size(1))
        .andExpect(model().attributeExists("bidListDTO"))
        .andExpect(view().name("bidList/add"))
        .andExpect(status().is(200))
        .andReturn();

        verify(bidListService, times(0)).getAllBidList();
        verify(bidListService, times(0)).addBidList(any(BidListDTO.class));

        String content = result.getResponse().getContentAsString();
        
//        assertThat(content).contains("Account is mandatory");
        assertThat(content).contains("The maximum length for account should be 30 characters");
    }

    // ********************************************************************




    @DisplayName("Url request /bidList/validate - Account  Non Alphanumeric characters - "
    		+ " - Given a BidList - Account with Non Alphanumeric characters,"
    		+ " when POST /bidList/validate action request,"
    		+ " then returns error & redirect /bidList/add page")    
    @Test
    public void testPostBidListValidateWithBidListAccountWithSymbols() throws Exception {
    	when(bidListService.getAllBidList()).thenReturn(bidListDTOList);
//    	when(bidListService.addBidList(any(BidListDTO.class))).thenReturn(any(BidListDTO.class));
        
    	MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/bidList/validate")
        .sessionAttr("bidListDTO", testBidListDTO1)
        .param("account", "Account!&&&")
        .param("type", testBidListDTO1.getType())
        .param("bidQuantity", testBidListDTO1.getBidQuantity().toString()))
        .andExpect(model().hasErrors())
        .andExpect(model().size(1))
        .andExpect(model().attributeExists("bidListDTO"))
        .andExpect(view().name("bidList/add"))
        .andExpect(status().is(200))
        .andReturn();

        verify(bidListService, times(0)).getAllBidList();
        verify(bidListService, times(0)).addBidList(any(BidListDTO.class));
        

        String content = result.getResponse().getContentAsString();
        
        assertThat(content).contains("Should be alphanumeric and minimum more than 2 characters");
    }

    // ********************************************************************


    @DisplayName("Url request /bidList/validate - TypeEmpty - "
    		+ " - Given a BidList - TypeEmpty,"
    		+ " when POST /bidList/validate action request,"
    		+ " then returns error & redirect /bidList/add page")    
    @Test
    public void testPostBidListValidateWithTypeEmpty() throws Exception {
    	when(bidListService.getAllBidList()).thenReturn(bidListDTOList);
//    	when(bidListService.addBidList(any(BidListDTO.class))).thenReturn(any(BidListDTO.class));
        
    	MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/bidList/validate")
        .sessionAttr("bidListDTO", testBidListDTO1)
        .param("account", testBidListDTO1.getAccount())
        .param("type", "")
        .param("bidQuantity", testBidListDTO1.getBidQuantity().toString()))
        .andExpect(model().hasErrors())
        .andExpect(model().size(1))
        .andExpect(model().attributeExists("bidListDTO"))
        .andExpect(view().name("bidList/add"))
        .andExpect(status().is(200))
        .andReturn();

        verify(bidListService, times(0)).getAllBidList();
        verify(bidListService, times(0)).addBidList(any(BidListDTO.class));
        

        String content = result.getResponse().getContentAsString();
        
      assertThat(content).contains("Type is mandatory");
      assertThat(content).contains("Should be alphanumeric and minimum more than 2 characters");

    }

    // ********************************************************************



    @DisplayName("Url request /bidList/validate - Type MoreThanThiryCharacters - "
    		+ " - Given a BidList - Type MoreThanThiryCharacters,"
    		+ " when POST /bidList/validate action request,"
    		+ " then returns error & redirect /bidList/add page")    
    @Test
    public void testPostBidListValidateWithMoreThanThiryCharacters() throws Exception {
    	when(bidListService.getAllBidList()).thenReturn(bidListDTOList);
//    	when(bidListService.addBidList(any(BidListDTO.class))).thenReturn(any(BidListDTO.class));
        
    	MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/bidList/validate")
        .sessionAttr("bidListDTO", testBidListDTO1)
        .param("account", testBidListDTO1.getAccount())
        .param("type", "TypeTypeTypeTypeTypeTypeTypeType")
        .param("bidQuantity", testBidListDTO1.getBidQuantity().toString()))
        .andExpect(model().hasErrors())
        .andExpect(model().size(1))
        .andExpect(model().attributeExists("bidListDTO"))
        .andExpect(view().name("bidList/add"))
        .andExpect(status().is(200))
        .andReturn();

        verify(bidListService, times(0)).getAllBidList();
        verify(bidListService, times(0)).addBidList(any(BidListDTO.class));
        

        String content = result.getResponse().getContentAsString();
        
      assertThat(content).contains("The maximum length for type should be 30 characters");

    }

    // ********************************************************************


    @DisplayName("Url request /bidList/validate - TypeWithSymbols - "
    		+ " - Given a BidList - TypeWithSymbols-,"
    		+ " when POST /bidList/validate action request,"
    		+ " then returns error & redirect /bidList/add page")    
    @Test
    public void testPostBidListValidateTypeWithSymbols() throws Exception {
    	when(bidListService.getAllBidList()).thenReturn(bidListDTOList);
//    	when(bidListService.addBidList(any(BidListDTO.class))).thenReturn(any(BidListDTO.class));
        
    	MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/bidList/validate")
        .sessionAttr("bidListDTO", testBidListDTO1)
        .param("account", testBidListDTO1.getAccount())
        .param("type", "Type!!&&")
        .param("bidQuantity", testBidListDTO1.getBidQuantity().toString()))
        .andExpect(model().hasErrors())
        .andExpect(model().size(1))
        .andExpect(model().attributeExists("bidListDTO"))
        .andExpect(view().name("bidList/add"))
        .andExpect(status().is(200))
        .andReturn();

        verify(bidListService, times(0)).getAllBidList();
        verify(bidListService, times(0)).addBidList(any(BidListDTO.class));
        

        String content = result.getResponse().getContentAsString();
        
        assertThat(content).contains("Should be alphanumeric and minimum more than 2 characters");
        
    }

    // ********************************************************************


    @DisplayName("Url request /bidList/validate - BidQuantityNegative - "
    		+ " - Given a BidList - BidQuantityNegative-,"
    		+ " when POST /bidList/validate action request,"
    		+ " then returns error & redirect /bidList/add page")    
    @Test
    public void testPostBidListValidateWithBidQuantityNegative() throws Exception {
    	when(bidListService.getAllBidList()).thenReturn(bidListDTOList);
//    	when(bidListService.addBidList(any(BidListDTO.class))).thenReturn(any(BidListDTO.class));
        
    	MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/bidList/validate")
        .sessionAttr("bidListDTO", testBidListDTO1)
        .param("account", testBidListDTO1.getAccount())
        .param("type", testBidListDTO1.getType())
        .param("bidQuantity", "-1000"))
        .andExpect(model().hasErrors())
        .andExpect(model().size(1))
        .andExpect(model().attributeExists("bidListDTO"))
        .andExpect(view().name("bidList/add"))
        .andExpect(status().is(200))
        .andReturn();

        verify(bidListService, times(0)).getAllBidList();
        verify(bidListService, times(0)).addBidList(any(BidListDTO.class));
        

        String content = result.getResponse().getContentAsString();
        
        assertThat(content).contains("The bidQuantity must be positive");
              
    }

    // ********************************************************************




    @DisplayName("Url request /bidList/validate - BuyQuantityMoreThan10Digits - "
    		+ " - Given a BidList - BuyQuantityMoreThan10Digits -,"
    		+ " when POST /bidList/validate action request,"
    		+ " then returns error & redirect /bidList/add page")    
    @Test
    public void testPostBidListValidateWithBuyQuantityMoreThan10Digits() throws Exception {
    	when(bidListService.getAllBidList()).thenReturn(bidListDTOList);
//    	when(bidListService.addBidList(any(BidListDTO.class))).thenReturn(any(BidListDTO.class));
        
    	MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/bidList/validate")
        .sessionAttr("bidListDTO", testBidListDTO1)
        .param("account", testBidListDTO1.getAccount())
        .param("type", testBidListDTO1.getType())
        .param("bidQuantity", "100000000000000.00"))
        .andExpect(model().hasErrors())
        .andExpect(model().size(1))
        .andExpect(model().attributeExists("bidListDTO"))
        .andExpect(view().name("bidList/add"))
        .andExpect(status().is(200))
        .andReturn();

        verify(bidListService, times(0)).getAllBidList();
        verify(bidListService, times(0)).addBidList(any(BidListDTO.class));
        

        String content = result.getResponse().getContentAsString();
        
        assertThat(content).contains("Invalid number input value : Maximum digits allowed are 10 and with 2 decimals fractions");
              
    }

    // ********************************************************************

    
}
