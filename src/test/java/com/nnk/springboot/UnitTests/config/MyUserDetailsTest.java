package com.nnk.springboot.UnitTests.config;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.nnk.springboot.config.MyUserDetails;
import com.nnk.springboot.domain.User;



/**
 * The Class MyUserDetailsTest.
 */

@DisplayName("MyUserDetails - Unit Tests")
class MyUserDetailsTest {

	
    @InjectMocks
    MyUserDetails testUserDetails1, testUserDetails2, toTest1, toTest2;	

    User user, toTestUser1, toTestUser2;
    
    @BeforeEach
    public void setUp() {


        testUserDetails1 = new MyUserDetails(
        		1,
        		"Username",
        		"Password&1",
        		new SimpleGrantedAuthority("admin"));
        
        user = User.builder()
        		.id(1)
        		.username("Username")
        		.password("Password&1")
        		.role(new SimpleGrantedAuthority("admin").toString()).build();

      toTestUser1 = new User(
    		  		1,
	    		  "Username",
	    		  "Password&1",
	    		  "Fullname",
	    		  "admin");

      toTestUser2 = new User(
    		  		1,
	    		  "Username",
	    		  "Password&1",
	    		  "Fullname",
	    		  "admin");
      
      testUserDetails1 = MyUserDetails.build(toTestUser1);
      testUserDetails2 = MyUserDetails.build(toTestUser2);
    }
    
    
  	// ********************************************************************

    @Test
    public void testSetUserDetails() {
    	
    	testUserDetails1 = MyUserDetails.build(toTestUser1);
        assertNotNull(testUserDetails1);
        assertEquals("Username", testUserDetails1.getUsername());
        assertEquals(Integer.valueOf(1), testUserDetails1.getId());
        assertEquals("Password&1", testUserDetails1.getPassword());
        assertEquals(List.of("admin").toString(), testUserDetails1.getAuthorities().toString());
    }

    @Test
    public void testUserDetailsBuild() {
        
        assertNotNull(testUserDetails2);
        assertEquals(testUserDetails1.getUsername(), testUserDetails2.getUsername());
        assertEquals(testUserDetails1.getId(), testUserDetails2.getId());
        assertEquals(testUserDetails1.getPassword(), testUserDetails2.getPassword());
        assertEquals(testUserDetails1.getAuthorities().toString(), testUserDetails2.getAuthorities().toString());
    }

    @Test
    public void testEquals() {
    	
        assertTrue(testUserDetails1.equals(testUserDetails2));
        assertFalse(testUserDetails1.equals(null));
        Object user = new Object();
        assertFalse(testUserDetails2.equals(user));
    }

    

	/**
	 * Test hash code.
	 */
	@Test
	void testHashCode() {
		assertEquals(
				(testUserDetails1.toString()).hashCode(),
				(testUserDetails1.toString()).hashCode());
	}


	/**
	 * Test equals object.
	 */
	@Test
	final void testEqualsObject() {
		assertNotNull(testUserDetails1.toString());
		assertNotNull(testUserDetails2.toString());
	}

}