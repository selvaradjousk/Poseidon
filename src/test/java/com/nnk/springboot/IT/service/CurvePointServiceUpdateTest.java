package com.nnk.springboot.IT.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nnk.springboot.dto.CurvePointDTO;
import com.nnk.springboot.exception.DataNotFoundException;
import com.nnk.springboot.service.CurvePointService;

@DisplayName("INTEGRATION TESTS - Service ==> CurvePoint Update")
@SpringBootTest
@ActiveProfiles("test")
class CurvePointServiceUpdateTest {


    @Autowired
    private CurvePointService curvePointService;

	
    @Mock
    private ObjectMapper objectMapper;
	
    private static CurvePointDTO curvePointUpdatedDTO;
  
        @BeforeEach
        public void init() {
        
		       curvePointUpdatedDTO = CurvePointDTO.builder()
	    		   	.id(2)
 	         		.curveId(1)
 	        		.term(1000.0)
 	        		.value(10.0)
 	        		.build();

        }

	// *******************************************************************	

    
    @DisplayName("Check <NotNull>"
		+ " - Given a new CurvePoint,"
		+ " when UPDATE CURVEPOINT action request,"
		+ " then CURVEPOINT should not be null")	    
    @Test
    public void testUpdateExistingCurvePointNotNullCheck() {
		

        CurvePointDTO curvePointSaved = curvePointService
        		.updateCurvePoint(2, curvePointUpdatedDTO);

        assertNotNull(curvePointSaved);
    }

	// *******************************************************************			

    

    @Test
    @DisplayName("Check <Validate> match of both same record instance "
    		+ " - Given a new CurvePoint,"
    		+ " when UPDATE CURVEPOINT action request,"
    		+ " then CURVEPOINT updateed should be updateed and same as test record")
    public void testUpdateExistingCurvePointReturnResultMatch() {
   			

   	        CurvePointDTO curvePointSaved = curvePointService
   	        		.updateCurvePoint(2, curvePointUpdatedDTO);

   	        assertEquals(curvePointUpdatedDTO.toString(), curvePointSaved.toString());
   	        assertThat(curvePointSaved).usingRecursiveComparison().isEqualTo(curvePointUpdatedDTO);
   	    }

    
	// *******************************************************************	   

	
    @DisplayName("ERROR UPDATE CURVEPOINT for non existing CURVEPOINT data"
    		+ " - Given a non existing CURVEPOINT,"
    		+ " when UPDATE CURVEPOINTaction request,"
    		+ " then CURVEPOINT entry should respond"
    		+ " with Data Not Found Exception")
	@Test
	public void testGetCurvePointByIdNonExistingCurvePointData() throws Exception {

    
    	// WHEN // THEN
    	assertThrows(DataNotFoundException.class, ()
        		-> curvePointService.updateCurvePoint(100, curvePointUpdatedDTO));
	}
    
	// *******************************************************************	  

}