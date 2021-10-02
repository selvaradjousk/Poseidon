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

@DisplayName("CurvePoint Service Update CurvePoint- UNIT TESTS")
@ExtendWith(MockitoExtension.class)
class CurvePointServiceUpdateTest {


    @InjectMocks
    private CurvePointService curvePointService;

    @Mock
    private CurvePointRepository curvePointRepository;
    
    @Mock
    private CurvePointMapper curvePointMapper;
	
    @Mock
    private ObjectMapper objectMapper;
	
    private static CurvePointDTO curvePointUpdatedDTO;
    
    private static CurvePoint testCurvePoint1, curvePointToUpdate, curvePointUpdated;
    
    
    @BeforeEach
    public void setUp() {
        objectMapper = new ObjectMapper();
        
        testCurvePoint1 = CurvePoint.builder()
        		.curveId(1)
        		.term(10.0)
        		.value(10.0)
        		.build();
        
       
    }
  
    // ***********************************************************************************

    @DisplayName("Test UPDATE Existing CURVEPOINT")
    @Nested
    class TestUpdateExistingCurvePoint {  
    	
        @BeforeEach
        public void init() {
        	
 	       curvePointToUpdate = CurvePoint.builder()
 	         		.curveId(1)
 	        		.term(10.0)
 	        		.value(10.0)
 	        		.build();
 	       
	       curvePointUpdated = CurvePoint.builder()
 	         		.curveId(1)
 	        		.term(1000.0)
 	        		.value(10.0)
 	        		.build();
	        
        
		       curvePointUpdatedDTO = CurvePointDTO.builder()
 	         		.curveId(1)
 	        		.term(1000.0)
 	        		.value(10.0)
 	        		.build();
	        
       when(curvePointRepository
    		   .findById(anyInt()))
       .thenReturn(java.util.Optional.ofNullable(testCurvePoint1));
	               
       when(curvePointMapper
    		   .toCurvePoint(any(CurvePointDTO.class)))
       .thenReturn(curvePointToUpdate);
       
       when(curvePointRepository
    		   .save(any(CurvePoint.class)))
       .thenReturn(curvePointUpdated);
       
       when(curvePointMapper
    		   .toCurvePointDTO(any(CurvePoint.class)))
       .thenReturn(curvePointUpdatedDTO);

        }

	// *******************************************************************	
    
    @DisplayName("Check <Execution Order>"
    		+ " - Given a existing CurvePoint,"
    		+ " when UPDATE CURVEPOINT action request,"
    		+ " then all steps are executed in correct order and number of expected times")    
    @Test
    public void testUpdateExistingCurvePointExecutionOrderCheck() throws Exception {
		

    	curvePointService.updateCurvePoint(2, curvePointUpdatedDTO);

        InOrder inOrder = inOrder(curvePointRepository, curvePointMapper, curvePointMapper);
        inOrder.verify(curvePointRepository).findById(anyInt());
        inOrder.verify(curvePointMapper).toCurvePoint(any(CurvePointDTO.class));
        inOrder.verify(curvePointRepository).save(any(CurvePoint.class));
        inOrder.verify(curvePointMapper).toCurvePointDTO(any(CurvePoint.class));
        
        verify(curvePointRepository, times(1)).findById(anyInt());
        verify(curvePointMapper, times(1)).toCurvePoint(any(CurvePointDTO.class));
        verify(curvePointRepository, times(1)).save(any(CurvePoint.class));
        verify(curvePointMapper, times(1)).toCurvePointDTO(any(CurvePoint.class));
        
    }

	// *******************************************************************	
 
    
    }
    
	// *******************************************************************	   
}