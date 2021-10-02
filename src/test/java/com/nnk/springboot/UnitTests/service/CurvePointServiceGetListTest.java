package com.nnk.springboot.UnitTests.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

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

@DisplayName("CurvePoint Service GetCurvePoint List - UNIT TESTS")
@ExtendWith(MockitoExtension.class)
class CurvePointServiceGetListTest {

    @InjectMocks
    private CurvePointService CurvePointService;

    @Mock
    private CurvePointRepository CurvePointRepository;
    
    @Mock
    private CurvePointMapper CurvePointMapper;
    
    private ObjectMapper objectMapper;
	
    private static CurvePointDTO testCurvePointDTO1, testCurvePointDTO2;
    
    private static CurvePoint testCurvePoint1, testCurvePoint2;
    
    private static List<CurvePointDTO> CurvePointDTOList;
    
    private static List<CurvePoint> CurvePointList;

    
    @BeforeEach
    public void setUp() {
        objectMapper = new ObjectMapper();
        testCurvePointDTO1 = CurvePointDTO.builder()
        		.curveId(1)
        		.term(10.0)
        		.value(10.0)
        		.build();
        
        testCurvePointDTO2 = CurvePointDTO.builder()
        		.curveId(2)
        		.term(20.0)
        		.value(20.0)
        		.build();
        
        CurvePointDTOList = Arrays.asList(testCurvePointDTO1, testCurvePointDTO2);   
        
        testCurvePoint1 = CurvePoint.builder()
        		.curveId(1)
        		.term(10.0)
        		.value(10.0)
        		.build();
        
        testCurvePoint2 = CurvePoint.builder()
        		.curveId(2)
        		.term(20.0)
        		.value(20.0)
        		.build();
        
        CurvePointList = Arrays.asList(testCurvePoint1, testCurvePoint2);   
        
    }
    


   	// *******************************************************************	
    @DisplayName("Test List CurvePoints")
    @Nested
    class TestListCurvePoint {  
    	
        @BeforeEach
        public void init() {
        	
        	// GIVEN
            when(CurvePointRepository
            		.findAll())
            .thenReturn(CurvePointList);
            
            when(CurvePointMapper
            		.toCurvePointDTO(testCurvePoint1))
            .thenReturn(testCurvePointDTO1);
            
            when(CurvePointMapper
            		.toCurvePointDTO(testCurvePoint2))
            .thenReturn(testCurvePointDTO2);
            
        }
        
        
    	// *******************************************************************	
        
        @DisplayName("Check <Execution Order>"
        		+ " - Given a CurvePoint List,"
        		+ " when Get CurvePoint List action request,"
        		+ " then all steps are executed in correct order and number of expected times")    
        @Test
        public void testGetCurvePointsListExecutionOrderCheck() throws Exception {
   	

           // WHEN
           CurvePointService.getAllCurvePoint();
           
           // THEN
           InOrder inOrder = inOrder(CurvePointRepository, CurvePointMapper);
           inOrder.verify(CurvePointRepository).findAll();
           inOrder.verify(CurvePointMapper).toCurvePointDTO(testCurvePoint1);
           inOrder.verify(CurvePointMapper).toCurvePointDTO(testCurvePoint2);
           
           verify(CurvePointRepository, times(1)).findAll();
           verify(CurvePointMapper, times(1)).toCurvePointDTO(testCurvePoint1);
           verify(CurvePointMapper, times(1)).toCurvePointDTO(testCurvePoint2);
       
        }
   	
        
        
       	// *******************************************************************	
           
           @DisplayName("Check Check <NotNull>"
           		+ " - Given a CurvePoint List,"
           		+ " when Get CurvePoint List action request,"
           		+ " then returns CurvePointslist not null")    
           @Test
           public void testGetCurvePointsListNotNullCheck() throws Exception {
      	

              // WHEN
              List<CurvePointDTO> result = CurvePointService.getAllCurvePoint();
              
              // THEN
              assertNotNull(result);
          
           }
           
   
         // *******************************************************************	          
    

        
    } 
}
