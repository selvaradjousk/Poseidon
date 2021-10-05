package com.nnk.springboot.UnitTests.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nnk.springboot.controller.CurveController;
import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.dto.CurvePointDTO;
import com.nnk.springboot.service.CurvePointService;

@DisplayName("Controller < CURVEPOINT > GET LIST - UNIT TESTS")
@ExtendWith(SpringExtension.class)
@WebMvcTest(CurveController.class)
class CurvePointControllerGetListTest {


    @MockBean
    private CurvePointService curvePointService;

    @MockBean
    private UserDetailsService userDetailsService;
    
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    private ObjectMapper objectMapper;
	
    private static CurvePointDTO testCurvePointDTO1, testCurvePointDTO2;
    
    private static CurvePoint testCurvePoint1, testCurvePoint2;
    
    private static List<CurvePointDTO> curvePointDTOList;
    
    private static List<CurvePoint> curvePointList;

    @BeforeEach
    public void setUp() {

        objectMapper = new ObjectMapper();
        testCurvePointDTO1 = CurvePointDTO.builder()
        		.id(1)
        		.curveId(1)
        		.term(10.0)
        		.value(10.0)
        		.build();
        
        testCurvePointDTO2 = CurvePointDTO.builder()
        		.id(2)
        		.curveId(2)
        		.term(20.0)
        		.value(20.0)
        		.build();
        
        curvePointDTOList = Arrays.asList(testCurvePointDTO1, testCurvePointDTO2);   
        
        testCurvePoint1 = CurvePoint.builder()
        		.id(1)
        		.curveId(1)
        		.term(10.0)
        		.value(10.0)
        		.build();
        
        testCurvePoint2 = CurvePoint.builder()
        		.id(2)
        		.curveId(2)
        		.term(20.0)
        		.value(20.0)
        		.build();
        
        curvePointList = Arrays.asList(testCurvePoint1, testCurvePoint2);   
    
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }
    
  	// ********************************************************************

    
    @DisplayName(" Url request /curvePoint/list - "
    		+ " - Given a CurvePoint List,"
    		+ " when GET /curvePoint/list action request,"
    		+ " then returns curvePointslist page")
    @Test
    public void testGetCurvePointList() throws Exception {
        when(curvePointService.getAllCurvePoint()).thenReturn(curvePointDTOList);
        mockMvc.perform(MockMvcRequestBuilders.get("/curvePoint/list"))
                .andExpect(model().attributeExists("curvePoints"))
                .andExpect(model().size(1))
                .andExpect(view().name("curvePoint/list"))
                .andExpect(status().isOk());

        verify(curvePointService, times(1)).getAllCurvePoint();
        assertEquals(2, (curvePointService.getAllCurvePoint()).size());
        assertThat(curvePointDTOList).usingRecursiveComparison().isEqualTo(curvePointService.getAllCurvePoint()).toString();
    }

    // ********************************************************************

}
