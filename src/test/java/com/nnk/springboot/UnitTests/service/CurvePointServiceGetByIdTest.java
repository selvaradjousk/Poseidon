package com.nnk.springboot.UnitTests.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
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

@DisplayName("Service ==> CurvePoint GET BY ID - UNIT TESTS")
@ExtendWith(MockitoExtension.class)
class CurvePointServiceGetByIdTest {

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
  
    // ***********************************************************************************

    @DisplayName("Test GET USER BY IDENTITY")
    @Nested
    class TestGetCurvePointById  {  
    	
        @BeforeEach
        public void init() {
        	
            when(curvePointRepository
            		.findById(anyInt()))
            .thenReturn(java.util.Optional.ofNullable(testCurvePoint1));
            
            when(curvePointMapper
            		.toCurvePointDTO(any(CurvePoint.class)))
            .thenReturn(testCurvePointDTO1);  

        }

	// *******************************************************************	
    
    @DisplayName("Check <Execution Order>"
    		+ " - Given a CurvePoint,"
    		+ " when GET CURVEPOINT BY ID action request,"
    		+ " then all steps are executed in correct order and number of expected times")    
    @Test
    public void testGetCurvePointByIdExecutionOrderCheck() throws Exception {
		

    	curvePointService.getCurvePointById(1);

        InOrder inOrder = inOrder(curvePointRepository, curvePointMapper);
        inOrder.verify(curvePointRepository).findById(anyInt());
        inOrder.verify(curvePointMapper).toCurvePointDTO(any(CurvePoint.class));
        
        verify(curvePointRepository, times(1)).findById(anyInt());
        verify(curvePointMapper, times(1)).toCurvePointDTO(any(CurvePoint.class));
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

    	assertEquals(result, testCurvePointDTO1);
	    assertThat(result).usingRecursiveComparison().isEqualTo(testCurvePointDTO1);
	    assertEquals(testCurvePointDTO1.getValue(), result.getValue());
	    assertEquals(testCurvePointDTO1.getCurveId(), result.getCurveId());
    }  
    
    // ******************************************************************		
	   
  }

 	
    @DisplayName("ERROR GET EXISTING CURVEPOINT by ID for non existing CURVEPOINT data"
    		+ " - Given a non existing CURVEPOINT,"
    		+ " when GET CURVEPOINT By ID action request,"
    		+ " then CURVEPOINT entry should respond"
    		+ " with Data Not Found Exception")
	@Test
	public void testGetCurvePointByIdNonExistingCurvePointData() throws Exception {

    	when(curvePointRepository
    			.findById(anyInt()))
    	.thenReturn(java.util.Optional.empty());
    
    	// WHEN // THEN
    	assertThrows(DataNotFoundException.class, ()
        		-> curvePointService.getCurvePointById(1));
	}
    
	// *******************************************************************	  

}