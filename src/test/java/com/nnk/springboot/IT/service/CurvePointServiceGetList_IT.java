package com.nnk.springboot.IT.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.nnk.springboot.dto.CurvePointDTO;
import com.nnk.springboot.service.CurvePointService;

@DisplayName("INTEGRATION TESTS - Service ==> CurvePoint Get List")
@SpringBootTest
@ActiveProfiles("test")
class CurvePointServiceGetList_IT {

    @Autowired
    private CurvePointService CurvePointService;

        
       	// *******************************************************************	
           
           @DisplayName("Check Check <NotNull>"
           		+ " - Given a CurvePoint List,"
           		+ " when Get CurvePoint List action request,"
           		+ " then returns CurvePointslist not null")    
           @Test
           public void testGetCurvePointsListNotNullCheck() throws Exception {
      	

              // WHEN
              List<CurvePointDTO> result = CurvePointService
            		  .getAllCurvePoint();
              
              // THEN
              assertNotNull(result);
          
           }
           
   
         // *******************************************************************	          

           @DisplayName("Check <Execution Order>"
           		+ " - Given a CurvePoint List,"
           		+ " when Get CurvePoint List action request,"
           		+ " then return expected Number of CurvePoints")    
           @Test
           public void testGetCurvePointsListRecordsNumberMatchCheck() throws Exception {
      	
              // GIVEN

              // WHEN
              List<CurvePointDTO> result = CurvePointService.getAllCurvePoint();
              
              // THEN
              assertEquals(5, result.size());

           }    
           
           // *******************************************************************	       

}
