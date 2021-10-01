package com.nnk.springboot.UnitTests.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collections;
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
import com.nnk.springboot.domain.User;
import com.nnk.springboot.dto.UserDTO;
import com.nnk.springboot.exception.DataNotFoundException;
import com.nnk.springboot.repository.UserRepository;
import com.nnk.springboot.service.UserService;
import com.nnk.springboot.util.UserMapper;

@DisplayName("User Service GetUser List - UNIT TESTS")
@ExtendWith(MockitoExtension.class)
class UserServiceGetListTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;
    
    @Mock
    private UserMapper userMapper;
    
    private ObjectMapper objectMapper;
	
    private static UserDTO testUserDTO1, testUserDTO2;
    
    private static User testUser1, testUser2;
    
    private static List<UserDTO> userDTOList;
    
    private static List<User> userList;

    
    @BeforeEach
    public void setUp() {
        objectMapper = new ObjectMapper();
        testUserDTO1 = UserDTO.builder()
        		.username("Username")
        		.password("Password&1")
        		.fullname("Fullname")
        		.role("USER")
        		.build();
        
        testUserDTO2 = UserDTO.builder()
        		.username("Username")
        		.password("Password&1")
        		.fullname("Fullname")
        		.role("USER")
        		.build();
        
        userDTOList = Arrays.asList(testUserDTO1, testUserDTO2);   
        
        testUser1 = User.builder()
        		.username("Username")
        		.password("Password&1")
        		.fullname("Fullname")
        		.role("USER")
        		.build();
        
        testUser2 = User.builder()
        		.username("Username")
        		.password("Password&1")
        		.fullname("Fullname")
        		.role("USER")
        		.build();
        
        userList = Arrays.asList(testUser1, testUser2);   
        
    }
    

    

   	// *******************************************************************	
    @DisplayName("Test List Users")
    @Nested
    class TestAddNewUser {  
    	
        @BeforeEach
        public void init() {
        	
            when(userRepository
            		.findAll())
            .thenReturn(userList);
            
            when(userMapper
            		.toUserDTO(testUser1))
            .thenReturn(testUserDTO1);
            
            when(userMapper
            		.toUserDTO(testUser2))
            .thenReturn(testUserDTO2);
            
        }
        
        
        

    	// *******************************************************************	
        
        @DisplayName("Check <Execution Order>"
        		+ " - Given a User List,"
        		+ " when Get User List action request,"
        		+ " then all steps are executed in correct order and number of expected times")    
        @Test
        public void testGetUsersListExecutionOrderCheck() throws Exception {
   	
           // GIVEN

           // WHEN
           userService.getAllUser();
           
           // THEN
           InOrder inOrder = inOrder(userRepository, userMapper);
           inOrder.verify(userRepository).findAll();
           inOrder.verify(userMapper).toUserDTO(testUser1);
           inOrder.verify(userMapper).toUserDTO(testUser2);
           
           verify(userRepository, times(1)).findAll();
           verify(userMapper, times(1)).toUserDTO(testUser1);
           verify(userMapper, times(1)).toUserDTO(testUser2);
       
        }
   	
        
        

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
          
          @DisplayName("Check <Validate> match of both same record instance "
          		+ " - Given a User List,"
          		+ " when Get User List action request,"
          		+ " then USER added should be added and same as test record")   
          @Test
          public void testGetUsersListREsultMatchCheck() throws Exception {
     	
             // GIVEN

             // WHEN
             List<UserDTO> result = userService.getAllUser();
             
             // THEN
             assertEquals(userDTOList, result);
         
          }
     	
          
          

        	// *******************************************************************	
            
            @DisplayName("Check <Execution Order>"
            		+ " - Given a User List,"
            		+ " when Get User List action request,"
            		+ " then return expected No of Users")    
            @Test
            public void testGetUsersListRecordsNumberMatchCheck() throws Exception {
       	
               // GIVEN

               // WHEN
               List<UserDTO> result = userService.getAllUser();
               
               // THEN
               assertEquals(userDTOList.size(), result.size());
               assertEquals(2, result.size());

            }
 
    } 
    
    
 	// *******************************************************************	
 	
     @DisplayName("Check <Exception>"
 		+ "GIVEN List of Users null "
 		+ "WHEN Requested List Users"
 		+ "THEN throws Exception")	    
 	@Test
 	public void testGetUserListEmptyExceptionCheck() throws Exception {

     	when(userRepository.findAll()).thenReturn(Collections.emptyList());
     
     	// WHEN // THEN
     	assertThrows(DataNotFoundException.class, ()
         		-> userService.getAllUser());
 	}
     

  	// *******************************************************************	
  	
     
}
