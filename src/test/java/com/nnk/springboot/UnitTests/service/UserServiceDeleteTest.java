package com.nnk.springboot.UnitTests.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nnk.springboot.domain.User;
import com.nnk.springboot.exception.DataNotFoundException;
import com.nnk.springboot.repository.UserRepository;
import com.nnk.springboot.service.UserService;
import com.nnk.springboot.util.UserMapper;

@DisplayName("User Service DELETE - UNIT TESTS")
@ExtendWith(MockitoExtension.class)
class UserServiceDeleteTest {


    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;
    
    @Mock
    private UserMapper userMapper;
	
    private ObjectMapper objectMapper;
	
    private static User testUser1;
    
   
    @BeforeEach
    public void setUp() {
        objectMapper = new ObjectMapper();
     
        
        testUser1 = User.builder()
        		.username("Username")
        		.password("Password&1")
        		.fullname("Fullname")
        		.role("USER")
        		.build();
        
    }
    
    

	// *******************************************************************	
    
	@DisplayName("Delete Users - "
			+ "GIVEN a USER  "
			+ "WHEN Requested DELETE User"
			+ "THEN returns expected user deleted")	    
    @Test
    public void testDeleteUser() throws Exception {
	
        // GIVEN
		 when(userRepository
				 .findById(anyInt()))
		 .thenReturn(java.util.Optional.ofNullable(testUser1));
		 
        // WHEN
		 userService.deleteUser(anyInt());
        
        // THEN
	       InOrder inOrder = inOrder(userRepository);
	       inOrder.verify(userRepository).findById(anyInt());
	       inOrder.verify(userRepository).deleteById(anyInt());
	       
	       verify(userRepository, times(1)).findById(anyInt());
	       verify(userRepository, times(1)).deleteById(anyInt());
    
	}
   
	   
		// *******************************************************************	
		
	    @DisplayName("Check <Exception>"
			+ "GIVEN a User not exist "
			+ "WHEN Requested DELETE User "
			+ "THEN throws Exception")	    
		@Test
		public void testDeleteUserNotExists() throws Exception {

	    	when(userRepository
	    			.findById(anyInt()))
	    	.thenReturn(java.util.Optional.empty());
	    
	    	// WHEN // THEN
	    	assertThrows(DataNotFoundException.class, ()
	        		-> userService.deleteUser(anyInt()));
		} 

		// *******************************************************************	

}
