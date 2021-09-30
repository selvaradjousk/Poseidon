package com.nnk.springboot.UnitTests.constant;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.nnk.springboot.constant.GeneralConstraints;

@DisplayName("General Constraints - UNIT TESTS")
class GeneralConstraintsTest {

	public static final int VARIABLE_LENGTH_125 = 125;

	public static final int VARIABLE_LENGTH_10 = 10;

	public static final int VARIABLE_LENGTH_30 = 30;

	public static final int VARIABLE_LENGTH_512 = 512;
	
	@Test
	void testGeneralConstraints() {
		assertEquals(VARIABLE_LENGTH_125, GeneralConstraints.VARIABLE_LENGTH_125);
		assertEquals(VARIABLE_LENGTH_10, GeneralConstraints.VARIABLE_LENGTH_10);
		assertEquals(VARIABLE_LENGTH_30, GeneralConstraints.VARIABLE_LENGTH_30);
		assertEquals(VARIABLE_LENGTH_512, GeneralConstraints.VARIABLE_LENGTH_512);
	}

}
