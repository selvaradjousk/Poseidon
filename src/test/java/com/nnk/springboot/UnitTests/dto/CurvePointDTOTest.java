package com.nnk.springboot.UnitTests.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.nnk.springboot.dto.CurvePointDTO;

@DisplayName("DTO CurvePoint - UNIT TESTS")
class CurvePointDTOTest {

	ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
	Validator validator = factory.getValidator();
	
	private static CurvePointDTO curvePoint1, curvePoint2,
	curvePointDTOCurveIdNull, curvePointDTOCurveIdNegative,
	curvePointDTOTermNegative, curvePointDTOTermMoreThanTenDigits,
	curvePointDTOValueNegative, curvePointDTOValueMoreThanTenDigits, curvePointDTOAllNull;
	

	
	
	@BeforeEach
	public void setUp() {
		curvePoint1 = new CurvePointDTO(10, 10d, 30d);
		curvePoint2 = new CurvePointDTO(10, 10d, 30d);
		curvePointDTOAllNull = new CurvePointDTO(null, null, null);
		curvePointDTOCurveIdNull = new CurvePointDTO(null, 10d, 30d);
		curvePointDTOCurveIdNegative = new CurvePointDTO(-10, 10d, 30d);
		curvePointDTOTermNegative = new CurvePointDTO(10, -10.0, 30d);
		curvePointDTOTermMoreThanTenDigits = new CurvePointDTO(10,10000000000000.0000, 30d);
		curvePointDTOValueNegative = new CurvePointDTO(10, 10d, -30.00);
		curvePointDTOValueMoreThanTenDigits = new CurvePointDTO(10, 10d, 30000000000000.0000);
		
	}
	
	
	
	// *******************************************************************	
		
	
	@DisplayName("DTO - "
			+ "GIVEN DTO  "
			+ "WHEN Requested DTO value"
			+ "THEN returns expected DTO")	
	@Test
	void testCurvePointDTO() {
		assertEquals(curvePoint1.toString(), curvePoint2.toString());
	}
	
	

	// *******************************************************************	
	
	
	@DisplayName("DTO - All inputs Valid - "
			+ "GIVEN DTO with All inputs Valid"
			+ "WHEN Requested DTO value"
			+ "THEN returns expected DTO constraint violations")
	@Test
	public void testCurvePointDTOValidInput() {

		// GIVEN

	    // WHEN
	    Set<ConstraintViolation<CurvePointDTO>> constraintViolations =
	          validator.validate(curvePoint1);
	    // THEN
	    assertEquals(0, constraintViolations.size());
	 }
	
	
	// *******************************************************************	
	
	
	@DisplayName("DTO - All inputs Null - "
			+ "GIVEN DTO with All inputs Null"
			+ "WHEN Requested DTO value"
			+ "THEN returns expected DTO constraint violations")
	@Test
	public void testCurvePointDTOAllNullInput() {

		// GIVEN

	    // WHEN
	    Set<ConstraintViolation<CurvePointDTO>> constraintViolations =
	          validator.validate(curvePointDTOAllNull);
	    // THEN
	    assertEquals(3, constraintViolations.size());
	 }
	
	
	// *******************************************************************	
	   
	@DisplayName("DTO - input curvePointDTOCurveIdNull - "
			+ "GIVEN DTO with input curvePointDTOCurveIdNull"
			+ "WHEN Requested DTO value"
			+ "THEN returns expected DTO constraint violations")
    @Test
    public void testCurvePointDTOCurveIdNull() {
 
    	// GIVEN
        
        // WHEN
        Set<ConstraintViolation<CurvePointDTO>> constraintViolations =
                validator.validate(curvePointDTOCurveIdNull);
        // THEN
        assertEquals(1, constraintViolations.size());
        assertEquals("CurveId is mandatory",
        constraintViolations.iterator().next().getMessage());

    }
	

	// *******************************************************************	
	   
	@DisplayName("DTO - input curvePointDTOCurveIdNegative - "
			+ "GIVEN DTO with input curvePointDTOCurveIdNegative"
			+ "WHEN Requested DTO value"
			+ "THEN returns expected DTO constraint violations")
    @Test
    public void testCurvePointDTOCurveIdNegative() {
 
    	// GIVEN
        
        // WHEN
        Set<ConstraintViolation<CurvePointDTO>> constraintViolations =
                validator.validate(curvePointDTOCurveIdNegative);
        // THEN
        assertEquals(1, constraintViolations.size());
        assertEquals("The value must be positive",
        constraintViolations.iterator().next().getMessage());
    }
	

	// *******************************************************************	
	   
	@DisplayName("DTO - input curvePointDTOTermNegative - "
			+ "GIVEN DTO with input curvePointDTOTermNegative"
			+ "WHEN Requested DTO value"
			+ "THEN returns expected DTO constraint violations")
    @Test
    public void testCurvePointDTOTermNegative() {
 
    	// GIVEN
        
        // WHEN
        Set<ConstraintViolation<CurvePointDTO>> constraintViolations =
                validator.validate(curvePointDTOTermNegative);
        // THEN
        assertEquals(1, constraintViolations.size());
        assertEquals("The value must be positive",
        constraintViolations.iterator().next().getMessage());
    }
	
	// *******************************************************************	
	   
	@DisplayName("DTO - input curvePointDTOTermMoreThanTenDigits - "
			+ "GIVEN DTO with input curvePointDTOTermMoreThanTenDigits"
			+ "WHEN Requested DTO value"
			+ "THEN returns expected DTO constraint violations")
    @Test
    public void testCurvePointDTOTermMoreThanTenDigits() {
 
    	// GIVEN
        
        // WHEN
        Set<ConstraintViolation<CurvePointDTO>> constraintViolations =
                validator.validate(curvePointDTOTermMoreThanTenDigits);
        // THEN
        assertEquals(1, constraintViolations.size());
        assertEquals("Invalid number input value : Maximum digits allowed are 10 and with 2 decimals fractions",
        constraintViolations.iterator().next().getMessage());
    }
	
	

	// *******************************************************************	
	   
	@DisplayName("DTO - input CurvePointDTOValueNegative - "
			+ "GIVEN DTO with input CurvePointDTOValueNegative"
			+ "WHEN Requested DTO value"
			+ "THEN returns expected DTO constraint violations")
    @Test
    public void testCurvePointDTOValueNegative() {
 
    	// GIVEN
        
        // WHEN
        Set<ConstraintViolation<CurvePointDTO>> constraintViolations =
                validator.validate(curvePointDTOValueNegative);
        // THEN
        assertEquals(1, constraintViolations.size());
        assertEquals("The value must be positive",
        constraintViolations.iterator().next().getMessage());
    }
	
	// *******************************************************************	
	   
	@DisplayName("DTO - input curvePointDTOValueMoreThanTenDigits - "
			+ "GIVEN DTO with input curvePointDTOValueMoreThanTenDigits"
			+ "WHEN Requested DTO value"
			+ "THEN returns expected DTO constraint violations")
    @Test
    public void testCurvePointDTOValueMoreThanTenDigits() {
 
    	// GIVEN
        
        // WHEN
        Set<ConstraintViolation<CurvePointDTO>> constraintViolations =
                validator.validate(curvePointDTOValueMoreThanTenDigits);
        // THEN
        assertEquals(1, constraintViolations.size());
        assertEquals("Invalid number input value : Maximum digits allowed are 10 and with 2 decimals fractions",
        constraintViolations.iterator().next().getMessage());
    }
	// *******************************************************************	
}
