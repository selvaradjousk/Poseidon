package com.nnk.springboot.IT.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nnk.springboot.dto.CurvePointDTO;
import com.nnk.springboot.exception.DataNotFoundException;
import com.nnk.springboot.service.CurvePointService;

@DisplayName("INTEGRATION TESTS - Service ==> CurvePoint GET BY ID")
@SpringBootTest
@ActiveProfiles("test")
class CurvePointServiceGetById_IT {


    @Autowired
    private CurvePointService curvePointService;

    private ObjectMapper objectMapper;
	
    private static CurvePointDTO testCurvePointDTO1;
    
    
    @BeforeEach
    public void setUp() {
        objectMapper = new ObjectMapper();
        testCurvePointDTO1 = CurvePointDTO.builder()
        		.id(1)
        		.curveId(1)
        		.term(1.0)
        		.value(10.0)
        		.build();
       
    }


	// *******************************************************************	

    @DisplayName("Check <NotNull>"
    		+ " - Given a existing CurvePoint,"
    		+ " when GET CURVEPOINT By ID action request,"
    		+ " then CURVEPOINT should not be null")	    
	    @Test
	    public void testCurvePointByIdNotNullCheck() {
			

    		CurvePointDTO result = curvePointService
    				.getCurvePointById(1);

	        assertNotNull(result);
	    }

	// *******************************************************************	
 
    
    @Test
    @DisplayName("Check <Validate> match of both same record instance "
    		+ " - Given a existing CurvePoint,"
    		+ " when GET CURVEPOINT By ID action request,"
    		+ " then CURVEPOINT ID same as test record")
    public void testAddNewPersonReturnResultMatch() {
			
    	CurvePointDTO result = curvePointService
    			.getCurvePointById(1);

    	assertEquals(result.toString(), testCurvePointDTO1.toString());
	    assertThat(result).usingRecursiveComparison().isEqualTo(testCurvePointDTO1);
	    assertEquals(testCurvePointDTO1.getValue(), result.getValue());
	    assertEquals(testCurvePointDTO1.getCurveId(), result.getCurveId());
    }  
    
    // ******************************************************************		

 	
    @DisplayName("ERROR GET EXISTING CURVEPOINT by ID for non existing CURVEPOINT data"
    		+ " - Given a non existing CURVEPOINT,"
    		+ " when GET CURVEPOINT By ID action request,"
    		+ " then CURVEPOINT entry should respond"
    		+ " with Data Not Found Exception")
	@Test
	public void testGetCurvePointByIdNonExistingCurvePointData() throws Exception {

    
    	// WHEN // THEN
    	assertThrows(DataNotFoundException.class, ()
        		-> curvePointService.getCurvePointById(100));
	}
    
	// *******************************************************************	  

}