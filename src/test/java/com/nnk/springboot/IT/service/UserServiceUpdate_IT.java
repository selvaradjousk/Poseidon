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

@DisplayName("INTEGRATION TESTS - Service ==> User Update")
@SpringBootTest
@ActiveProfiles("test")
class UserServiceUpdate_IT {


	@Autowired
    private UserService userService;

	@Autowired
    private BCryptPasswordEncoder passwordEncoder;
	
    private ObjectMapper objectMapper;
	
    private static UserDTO userUpdatedDTO, userExists;
    

        @BeforeEach
        public void init() {

	        
	        userUpdatedDTO = UserDTO.builder()
	        		.username("Username")
	        		.password("Password&1")
	        		.fullname("FullnameUpdate")
	        		.role("USER")
	        		.build();
	        

	        userExists = UserDTO.builder()
	        		.username("Username12")
	        		.password("Password&1")
	        		.fullname("Fullname")
	        		.role("USER")
	        		.build();

	

        }




	// *******************************************************************	
    
//    @DisplayName("Check <NotNull>"
//    		+ " - Given a existing User,"
//    		+ " when UPDATE USER action request,"
//    		+ " then USER should not be null")	    
//	    @Test
//	    public void testUpdateUserNotNullCheck() {
//			
//
//	        UserDTO userUpdated = userService
//	        		.updateUser(1, new UserDTO(
//	        				"Username",
//	        				"Password&1",
//	        				"FullnameUpdate",
//	        				"USER"));
//
//	        assertNotNull(userUpdated);
//	    }


	// *******************************************************************
    
//	   
//	 @Test
//	 @DisplayName("Check <Validate> match of both same record instance "
//	 		+ " - Given a existing User,"
//	 		+ " when UPDATE USER action request,"
//	 		+ " then USER added should be added and same as test record")
//	 public void testAddNewUserReturnResultMatch() {
//				
//	
//	     UserDTO userUpdated = userService
//	     		.updateUser(1, new UserDTO(
//	     				"Username",
//	     				"Password&1",
//	     				"FullnameUpdate",
//	     				"USER"));
//	
//		        assertEquals(userUpdatedDTO.getUsername(), userUpdated.getUsername());
//
//		        assertEquals(
//		        		true,
//		        		passwordEncoder.matches("Password&1", userUpdated.getPassword()));
//		        
//		        }

    
    // *******************************************************************
	
    @DisplayName("ERROR ADD EXISTING USER for non existing USER data"
    		+ " - Given a non existing USER,"
    		+ " when UPDATE USER action request,"
    		+ " then USER entry should respond"
    		+ " with Data Not Found Exception")
	@Test
	public void testUpdateUserForNonExistingUserData() throws Exception {

    
    	// WHEN // THEN
    	assertThrows(DataNotFoundException.class, ()
        		-> userService.updateUser(100, userExists));
	}
     
    // *******************************************************************	
}
