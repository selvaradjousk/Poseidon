package com.nnk.springboot.IT.service;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.nnk.springboot.exception.DataNotFoundException;
import com.nnk.springboot.service.CurvePointService;

@DisplayName("INTEGRATION TESTS - Service ==> CurvePoint DELETE")
@SpringBootTest
@ActiveProfiles("test")
class CurvePointServiceDelete_IT {


    @Autowired
    private CurvePointService curvePointService;


	// *******************************************************************	
    
    @DisplayName("Check <Delete CurvePoint>"
		+ "GIVEN a CurvePoint  "
		+ "WHEN Requested DELETE CurvePoint "
		+ "THEN Deletes CurvePoint")	    
	@Test
	public void CurvePoint() throws Exception {

    	curvePointService.deleteCurvePoint(3);
    	// WHEN // THEN
    	assertThrows(DataNotFoundException.class, ()
        		-> curvePointService.deleteCurvePoint(3));
	} 

	// *******************************************************************	
 
    @DisplayName("Check <Exception>"
		+ "GIVEN a CurvePoint not exist "
		+ "WHEN Requested DELETE CurvePoint "
		+ "THEN throws Exception")	    
	@Test
	public void testDeleteCurvePointNotExists() throws Exception {
    
    	// WHEN // THEN
    	assertThrows(DataNotFoundException.class, ()
        		-> curvePointService.deleteCurvePoint(20));
	} 

	// *******************************************************************	

}
