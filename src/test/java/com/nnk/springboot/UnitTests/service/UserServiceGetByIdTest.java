package com.nnk.springboot.UnitTests.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
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
import com.nnk.springboot.repository.UserRepository;
import com.nnk.springboot.service.UserService;
import com.nnk.springboot.util.UserMapper;

@DisplayName("User Service GET BY ID - UNIT TESTS")
@ExtendWith(MockitoExtension.class)
class UserServiceGetByIdTest {

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
	
    private static UserDTO testUserDTO1, testUserDTO2;
    
    private static User testUser1, testUser2;
    
    
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

    @DisplayName("Test GET USER BY IDENTITY")
    @Nested
    class TestGetUserById  {  
    	
        @BeforeEach
        public void init() {
        	
            when(userRepository
            		.findById(anyInt()))
            .thenReturn(java.util.Optional.ofNullable(testUser1));
            
            when(userMapper
            		.toUserDTO(any(User.class)))
            .thenReturn(testUserDTO1);  

        }

	// *******************************************************************	
    
    @DisplayName("Check <Execution Order>"
    		+ " - Given a User,"
    		+ " when GET USER BY ID action request,"
    		+ " then all steps are executed in correct order and number of expected times")    
    @Test
    public void testGetUserByIdExecutionOrderCheck() throws Exception {
		

    	userService.getUserById(1);

        InOrder inOrder = inOrder(userRepository, userMapper);
        inOrder.verify(userRepository).findById(anyInt());
        inOrder.verify(userMapper).toUserDTO(any(User.class));
        
        verify(userRepository, times(1)).findById(anyInt());
        verify(userMapper, times(1)).toUserDTO(any(User.class));
    }



	// *******************************************************************	
    
    @DisplayName("Check <NotNull>"
    		+ " - Given a existing User,"
    		+ " when GET USER By ID action request,"
    		+ " then USER should not be null")	    
	    @Test
	    public void testUserByIdNotNullCheck() {
			

    		UserDTO result = userService.getUserById(1);

	        assertNotNull(result);
	    }

	// *******************************************************************			
	   
    @Test
    @DisplayName("Check <Validate> match of both same record instance "
    		+ " - Given a existing User,"
    		+ " when GET USER By ID action request,"
    		+ " then USER ID same as test record")
    public void testAddNewPersonReturnResultMatch() {
			
    	UserDTO result = userService.getUserById(1);

    	assertEquals(result, testUserDTO1);
	    assertThat(result).usingRecursiveComparison().isEqualTo(testUserDTO1);
	    assertEquals("UserName", result.getUsername());
	    assertEquals(1, result.getId());
    }
     
    }

    
}
