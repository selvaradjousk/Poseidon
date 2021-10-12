package com.nnk.springboot.UnitTests.constant;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.nnk.springboot.constant.UserConstraints;
@DisplayName("User Constraints - UNIT TESTS")
class UserConstraintsTest {

	public static final String PATTERN_PASSWORD = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[&~#@=*-+!€^$£µ%])(?=\\S+$).{8,}$";
	
	@Test
	void testUserConstraints() {
		assertEquals(PATTERN_PASSWORD, UserConstraints.PATTERN_PASSWORD);
	}

}
