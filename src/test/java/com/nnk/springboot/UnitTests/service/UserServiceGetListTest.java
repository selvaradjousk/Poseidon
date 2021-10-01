package com.nnk.springboot.UnitTests.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

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
import com.nnk.springboot.dto.UserDTO;
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
       
   	@DisplayName("List Users"
   			+ "GIVEN List of Users  "
   			+ "WHEN Requested List Users"
   			+ "THEN returns expected list of users")	    
       @Test
       public void testGetUserList() throws Exception {
   	
           // GIVEN
           when(userRepository.findAll()).thenReturn(userList);
           when(userMapper.toUserDTO(testUser1)).thenReturn(testUserDTO1);
           when(userMapper.toUserDTO(testUser2)).thenReturn(testUserDTO2);
           // WHEN
           List<UserDTO> result = userService.getAllUser();
           
           // THEN
           assertEquals(userDTOList, result);
           InOrder inOrder = inOrder(userRepository, userMapper);
           inOrder.verify(userRepository).findAll();
           inOrder.verify(userMapper).toUserDTO(testUser1);
           inOrder.verify(userMapper).toUserDTO(testUser2);
           
           verify(userRepository, times(1)).findAll();
           verify(userMapper, times(1)).toUserDTO(testUser1);
           verify(userMapper, times(1)).toUserDTO(testUser2);
       
   	}
   	
   	
}
