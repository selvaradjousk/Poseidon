 package com.nnk.springboot.UnitTests.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
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
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nnk.springboot.controller.CurveController;
import com.nnk.springboot.dto.CurvePointDTO;
import com.nnk.springboot.service.CurvePointService;

@DisplayName("Controller < CURVEPOINT > -VALIDATE UNIT TESTS")
@ExtendWith(SpringExtension.class)
@WebMvcTest(CurveController.class)
class CurvePointControllerPostUpdateTest {


    @MockBean
    private CurvePointService curvePointService;

    @MockBean
    private UserDetailsService userDetailsService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    private ObjectMapper objectMapper;
	
    private static CurvePointDTO testCurvePointDTO1, testCurvePointDTO2, curvePointUpdateDTO;
 
    private static List<CurvePointDTO> curvePointDTOList;

    @BeforeEach
    public void setUp() {

        objectMapper = new ObjectMapper();
        objectMapper = new ObjectMapper();
        testCurvePointDTO1 = CurvePointDTO.builder()
        		.curveId(1)
        		.term(10.0)
        		.value(10.0)
        		.build();
 
        
        curvePointUpdateDTO = CurvePointDTO.builder()
        		.curveId(1)
        		.term(10.0)
        		.value(10.0)
        		.build();
        
        testCurvePointDTO2 = CurvePointDTO.builder()
        		.curveId(2)
        		.term(20.0)
        		.value(20.0)
        		.build();
        
        curvePointDTOList = Arrays.asList(testCurvePointDTO1, testCurvePointDTO2); 
        
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }
    
  	// ********************************************************************


    @DisplayName(" Url request /curvePoint/update/{id} - "
    		+ " - Given a CurvePoint,"
    		+ " when POST /curvePoint/update/{id} action request,"
    		+ " then returns redirect /curvePoint/update/{id} page")    
    @Test
    public void testPostCurvePointValidate() throws Exception {

    	when(curvePointService
    			.updateCurvePoint(anyInt(), any(CurvePointDTO.class)))
    	.thenReturn(curvePointUpdateDTO);
        
        mockMvc.perform(MockMvcRequestBuilders.post("/curvePoint/update/1")
        .sessionAttr("curvePointDTO", testCurvePointDTO1)
        .param("curveId", testCurvePointDTO1.getCurveId().toString())
        .param("term", testCurvePointDTO1.getTerm().toString())
        .param("value", testCurvePointDTO1.getValue().toString()))
        .andExpect(model().hasNoErrors())
        .andExpect(model().size(0))
        .andExpect(model().attributeDoesNotExist("curvePointDTO"))
        .andExpect(redirectedUrl("/curvePoint/list"))
        .andExpect(status().is(302));

        verify(curvePointService, times(1)).updateCurvePoint(anyInt(), any(CurvePointDTO.class));
    }

    // ********************************************************************


    @DisplayName("Url request /curvePoint/update/{id} - CurveIdNegative - "
    		+ " - Given a CurvePoint - CurveIdNegative-,"
    		+ " when POST /curvePoint/update/{id} action request,"
    		+ " then returns error & redirect /curvePoint/add page")    
    @Test
    public void testPostCurvePointValidateWithCurveIdNegative() throws Exception {
    	when(curvePointService.getAllCurvePoint()).thenReturn(curvePointDTOList);
//    	when(curvePointService..updateCurvePoint(anyInt(), any(CurvePointDTO.class))).thenReturn(any(CurvePointDTO.class));
        
    	MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/curvePoint/update/1")
        .sessionAttr("curvePointDTO", testCurvePointDTO1)
        .param("curveId", "-1")
        .param("term", testCurvePointDTO1.getTerm().toString())
        .param("value", testCurvePointDTO1.getValue().toString()))
        .andExpect(model().hasErrors())
        .andExpect(model().size(2))
        .andExpect(model().attributeExists("curvePointDTO"))
        .andExpect(view().name("curvePoint/update"))
        .andExpect(status().is(200))
        .andReturn();

        verify(curvePointService, times(0)).getAllCurvePoint();
        verify(curvePointService, times(0)).updateCurvePoint(anyInt(), any(CurvePointDTO.class));
        

        String content = result.getResponse().getContentAsString();
        
        assertThat(content).contains("The value must be positive");
              
    }

    // ********************************************************************




    @DisplayName("Url request /curvePoint/update/{id} - CurveIdNull - "
    		+ " - Given a CurvePoint - CurveIdNull-,"
    		+ " when POST /curvePoint/update/{id} action request,"
    		+ " then returns error & redirect /curvePoint/add page")    
    @Test
    public void testPostCurvePointValidateWithCurveIdNull() throws Exception {
    	when(curvePointService.getAllCurvePoint()).thenReturn(curvePointDTOList);
//    	when(curvePointService..updateCurvePoint(anyInt(), any(CurvePointDTO.class))).thenReturn(any(CurvePointDTO.class));
        
    	MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/curvePoint/update/1")
        .sessionAttr("curvePointDTO", testCurvePointDTO1)
        .param("curveId", "")
        .param("term", testCurvePointDTO1.getTerm().toString())
        .param("value", testCurvePointDTO1.getValue().toString()))
        .andExpect(model().hasErrors())
        .andExpect(model().size(2))
        .andExpect(model().attributeExists("curvePointDTO"))
        .andExpect(view().name("curvePoint/update"))
        .andExpect(status().is(200))
        .andReturn();

        verify(curvePointService, times(0)).getAllCurvePoint();
        verify(curvePointService, times(0)).updateCurvePoint(anyInt(), any(CurvePointDTO.class));
        

        String content = result.getResponse().getContentAsString();
        
        assertThat(content).contains("CurveId is mandatory");
              
    }

    // ********************************************************************


    @DisplayName("Url request /curvePoint/update/{id} - Term Null - "
    		+ " - Given a CurvePoint - Term Null-,"
    		+ " when POST /curvePoint/update/{id} action request,"
    		+ " then returns error & redirect /curvePoint/add page")    
    @Test
    public void testPostCurvePointValidateWithTermNull() throws Exception {
    	when(curvePointService.getAllCurvePoint()).thenReturn(curvePointDTOList);
//    	when(curvePointService..updateCurvePoint(anyInt(), any(CurvePointDTO.class))).thenReturn(any(CurvePointDTO.class));
        
    	MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/curvePoint/update/1")
        .sessionAttr("curvePointDTO", testCurvePointDTO1)
        .param("curveId", testCurvePointDTO1.getCurveId().toString())
        .param("term", "")
        .param("value", testCurvePointDTO1.getValue().toString()))
        .andExpect(model().hasErrors())
        .andExpect(model().size(2))
        .andExpect(model().attributeExists("curvePointDTO"))
        .andExpect(view().name("curvePoint/update"))
        .andExpect(status().is(200))
        .andReturn();

        verify(curvePointService, times(0)).getAllCurvePoint();
        verify(curvePointService, times(0)).updateCurvePoint(anyInt(), any(CurvePointDTO.class));
        

        String content = result.getResponse().getContentAsString();
        
        assertThat(content).contains("Term is mandatory");
              
    }

    // ********************************************************************



    @DisplayName("Url request /curvePoint/update/{id} - Term Negative - "
    		+ " - Given a CurvePoint - Term Negative -,"
    		+ " when POST /curvePoint/update/{id} action request,"
    		+ " then returns error & redirect /curvePoint/add page")    
    @Test
    public void testPostCurvePointValidateWithTermNegative() throws Exception {
    	when(curvePointService.getAllCurvePoint()).thenReturn(curvePointDTOList);
//    	when(curvePointService..updateCurvePoint(anyInt(), any(CurvePointDTO.class))).thenReturn(any(CurvePointDTO.class));
        
    	MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/curvePoint/update/1")
        .sessionAttr("curvePointDTO", testCurvePointDTO1)
        .param("curveId", testCurvePointDTO1.getCurveId().toString())
        .param("term", "-10.0")
        .param("value", testCurvePointDTO1.getValue().toString()))
        .andExpect(model().hasErrors())
        .andExpect(model().size(2))
        .andExpect(model().attributeExists("curvePointDTO"))
        .andExpect(view().name("curvePoint/update"))
        .andExpect(status().is(200))
        .andReturn();

        verify(curvePointService, times(0)).getAllCurvePoint();
        verify(curvePointService, times(0)).updateCurvePoint(anyInt(), any(CurvePointDTO.class));
        

        String content = result.getResponse().getContentAsString();
        
        assertThat(content).contains("The value must be positive");
              
    }

    // ********************************************************************
    


    @DisplayName("Url request /curvePoint/update/{id} - Term Digits >10 - "
    		+ " - Given a CurvePoint - Term Digits >10 -,"
    		+ " when POST /curvePoint/update/{id} action request,"
    		+ " then returns error & redirect /curvePoint/add page")    
    @Test
    public void testPostCurvePointValidateWithTermMoreThan10Digits() throws Exception {
    	when(curvePointService.getAllCurvePoint()).thenReturn(curvePointDTOList);
//    	when(curvePointService..updateCurvePoint(anyInt(), any(CurvePointDTO.class))).thenReturn(any(CurvePointDTO.class));
        
    	MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/curvePoint/update/1")
        .sessionAttr("curvePointDTO", testCurvePointDTO1)
        .param("curveId", testCurvePointDTO1.getCurveId().toString())
        .param("term", "1000000000000.00")
        .param("value", testCurvePointDTO1.getValue().toString()))
        .andExpect(model().hasErrors())
        .andExpect(model().size(2))
        .andExpect(model().attributeExists("curvePointDTO"))
        .andExpect(view().name("curvePoint/update"))
        .andExpect(status().is(200))
        .andReturn();

        verify(curvePointService, times(0)).getAllCurvePoint();
        verify(curvePointService, times(0)).updateCurvePoint(anyInt(), any(CurvePointDTO.class));
        

        String content = result.getResponse().getContentAsString();
        
        assertThat(content).contains("Invalid number input value : Maximum digits allowed are 10 and with 2 decimals fractions");
              
    }

    // ********************************************************************



    @DisplayName("Url request /curvePoint/update/{id} - Term With Symbols - "
    		+ " - Given a CurvePoint - Term With Symbols -,"
    		+ " when POST /curvePoint/update/{id} action request,"
    		+ " then returns error & redirect /curvePoint/add page")    
    @Test
    public void testPostCurvePointValidateWithTermWithSymbols() throws Exception {
    	when(curvePointService.getAllCurvePoint()).thenReturn(curvePointDTOList);
//    	when(curvePointService..updateCurvePoint(anyInt(), any(CurvePointDTO.class))).thenReturn(any(CurvePointDTO.class));
        
    	MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/curvePoint/update/1")
        .sessionAttr("curvePointDTO", testCurvePointDTO1)
        .param("curveId", testCurvePointDTO1.getCurveId().toString())
        .param("term", "&&&&&&")
        .param("value", testCurvePointDTO1.getValue().toString()))
        .andExpect(model().hasErrors())
        .andExpect(model().size(2))
        .andExpect(model().attributeExists("curvePointDTO"))
        .andExpect(view().name("curvePoint/update"))
        .andExpect(status().is(200))
        .andReturn();

        verify(curvePointService, times(0)).getAllCurvePoint();
        verify(curvePointService, times(0)).updateCurvePoint(anyInt(), any(CurvePointDTO.class));
        

        String content = result.getResponse().getContentAsString();
        
        assertThat(content).contains("Failed to convert property value of type java.lang.String to required type java.lang.Double for property term");
              
    }

    // ********************************************************************
            
             
         
}
