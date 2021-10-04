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
import com.nnk.springboot.controller.BidListController;
import com.nnk.springboot.dto.BidListDTO;
import com.nnk.springboot.service.BidListService;

@DisplayName("Controller < BIDLIST > POST UPDATE - UNIT TESTS")
@ExtendWith(SpringExtension.class)
@WebMvcTest(BidListController.class)
class BidListControllerPostUpdateTest {


    @MockBean
    private BidListService bidListService;

    @MockBean
    private UserDetailsService userDetailsService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    private ObjectMapper objectMapper;
	
    private static BidListDTO testBidListDTO1, testBidListDTO2, bidListUpdateDTO;
 
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

       bidListUpdateDTO = BidListDTO.builder()
        		.bidListId(2)
        		.account("Account")
        		.type("Type")
        		.bidQuantity(10.0)
        		.build();
        
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }
    
  	// ********************************************************************


    @DisplayName(" Url request /bidList/update/{id} - VALID "
    		+ " - Given a BidList,"
    		+ " when POST /bidList/update/{id} action request,"
    		+ " then returns redirect /bidList/update/{id} page")    
    @Test
    public void testPostBidListUpdate() throws Exception {

    	when(bidListService
    			.updateBidList(anyInt(), any(BidListDTO.class)))
    	.thenReturn(bidListUpdateDTO);
        
        mockMvc.perform(MockMvcRequestBuilders.post("/bidList/update/1")
        .sessionAttr("bidListDTO", testBidListDTO1)
        .param("account", testBidListDTO1.getAccount())
        .param("type", testBidListDTO1.getType())
        .param("bidQuantity", testBidListDTO1.getBidQuantity().toString()))
        .andExpect(model().hasNoErrors())
        .andExpect(model().size(0))
        .andExpect(model().attributeDoesNotExist("bidListDTO"))
        .andExpect(redirectedUrl("/bidList/list"))
        .andExpect(status().is(302));

        verify(bidListService, times(1)).updateBidList(anyInt(), any(BidListDTO.class));
    }

    // ********************************************************************


    @DisplayName(" Url request /bidList/update/{id} - EmptyAccount "
    		+ " - Given a BidList - EmptyAccount,"
    		+ " when POST /bidList/update/{id} action request,"
    		+ " then returns error & redirect /bidList/update/{id} page")    
    @Test
    public void testPostBidListUpdateEmptyAccount() throws Exception {
    	when(bidListService.getAllBidList()).thenReturn(bidListDTOList);
//    	when(bidListService.updateBidList(anyInt(), any(BidListDTO.class))).thenReturn(any(BidListDTO.class));
        
    	MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/bidList/update/1")
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

        verify(bidListService, times(0)).getAllBidList();
        verify(bidListService, times(0)).updateBidList(anyInt(), any(BidListDTO.class));

        String content = result.getResponse().getContentAsString();
        
        assertThat(content).contains("Account is mandatory");
        assertThat(content).contains("Should be alphanumeric and minimum more than 2 characters");
    }

    // ********************************************************************


    @DisplayName(" Url request /bidList/update/{id} - AccountMoreThanThiryCharacters "
    		+ " - Given a BidList - AccountMoreThanThiryCharacters,"
    		+ " when POST /bidList/update/{id} action request,"
    		+ " then returns error & redirect /bidList/update/{id} page")    
    @Test
    public void testPostBidListUpdateWithAccountMoreThanThiryCharacters() throws Exception {
    	when(bidListService.getAllBidList()).thenReturn(bidListDTOList);
//    	when(bidListService.updateBidList(anyInt(), any(BidListDTO.class))).thenReturn(any(BidListDTO.class));
        
    	MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/bidList/update/1")
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

        verify(bidListService, times(0)).getAllBidList();
        verify(bidListService, times(0)).updateBidList(anyInt(), any(BidListDTO.class));

        String content = result.getResponse().getContentAsString();
        
//        assertThat(content).contains("Account is mandatory");
        assertThat(content).contains("The maximum length for account should be 30 characters");
    }

    // ********************************************************************


    @DisplayName("Url request /bidList/update/{id} - Account  Non Alphanumeric characters - "
    		+ " - Given a BidList - Account with Non Alphanumeric characters,"
    		+ " when POST /bidList/update/{id} action request,"
    		+ " then returns error & redirect /bidList/update/{id} page")    
    @Test
    public void testPostBidListUpdateWithBidListAccountWithSymbols() throws Exception {
    	when(bidListService.getAllBidList()).thenReturn(bidListDTOList);
//    	when(bidListService.updateBidList(anyInt(), any(BidListDTO.class))).thenReturn(any(BidListDTO.class));
        
    	MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/bidList/update/1")
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

        verify(bidListService, times(0)).getAllBidList();
        verify(bidListService, times(0)).updateBidList(anyInt(), any(BidListDTO.class));
        

        String content = result.getResponse().getContentAsString();
        
        assertThat(content).contains("Should be alphanumeric and minimum more than 2 characters");
    }

    // ********************************************************************


    @DisplayName("Url request /bidList/update/{id} - TypeEmpty - "
    		+ " - Given a BidList - TypeEmpty,"
    		+ " when POST /bidList/update/{id} action request,"
    		+ " then returns error & redirect /bidList/update/{id} page")    
    @Test
    public void testPostBidListUpdateWithTypeEmpty() throws Exception {
    	when(bidListService.getAllBidList()).thenReturn(bidListDTOList);
//    	when(bidListService.updateBidList(anyInt(), any(BidListDTO.class))).thenReturn(any(BidListDTO.class));
        
    	MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/bidList/update/1")
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

        verify(bidListService, times(0)).getAllBidList();
        verify(bidListService, times(0)).updateBidList(anyInt(), any(BidListDTO.class));
        

        String content = result.getResponse().getContentAsString();
        
      assertThat(content).contains("Type is mandatory");
      assertThat(content).contains("Should be alphanumeric and minimum more than 2 characters");

    }

    // ********************************************************************


    
}
