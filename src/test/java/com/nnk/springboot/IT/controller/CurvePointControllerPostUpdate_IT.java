 package com.nnk.springboot.IT.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nnk.springboot.dto.CurvePointDTO;

@DisplayName("INTEGRATION TESTS - Controller < CURVEPOINT > -VALIDATE")
@AutoConfigureMockMvc
@SpringBootTest
@ActiveProfiles("test")
class CurvePointControllerPostUpdate_IT {

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper;
	
    private static CurvePointDTO testCurvePointDTO1;

    @BeforeEach
    public void setUp() {

        objectMapper = new ObjectMapper();
        testCurvePointDTO1 = CurvePointDTO.builder()
        		.curveId(1)
        		.term(10.0)
        		.value(10.0)
        		.build();


    }
    
   	// ********************************************************************


     @DisplayName(" Url request /curvePoint/update/{id} - Without Authentication"
     		+ " - Given a CurvePoint,"
     		+ " when POST /curvePoint/update/{id} action request,"
     		+ " then returns Error Authentication required")    
     @Test
     public void testPostCurvePointValidateWithoutAuthentication() throws Exception {

         
         mockMvc.perform(MockMvcRequestBuilders.post("/curvePoint/update/1"))
     	        .andExpect(status().is(401))
     	        .andDo(MockMvcResultHandlers.print())
     	        .andExpect(status().isUnauthorized())
     	        .andExpect(status().reason(containsString("Full authentication is required to access this resource")))
     	        .andExpect(unauthenticated());

     }
   
  	// ********************************************************************

     @WithMockUser(username = "admin", authorities = { "ADMIN", "USER"})
    @DisplayName(" Url request /curvePoint/update/{id} - With Authentication"
    		+ " - Given a CurvePoint,"
    		+ " when POST /curvePoint/update/{id} action request,"
    		+ " then returns redirect /curvePoint/update/{id} page")    
    @Test
    public void testPostCurvePointValidateWithAuthentication() throws Exception {

        
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

    }

    // ********************************************************************

     @WithMockUser(username = "admin", authorities = { "ADMIN", "USER"})
    @DisplayName("Url request /curvePoint/update/{id} - CurveIdNegative - "
    		+ " - Given a CurvePoint - CurveIdNegative-,"
    		+ " when POST /curvePoint/update/{id} action request,"
    		+ " then returns error & redirect /curvePoint/add page")    
    @Test
    public void testPostCurvePointValidateWithCurveIdNegative() throws Exception {
        
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
       

        String content = result.getResponse().getContentAsString();
        
        assertThat(content).contains("The value must be positive");
              
    }

    // ********************************************************************



     @WithMockUser(username = "admin", authorities = { "ADMIN", "USER"})
    @DisplayName("Url request /curvePoint/update/{id} - CurveIdNull - "
    		+ " - Given a CurvePoint - CurveIdNull-,"
    		+ " when POST /curvePoint/update/{id} action request,"
    		+ " then returns error & redirect /curvePoint/add page")    
    @Test
    public void testPostCurvePointValidateWithCurveIdNull() throws Exception {

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

        String content = result.getResponse().getContentAsString();
        
        assertThat(content).contains("CurveId is mandatory");
              
    }

    // ********************************************************************

     @WithMockUser(username = "admin", authorities = { "ADMIN", "USER"})
    @DisplayName("Url request /curvePoint/update/{id} - Term Null - "
    		+ " - Given a CurvePoint - Term Null-,"
    		+ " when POST /curvePoint/update/{id} action request,"
    		+ " then returns error & redirect /curvePoint/add page")    
    @Test
    public void testPostCurvePointValidateWithTermNull() throws Exception {

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


        String content = result.getResponse().getContentAsString();
        
        assertThat(content).contains("Term is mandatory");
              
    }

    // ********************************************************************


     @WithMockUser(username = "admin", authorities = { "ADMIN", "USER"})
    @DisplayName("Url request /curvePoint/update/{id} - Term Negative - "
    		+ " - Given a CurvePoint - Term Negative -,"
    		+ " when POST /curvePoint/update/{id} action request,"
    		+ " then returns error & redirect /curvePoint/add page")    
    @Test
    public void testPostCurvePointValidateWithTermNegative() throws Exception {

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

        String content = result.getResponse().getContentAsString();
        
        assertThat(content).contains("The value must be positive");
              
    }

    // ********************************************************************
    

     @WithMockUser(username = "admin", authorities = { "ADMIN", "USER"})
    @DisplayName("Url request /curvePoint/update/{id} - Term Digits >10 - "
    		+ " - Given a CurvePoint - Term Digits >10 -,"
    		+ " when POST /curvePoint/update/{id} action request,"
    		+ " then returns error & redirect /curvePoint/add page")    
    @Test
    public void testPostCurvePointValidateWithTermMoreThan10Digits() throws Exception {

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

        String content = result.getResponse().getContentAsString();
        
        assertThat(content).contains("Invalid number input value : Maximum digits allowed are 10 and with 2 decimals fractions");
              
    }

    // ********************************************************************


     @WithMockUser(username = "admin", authorities = { "ADMIN", "USER"})
    @DisplayName("Url request /curvePoint/update/{id} - Term With Symbols - "
    		+ " - Given a CurvePoint - Term With Symbols -,"
    		+ " when POST /curvePoint/update/{id} action request,"
    		+ " then returns error & redirect /curvePoint/add page")    
    @Test
    public void testPostCurvePointValidateWithTermWithSymbols() throws Exception {

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

        String content = result.getResponse().getContentAsString();
        
        assertThat(content).contains("Failed to convert property value of type java.lang.String to required type java.lang.Double for property term");
              
    }

    // ********************************************************************


     @WithMockUser(username = "admin", authorities = { "ADMIN", "USER"})
    @DisplayName("Url request /curvePoint/update/{id} - Value Null - "
    		+ " - Given a CurvePoint - Value Null-,"
    		+ " when POST /curvePoint/update/{id} action request,"
    		+ " then returns error & redirect /curvePoint/add page")    
    @Test
    public void testPostCurvePointValidateWithValueNull() throws Exception {

    	MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/curvePoint/update/1")
	        .sessionAttr("curvePointDTO", testCurvePointDTO1)
	        .param("curveId", testCurvePointDTO1.getCurveId().toString())
	        .param("term", testCurvePointDTO1.getTerm().toString())
	        .param("value", ""))
	        .andExpect(model().hasErrors())
	        .andExpect(model().size(2))
	        .andExpect(model().attributeExists("curvePointDTO"))
	        .andExpect(view().name("curvePoint/update"))
	        .andExpect(status().is(200))
	        .andReturn();

        String content = result.getResponse().getContentAsString();
        
        assertThat(content).contains("Value is mandatory");
              
    }

    // ********************************************************************

     @WithMockUser(username = "admin", authorities = { "ADMIN", "USER"})
    @DisplayName("Url request /curvePoint/update/{id} - Value Negative - "
    		+ " - Given a CurvePoint - Value Negative -,"
    		+ " when POST /curvePoint/update/{id} action request,"
    		+ " then returns error & redirect /curvePoint/add page")    
    @Test
    public void testPostCurvePointValidateWithValueNegative() throws Exception {

    	MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/curvePoint/update/1")
	        .sessionAttr("curvePointDTO", testCurvePointDTO1)
	        .param("curveId", testCurvePointDTO1.getCurveId().toString())
	        .param("term", testCurvePointDTO1.getTerm().toString())
	        .param("value", "-10.0"))
	        .andExpect(model().hasErrors())
	        .andExpect(model().size(2))
	        .andExpect(model().attributeExists("curvePointDTO"))
	        .andExpect(view().name("curvePoint/update"))
	        .andExpect(status().is(200))
	        .andReturn();

        String content = result.getResponse().getContentAsString();
        
        assertThat(content).contains("The value must be positive");
              
    }

    // ********************************************************************

     @WithMockUser(username = "admin", authorities = { "ADMIN", "USER"})
    @DisplayName("Url request /curvePoint/update/{id} - Value Digits >10 - "
    		+ " - Given a CurvePoint - Value Digits >10 -,"
    		+ " when POST /curvePoint/update/{id} action request,"
    		+ " then returns error & redirect /curvePoint/add page")    
    @Test
    public void testPostCurvePointValidateWithValueMoreThan10Digits() throws Exception {

    	MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/curvePoint/update/1")
	        .sessionAttr("curvePointDTO", testCurvePointDTO1)
	        .param("curveId", testCurvePointDTO1.getCurveId().toString())
	        .param("term", testCurvePointDTO1.getTerm().toString())
	        .param("value", "1000000000000.00"))
	        .andExpect(model().hasErrors())
	        .andExpect(model().size(2))
	        .andExpect(model().attributeExists("curvePointDTO"))
	        .andExpect(view().name("curvePoint/update"))
	        .andExpect(status().is(200))
	        .andReturn();

        String content = result.getResponse().getContentAsString();
        
        assertThat(content).contains("Invalid number input value : Maximum digits allowed are 10 and with 2 decimals fractions");
              
    }

    // ********************************************************************


     @WithMockUser(username = "admin", authorities = { "ADMIN", "USER"})
    @DisplayName("Url request /curvePoint/update/{id} - Value With Symbols - "
    		+ " - Given a CurvePoint - Value With Symbols -,"
    		+ " when POST /curvePoint/update/{id} action request,"
    		+ " then returns error & redirect /curvePoint/add page")    
    @Test
    public void testPostCurvePointValidateWithValueWithSymbols() throws Exception {

    	MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/curvePoint/update/1")
	        .sessionAttr("curvePointDTO", testCurvePointDTO1)
	        .param("curveId", testCurvePointDTO1.getCurveId().toString())
	        .param("term", testCurvePointDTO1.getTerm().toString())
	        .param("value", "&&&&&&"))
	        .andExpect(model().hasErrors())
	        .andExpect(model().size(2))
	        .andExpect(model().attributeExists("curvePointDTO"))
	        .andExpect(view().name("curvePoint/update"))
	        .andExpect(status().is(200))
	        .andReturn();

        String content = result.getResponse().getContentAsString();
        
        assertThat(content).contains("Failed to convert property value of type java.lang.String to required type java.lang.Double for property value");
              
    }

    // ********************************************************************

         
}
