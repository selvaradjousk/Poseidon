package com.nnk.springboot.UnitTests.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
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
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nnk.springboot.config.JwtUtils;
import com.nnk.springboot.config.MyUserDetailsService;
import com.nnk.springboot.controller.BidListController;
import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.dto.BidListDTO;
import com.nnk.springboot.service.BidListService;

@DisplayName("Controller < BIDLIST > GET LIST - UNIT TESTS")
@ExtendWith(SpringExtension.class)
@WebMvcTest(BidListController.class)
class BidListControllerGetListTest {


    @MockBean
    private BidListService bidListService;

    @MockBean
    private MyUserDetailsService userDetailsService;

    @MockBean
    private JwtUtils jwtUtils;
           
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    private ObjectMapper objectMapper;
	
    private static BidListDTO testBidListDTO1, testBidListDTO2;
    
    private static BidList testBidList1, testBidList2;
    
    private static List<BidListDTO> bidListDTOList;
    
    private static List<BidList> bidListList;

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
        
        testBidList1 = BidList.builder()
        		.account("Account")
        		.type("Type")
        		.bidQuantity(10.0)
        		.build();
        
        testBidList2 = BidList.builder()
        		.account("Account")
        		.type("Type")
        		.bidQuantity(10.0)
        		.build();
        
        bidListList = Arrays.asList(testBidList1, testBidList2);   
    
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }
    
  	// ********************************************************************

    @WithMockUser(username="admin",roles={"ADMIN"})
    @DisplayName(" Url request /bidList/list - "
    		+ " - Given a BidList List,"
    		+ " when GET /bidList/list action request,"
    		+ " then returns bidListslist page")
    @Test
    public void testGetBidListList() throws Exception {
        when(bidListService.getAllBidList()).thenReturn(bidListDTOList);
        mockMvc.perform(MockMvcRequestBuilders.get("/bidList/list"))
                .andExpect(model().attributeExists("bids"))
                .andExpect(model().size(1))
                .andExpect(view().name("bidList/list"))
                .andExpect(status().isOk());

        verify(bidListService, times(1)).getAllBidList();
        assertEquals(2, (bidListService.getAllBidList()).size());
        assertThat(bidListDTOList).usingRecursiveComparison().isEqualTo(bidListService.getAllBidList()).toString();
    }

    // ********************************************************************

    @WithMockUser(username="admin")
    @DisplayName(" Url request /bidList/list - "
    		+ " - Given a BidList List,"
    		+ " when GET /bidList/list action request,"
    		+ " then returns bidListslist page")    
    @Test
    public void testGetBidListListNull() throws Exception {
        when(bidListService.getAllBidList()).thenReturn(null);
        mockMvc.perform(MockMvcRequestBuilders.get("/bidList/list"))
//                .andExpect(model().attributeExists("bids"))
                .andExpect(model().size(1))
                .andExpect(view().name("bidList/list"))
                .andExpect(status().isOk());

        verify(bidListService, times(1)).getAllBidList();
        assertNull((bidListService.getAllBidList()));
    }

    // ********************************************************************
    

}
