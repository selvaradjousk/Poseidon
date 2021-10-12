package com.nnk.springboot.IT.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nnk.springboot.dto.CurvePointDTO;
import com.nnk.springboot.service.CurvePointService;

@DisplayName("INTEGRATION TESTS - Service ==> CurvePoint Add")
@SpringBootTest
@ActiveProfiles("test")
class CurvePointServiceAdd_IT {



    @Autowired
    private CurvePointService curvePointService;

    private ObjectMapper objectMapper;
	
    private static CurvePointDTO curvePointToAddDTO;

    
    @BeforeEach
    public void setUp() {

    	objectMapper = new ObjectMapper();

        curvePointToAddDTO = CurvePointDTO.builder()
        		.id(7)
           		.curveId(1)
        		.term(10.0)
        		.value(10.0)
        		.build();    
       
    }
  
    // ***********************************************************************************

    
	    @DisplayName("Check <NotNull>"
			+ " - Given a new CurvePoint,"
			+ " when ADD CURVEPOINT action request,"
			+ " then CURVEPOINT should not be null")	    
	    @Test
	    public void testAddNewCurvePointNotNullCheck() {
			
	
	        CurvePointDTO curvePointSaved = curvePointService
	        		.addCurvePoint(curvePointToAddDTO);
	
	        assertNotNull(curvePointSaved);
    }

	// *******************************************************************    
    
	    
	    @Test
	    @DisplayName("Check <Validate> match of both same record instance "
	    		+ " - Given a new CurvePoint,"
	    		+ " when ADD CURVEPOINT action request,"
	    		+ " then CURVEPOINT added should be added and same as test record")
	    public void testAddNewPersonReturnResultMatch() {
	   			

	   	        CurvePointDTO curvePointSaved = curvePointService
	   	        		.addCurvePoint(curvePointToAddDTO);

	   	        assertEquals(curvePointToAddDTO.toString(), curvePointSaved.toString());
	   	        assertThat(curvePointSaved).usingRecursiveComparison().isEqualTo(curvePointToAddDTO);
	   	    }
	    

	// *******************************************************************	

 

}