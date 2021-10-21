package com.nnk.springboot.IT.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nnk.springboot.dto.UserDTO;
import com.nnk.springboot.exception.DataNotFoundException;
import com.nnk.springboot.service.UserService;

@DisplayName("INTEGRATION TESTS - Service ==> User GET BY ID")
@SpringBootTest
@ActiveProfiles("test")
class UserServiceGetById_IT {

	@Autowired
    private UserService userService;

	@Autowired
    private BCryptPasswordEncoder passwordEncoder;
	
    private ObjectMapper objectMapper;
	
    private static UserDTO testUserDTO1;

    
    
    @BeforeEach
    public void setUp() {
        objectMapper = new ObjectMapper();
        testUserDTO1 = UserDTO.builder()
        		.id(4)
        		.username("Username")
        		.password("Password&1")
        		.fullname("Fullname")
        		.role("USER")
        		.build();
  
       
    }
  

	// *******************************************************************	
    
    @DisplayName("Check <NotNull>"
    		+ " - Given a existing User,"
    		+ " when GET USER By ID action request,"
    		+ " then USER should not be null")	    
	    @Test
	    public void testUserByIdNotNullCheck() {
			

    		UserDTO result = userService
    				.getUserById(4);

	        assertNotNull(result);
	    }

	// *******************************************************************			
	   
    @Test
    @DisplayName("Check <Validate> match of both same record instance "
    		+ " - Given a existing User,"
    		+ " when GET USER By ID action request,"
    		+ " then USER ID same as test record")
    public void testAddNewPersonReturnResultMatch() {
			
    	UserDTO result = userService
    			.getUserById(4);

        assertEquals(
        		passwordEncoder.matches("Password&1", testUserDTO1.getPassword()),
        		passwordEncoder.matches("Password&1", result.getPassword()));
	    assertEquals("user4", result.getUsername());
	    assertEquals(4, result.getId());
    }

    
	
	// *******************************************************************	
	
    @DisplayName("ERROR GET EXISTING USER by ID for non existing USER data"
    		+ " - Given a non existing USER,"
    		+ " when GET USER By ID action request,"
    		+ " then USER entry should respond"
    		+ " with Data Not Found Exception")
	@Test
	public void testGetUserByIdNonExistingUserData() throws Exception {

    
    	// WHEN // THEN
    	assertThrows(DataNotFoundException.class, ()
        		-> userService.getUserById(100));
	}
    
	// *******************************************************************	   
}
