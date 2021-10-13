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
import com.nnk.springboot.exception.DataAlreadyExistsException;
import com.nnk.springboot.service.UserService;

@DisplayName("INTEGRATION TESTS - Service ==> User Add")
@SpringBootTest
@ActiveProfiles("test")
class UserServiceAdd_IT {


	@Autowired
    private UserService userService;
	
	@Autowired
	BCryptPasswordEncoder passwordEncoder;

    private ObjectMapper objectMapper;
	
    private static UserDTO userExists, userDTOAfterSave;

    	
        @BeforeEach
        public void init() {


	        userExists = UserDTO.builder()
	        		.username("Username12")
	        		.password("Password&1")
	        		.fullname("Fullname")
	        		.role("USER")
	        		.build();
	        
	        userDTOAfterSave = UserDTO.builder()
	        		.id(9)
	        		.username("Username")
	        		.password(passwordEncoder.encode("Password&1"))
	        		.fullname("Fullname")
	        		.role("USER")
	        		.build();

        }



	// *******************************************************************	
    
    
    @DisplayName("Check <NotNull>"
    		+ " - Given a new User,"
    		+ " when ADD USER action request,"
    		+ " then USER should not be null")	    
	    @Test
	    public void testAddNewUserNotNullCheck() {
			

	        UserDTO userSaved = userService
	        		.addUser(new UserDTO(
	        				"Username10",
	        				"Password&1",
	        				"Fullname",
	        				"USER"));

	        assertNotNull(userSaved);
	    }

	// *******************************************************************			
		     
	   
 @Test
 @DisplayName("Check <Validate> match of both same record instance "
 		+ " - Given a new User,"
 		+ " when ADD USER action request,"
 		+ " then USER added should be added and same as test record")
 public void testAddNewUserReturnResultMatch() {
			

	        UserDTO userSaved = userService
	        		.addUser(new UserDTO("Username", "Password&1", "Fullname", "USER"));

	        assertEquals(userDTOAfterSave.getUsername(), userSaved.getUsername());

	        assertEquals(
	        		passwordEncoder.matches("Password&1", userDTOAfterSave.getPassword()),
	        		passwordEncoder.matches("Password&1", userSaved.getPassword()));
	    }

 
	// *******************************************************************	


    @DisplayName("ERROR ADD NEW USER for existing USER data"
    		+ " - Given a existing USER,"
    		+ " when ADD USER action request,"
    		+ " then USER entry should respond"
    		+ " with Data Already Exists Exception")
	@Test
	public void testAddUserForExistingUserData() throws Exception {

    	userService
        		.addUser(new UserDTO("Username12", "Password&1", "Fullname", "USER"));
    	// WHEN // THEN
    	assertThrows(DataAlreadyExistsException.class, ()
        		-> userService.addUser(userExists));
	}
    
	// *******************************************************************	

}
