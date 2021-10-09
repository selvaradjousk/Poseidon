package com.nnk.springboot.UnitTests.config;


import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.nnk.springboot.config.MyUserDetails;
import com.nnk.springboot.domain.User;



/**
 * The Class MyUserDetailsTest.
 */
@DisplayName("MyUserDetails - Unit Tests")
class MyUserDetailsTest {

	  
	
    private static User testUser1, testUser2;
    User toTestUser1 = new User("Username", "Password", "Fullname", "admin");
    User toTestUser2 = new User("Username", "Password", "Fullname", "admin");

	/** The to test 1. */
	MyUserDetails toTest1 = new MyUserDetails(toTestUser1);

	/** The to test 2. */
	MyUserDetails toTest2 = new MyUserDetails(toTestUser2);

	/**
	 * Test hash code.
	 */
	@Test
	void testHashCode() {
		assertEquals(
				(toTest1.toString()).hashCode(),
				(toTest2.toString()).hashCode());
	}


	/**
	 * Test equals object.
	 */
	@Test
	final void testEqualsObject() {
		assertEquals(toTest1.toString(), toTest2.toString());
	}

	/**
	 * Test can equal.
	 */
	@Test
	final void testCanEqual() {
		assertEquals(toTest1.toString(), toTest2.toString());
	}

	/**
	 * Test person string string string string int string string.
	 */
	@Test
	final void testPersonStringStringStringStringIntStringString() {
		assertEquals(toTest1.toString(), toTest2.toString());
	}

	/**
	 * Test user name.
	 */
	@Test
	final void testUserName() {
		assertEquals(toTest1.getUsername(), toTest2.getUsername());
	}

	/**
	 * Test password.
	 */
	@Test
	final void testPassword() {
		assertEquals(toTest1.getPassword(), toTest2.getPassword());
	}

	/**
	 * Test is account non expired.
	 */
	@Test
	final void testIsAccountNonExpired() {

		assertEquals(toTest1.isAccountNonExpired(), true);
	}

	/**
	 * Test is account non locked.
	 */
	@Test
	final void testIsAccountNonLocked() {

		assertEquals(toTest1.isAccountNonLocked(), true);
	}

	/**
	 * Test is credentials non expired.
	 */
	@Test
	final void testIsCredentialsNonExpired() {

		assertEquals(toTest1.isCredentialsNonExpired(), true);
	}

	/**
	 * Test is enabled.
	 */
	@Test
	final void testIsEnabled() {

		assertEquals(toTest1.isEnabled(), true);
	}	

	/**
	 * Test granted authority.
	 */
	@Test
	final void testGrantedAuthority() {

		assertEquals(toTest1.getAuthorities().toString(), "[admin]");
	}	
		
	
}