package com.nnk.springboot.UnitTests.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.dto.CurvePointDTO;
import com.nnk.springboot.exception.DataNotFoundException;
import com.nnk.springboot.repository.CurvePointRepository;
import com.nnk.springboot.service.CurvePointService;
import com.nnk.springboot.util.CurvePointMapper;

@DisplayName("Service ==> CurvePoint DELETE - UNIT TESTS")
@ExtendWith(MockitoExtension.class)
class CurvePointServiceDeleteTest {

    @InjectMocks
    private CurvePointService curvePointService;

    @Mock
    private CurvePointRepository curvePointRepository;
    
    @Mock
    private CurvePointMapper curvePointMapper;

    @Mock
    private ObjectMapper objectMapper;
	
    private static CurvePointDTO testCurvePointDTO1;
    
    private static CurvePoint testCurvePoint1;
    
    
    @BeforeEach
    public void setUp() {
        objectMapper = new ObjectMapper();
        testCurvePointDTO1 = CurvePointDTO.builder()
        		.curveId(1)
        		.term(10.0)
        		.value(10.0)
        		.build();
        
       
        testCurvePoint1 = CurvePoint.builder()
        		.curveId(1)
        		.term(10.0)
        		.value(10.0)
        		.build();
        
       
    }

	// *******************************************************************	
    
	@DisplayName("Delete CurvePoint - "
			+ "GIVEN a CURVEPOINT  "
			+ "WHEN Requested DELETE CurvePoint"
			+ "THEN returns expected curvePoint deleted")	    
    @Test
    public void testDeleteCurvePoint() throws Exception {
	
        // GIVEN
		 when(curvePointRepository
				 .findById(anyInt()))
		 .thenReturn(java.util.Optional.ofNullable(testCurvePoint1));
		 
        // WHEN
		 curvePointService.deleteCurvePoint(1);
        
        // THEN
	       InOrder inOrder = inOrder(curvePointRepository);
	       inOrder.verify(curvePointRepository).findById(anyInt());
	       inOrder.verify(curvePointRepository).deleteById(anyInt());
	       
	       verify(curvePointRepository, times(1)).findById(anyInt());
	       verify(curvePointRepository, times(1)).deleteById(anyInt());
    
	}
   

	// *******************************************************************	
	
    @DisplayName("Check <Exception>"
		+ "GIVEN a CurvePoint not exist "
		+ "WHEN Requested DELETE CurvePoint "
		+ "THEN throws Exception")	    
	@Test
	public void testDeleteCurvePointNotExists() throws Exception {

    	when(curvePointRepository
    			.findById(anyInt()))
    	.thenReturn(java.util.Optional.empty());
    
    	// WHEN // THEN
    	assertThrows(DataNotFoundException.class, ()
        		-> curvePointService.deleteCurvePoint(anyInt()));
	} 

	// *******************************************************************	

}
