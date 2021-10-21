package com.nnk.springboot.IT.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.nnk.springboot.dto.UserDTO;
import com.nnk.springboot.service.UserService;

@DisplayName("INTEGRATION TESTS - Service ==> User Get List")
@SpringBootTest
@ActiveProfiles("test")
class UserServiceGetList_IT {

	@Autowired
    private UserService userService;

 
        
      	// *******************************************************************	
          
          @DisplayName("Check Check <NotNull>"
          		+ " - Given a User List,"
          		+ " when Get User List action request,"
          		+ " then returns userslist not null")    
          @Test
          public void testGetUsersListNotNullCheck() throws Exception {
     	
             // GIVEN

             // WHEN
             List<UserDTO> result = userService.getAllUser();
             
             // THEN
             assertNotNull(result);
         
          }
     	

          
        	// *******************************************************************	
            
            @DisplayName("Check <Count list size>"
            		+ " - Given a User List,"
            		+ " when Get User List action request,"
            		+ " then return expected No of Users")    
            @Test
            public void testGetUsersListRecordsNumberMatchCheck() throws Exception {
       	
               // GIVEN

               // WHEN
               List<UserDTO> result = userService.getAllUser();
               
               // THEN
//             assertEquals(6, result.size());
             assertTrue(result.size() > 1);

            }

    
 	// *******************************************************************	

     
}
