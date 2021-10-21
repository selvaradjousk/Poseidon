package com.nnk.springboot.UnitTests.config;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.nnk.springboot.config.LoginRequest;

@DisplayName("LoginRequest - UNIT TESTS")
class LoginRequestTest {

	
	LoginRequest loginRequest = new LoginRequest("Username", "Password");
	LoginRequest loginRequest2 = new LoginRequest("Username", "Password");

	// *******************************************************************	
		
	
	@DisplayName("DTO - "
			+ "GIVEN DTO  "
			+ "WHEN Requested DTO value"
			+ "THEN returns expected DTO")
	@Test
	public void LoginRequestTest() {
	assertEquals(loginRequest.getUsername(), loginRequest2.getUsername());
	assertEquals(loginRequest2.toString(), loginRequest.toString());
	}
	
	// *******************************************************************	
	
}
