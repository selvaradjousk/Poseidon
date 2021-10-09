package com.nnk.springboot.UnitTests.config;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithMockUser;

import com.nnk.springboot.config.MyUserDetails;
import com.nnk.springboot.config.MyUserDetailsService;
import com.nnk.springboot.domain.User;
import com.nnk.springboot.repository.UserRepository;

@DisplayName("MyUserDetailService - H2 DB TEST ")
@ExtendWith(MockitoExtension.class)
class MyUserDetailsServiceTest {

	
	@Mock
	UserRepository userRepository;

	@InjectMocks
	MyUserDetailsService myUserDetailService;
	
	  
	
    private static User testUser1, testUser2;
    User toTestUser1 = new User("Username", "Password", "Fullname", "admin");
    User toTestUser2 = new User("Username", "Password", "Fullname", "admin");


	/** The to test 1. */
	UserDetails toTest1 = new MyUserDetails(toTestUser1);

	/** The to test 2. */
	UserDetails toTest2 = new MyUserDetails(toTestUser2);

	
    @Test
    @DisplayName("Given - Valid User,"
    		+ " When loadUserByUsername,"
    		+ " THEN Asserts Not foundExpected")
    public void testLoadUserByUsernameNotFound() {

    	assertThrows(org.springframework.security.core.userdetails.UsernameNotFoundException.class, ()
	     		  -> myUserDetailService.loadUserByUsername("Username"));
      	
    }

    
	  
  	// *******************************************************************

	  @DisplayName("User Details Test Null - "
				+ "GIVEN user email null"
				+ "WHEN loadUserByUsername"
				+ "THEN returns Exception")
	  @Test
	  public void testLoadUserByUsernameNull(){
	
		    assertThrows(org.springframework.security.core.userdetails.UsernameNotFoundException.class, ()
		     		  -> myUserDetailService.loadUserByUsername(null));		
  }  
  
	    
	  
	  	// *******************************************************************

		  @DisplayName("User Details Test invalid username- "
					+ "GIVEN user email invalid"
					+ "WHEN loadUserByUsername"
					+ "THEN returns Exception")
		  @Test
		  public void testLoadUserByUsernameinvalidName(){
		
			    assertThrows(org.springframework.security.core.userdetails.UsernameNotFoundException.class, ()
			     		  -> myUserDetailService.loadUserByUsername("SDFSDFSDFSQFDSQDF"));		
	  }  
	  
	       
	  
	  	// *******************************************************************

		  @DisplayName("User Details Test username ButNotFound- "
					+ "GIVEN user email ButNotFound"
					+ "WHEN loadUserByUsername"
					+ "THEN returns Exception")
		  @Test
		  public void testLoadUserByUsernameValidEmailButNotFound(){
		
			    assertThrows(org.springframework.security.core.userdetails.UsernameNotFoundException.class, ()
			     		  -> myUserDetailService.loadUserByUsername("Username11"));		
	  }  
	  
	        
}