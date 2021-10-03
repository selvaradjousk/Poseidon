package com.nnk.springboot.UnitTests.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
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
import com.nnk.springboot.repository.CurvePointRepository;
import com.nnk.springboot.service.CurvePointService;
import com.nnk.springboot.util.CurvePointMapper;

@DisplayName("Service ==> CurvePoint Add - UNIT TESTS")
@ExtendWith(MockitoExtension.class)
class CurvePointServiceAddTest {


    @InjectMocks
    private CurvePointService curvePointService;

    @Mock
    private CurvePointRepository curvePointRepository;
    
    @Mock
    private CurvePointMapper curvePointMapper;
	
    @Mock
    private ObjectMapper objectMapper;
	
    private static CurvePointDTO testCurvePointDTO1, curvePointToAddDTO;
    
    private static CurvePoint testCurvePoint1, curvePointToAdd;
    
    
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

    @DisplayName("Test ADD New CURVEPOINT")
    @Nested
    class TestAddNewCurvePoint {  
    	
        @BeforeEach
        public void init() {
        	
 	       curvePointToAdd = CurvePoint.builder()
 	         		.curveId(1)
 	        		.term(10.0)
 	        		.value(10.0)
 	        		.build();
	        
	        curvePointToAddDTO = CurvePointDTO.builder()
	           		.curveId(1)
	        		.term(10.0)
	        		.value(10.0)
	        		.build();
       
       when(curvePointMapper
    		   .toCurvePoint(any(CurvePointDTO.class)))
       .thenReturn(curvePointToAdd);
       
       when(curvePointRepository
    		   .save(any(CurvePoint.class)))
       .thenReturn(testCurvePoint1);
       
       when(curvePointMapper
    		   .toCurvePointDTO(any(CurvePoint.class)))
       .thenReturn(testCurvePointDTO1);

        }

	// *******************************************************************	
    
    @DisplayName("Check <Execution Order>"
    		+ " - Given a new CurvePoint,"
    		+ " when ADD CURVEPOINT action request,"
    		+ " then all steps are executed in correct order and number of expected times")    
    @Test
    public void testAddNewCurvePointExecutionOrderCheck() throws Exception {
		

    	curvePointService.addCurvePoint(curvePointToAddDTO);

        InOrder inOrder = inOrder(curvePointMapper, curvePointRepository, curvePointMapper);
        inOrder.verify(curvePointMapper).toCurvePoint(any(CurvePointDTO.class));
        inOrder.verify(curvePointRepository).save(any(CurvePoint.class));
        inOrder.verify(curvePointMapper).toCurvePointDTO(any(CurvePoint.class));
        
        verify(curvePointMapper, times(1)).toCurvePoint(any(CurvePointDTO.class));
        verify(curvePointRepository, times(1)).save(any(CurvePoint.class));
        verify(curvePointMapper, times(1)).toCurvePointDTO(any(CurvePoint.class));
    }
	

	// *******************************************************************


    
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
	    
	    }
	    
	// *******************************************************************	

 

}