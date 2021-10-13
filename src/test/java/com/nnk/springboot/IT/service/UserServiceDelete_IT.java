package com.nnk.springboot.IT.service;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.nnk.springboot.exception.DataNotFoundException;
import com.nnk.springboot.service.UserService;

@DisplayName("INTEGRATION TESTS - Service ==> User DELETE")
@SpringBootTest
@ActiveProfiles("test")
class UserServiceDelete_IT {


	@Autowired
    private UserService userService;


	// *******************************************************************	
    
	@DisplayName("Delete Users - "
			+ "GIVEN a USER  "
			+ "WHEN Requested DELETE User"
			+ "THEN returns expected user deleted")	    
    @Test
    public void testDeleteUser() throws Exception {
	
        // GIVEN
		 
        // WHEN
		 userService.deleteUser(1);
        
        // THEN
	    	assertThrows(DataNotFoundException.class, ()
	        		-> userService.deleteUser(1));
    
	}
   
	   
		// *******************************************************************	
		
	    @DisplayName("Check <Exception>"
			+ "GIVEN a User not exist "
			+ "WHEN Requested DELETE User "
			+ "THEN throws Exception")	    
		@Test
		public void testDeleteUserNotExists() throws Exception {

	    
	    	// WHEN // THEN
	    	assertThrows(DataNotFoundException.class, ()
	        		-> userService.deleteUser(100));
		} 

		// *******************************************************************	

}
