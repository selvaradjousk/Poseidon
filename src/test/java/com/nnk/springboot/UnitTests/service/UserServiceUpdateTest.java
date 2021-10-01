package com.nnk.springboot.UnitTests.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nnk.springboot.domain.User;
import com.nnk.springboot.dto.UserDTO;
import com.nnk.springboot.exception.DataNotFoundException;
import com.nnk.springboot.repository.UserRepository;
import com.nnk.springboot.service.UserService;
import com.nnk.springboot.util.UserMapper;

@DisplayName("User Service Update User- UNIT TESTS")
@ExtendWith(MockitoExtension.class)
class UserServiceUpdateTest {


    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;
    
    @Mock
    private UserMapper userMapper;
    
    @Mock
    private BCryptPasswordEncoder passwordEncoder;
	
    @Mock
    private ObjectMapper objectMapper;
	
    private static UserDTO testUserDTO1, testUserDTO2, userUpdatedDTO;
    
    private static User testUser1, testUser2, userUpdated;
    
    
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
        
       
    }
  
    // ***********************************************************************************

    @DisplayName("Test UPDATE New USER")
    @Nested
    class TestUpdateNewUser {  
    	
        @BeforeEach
        public void init() {
        	
 	       userUpdated = User.builder()
	        		.id(1)
	        		.username("Username")
	        		.password("Password&1")
	        		.fullname("FullnameUpdate")
	        		.role("USER")
	        		.build();
	        
	        userUpdatedDTO = UserDTO.builder()
	        		.username("Username")
	        		.password("PasswordEncoded")
	        		.fullname("FullnameUpdate")
	        		.role("USER")
	        		.build();
	        
	   when(userRepository
			   .findById(anyInt()))
	   .thenReturn(java.util.Optional.ofNullable(testUser1));
	   
       when(userMapper
    		   .toUser(any(UserDTO.class)))
       .thenReturn(new User("Username", "Password&1", "FullnameUpdate", "USER"));
       
       when(passwordEncoder
    		   .encode(anyString()))
       .thenReturn("PasswordEncoded");
       
       when(userRepository
    		   .save(any(User.class)))
       .thenReturn(userUpdated);
       
       when(userMapper
    		   .toUserDTO(any(User.class)))
       .thenReturn(userUpdatedDTO);

        }

	// *******************************************************************	
    
    @DisplayName("Check <Execution Order>"
    		+ " - Given a User,"
    		+ " when UPDATE USER action request,"
    		+ " then all steps are executed in correct order and number of expected times")    
    @Test
    public void testUpdateUserExecutionOrderCheck() throws Exception {
		

        userService.updateUser(1, new UserDTO(
        		"Username",
        		"Password&1",
        		"FullnameUpdate",
        		"USER"));

        InOrder inOrder = inOrder(userRepository, passwordEncoder, userMapper);
        inOrder.verify(userRepository, times(2)).findById(anyInt());
        inOrder.verify(passwordEncoder).encode(anyString());
        inOrder.verify(userRepository).save(any(User.class));
        inOrder.verify(userMapper).toUserDTO(any(User.class));
        
        verify(userRepository, times(2)).findById(anyInt());
        verify(passwordEncoder, times(1)).encode(anyString());
        verify(userRepository, times(1)).save(any(User.class));
        verify(userMapper, times(1)).toUserDTO(any(User.class));
    }



	// *******************************************************************	
    
    @DisplayName("Check <NotNull>"
    		+ " - Given a existing User,"
    		+ " when UPDATE USER action request,"
    		+ " then USER should not be null")	    
	    @Test
	    public void testUpdateUserNotNullCheck() {
			

	        UserDTO userUpdated = userService
	        		.updateUser(1, new UserDTO(
	        				"Username",
	        				"Password&1",
	        				"FullnameUpdate",
	        				"USER"));

	        assertNotNull(userUpdated);
	    }


	// *******************************************************************
    
	   
	 @Test
	 @DisplayName("Check <Validate> match of both same record instance "
	 		+ " - Given a existing User,"
	 		+ " when UPDATE USER action request,"
	 		+ " then USER added should be added and same as test record")
	 public void testAddNewPersonReturnResultMatch() {
				
	
	     UserDTO userUpdated = userService
	     		.updateUser(1, new UserDTO(
	     				"Username",
	     				"Password&1",
	     				"FullnameUpdate",
	     				"USER"));
	
		        assertEquals(userUpdatedDTO, userUpdated);
		        assertThat(userUpdated).usingRecursiveComparison().isEqualTo(userUpdatedDTO);
		    }
 

    }

    
    // *******************************************************************
	
    @DisplayName("ERROR ADD EXISTING USER for non existing USER data"
    		+ " - Given a non existing USER,"
    		+ " when UPDATE USER action request,"
    		+ " then USER entry should respond"
    		+ " with Data Not Found Exception")
	@Test
	public void testUpdateUserForNonExistingUserData() throws Exception {

    	when(userRepository
    			.findById(anyInt()))
    	.thenReturn(java.util.Optional.empty());
    
    	// WHEN // THEN
    	assertThrows(DataNotFoundException.class, ()
        		-> userService.updateUser(1, testUserDTO1));
	}
     
    // *******************************************************************	
}
