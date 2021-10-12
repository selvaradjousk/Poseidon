package com.nnk.springboot.IT.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.nnk.springboot.dto.BidListDTO;
import com.nnk.springboot.service.BidListService;

@DisplayName("INTEGRATION TESTS - Service ==>  BidList Get List")
@SpringBootTest
@ActiveProfiles("test")
class BidListServiceGetList_IT {

    @Autowired
    private BidListService bidListService;



    	// *******************************************************************	
     	

        
        @DisplayName("Check Check <NotNull>"
        		+ " - Given a BidList List,"
        		+ " when Get BidList List action request,"
        		+ " then returns BidListslist not null")    
        @Test
        public void testGetBidListsListNotNullCheck() throws Exception {
   	
           // GIVEN

           // WHEN
           List<BidListDTO> result = bidListService
        		   .getAllBidList();
           
           // THEN
           assertNotNull(result);
       
        }
        

      // *******************************************************************	          



        @DisplayName("Check <result Match>"
        		+ " - Given a BidList List,"
        		+ " when Get BidList List action request,"
        		+ " then return expected Number of BidLists")    
        @Test
        public void testGetBidListsListRecordsNumberMatchCheck() throws Exception {
   	
           // GIVEN

           // WHEN
           List<BidListDTO> result = bidListService.getAllBidList();
           
           // THEN
           assertEquals(5, result.size());

        }    
        
        // *******************************************************************	

   
}
