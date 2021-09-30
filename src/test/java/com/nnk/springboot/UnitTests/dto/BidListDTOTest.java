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

import com.nnk.springboot.dto.BidListDTO;

@DisplayName("DTO BidList - UNIT TESTS")
class BidListDTOTest {


	ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
	Validator validator = factory.getValidator();
	
	private static BidListDTO bidListTest1, bidListTest2,
	bidListDTOAccountNull, bidListDTOAccountEmpty, bidListDTOAccountNonConform,
	bidListDTOTypeNull, bidListDTOTypeEmpty, bidListDTOTypeNonConform,
	bidListDTObidQuantityNegative, bidListDTObidQuantityMoreThanTenDigits;
	
	@BeforeEach
	public void setUp() {
	bidListTest1 = new BidListDTO("BidList Account", "Type", 10.0);
	bidListTest2 = new BidListDTO("BidList Account", "Type", 10.0);
	bidListDTOAccountNull = new BidListDTO(null, "Type", 10.0);
	bidListDTOAccountEmpty = new BidListDTO("", "Type", 10.0);
	bidListDTOAccountNonConform = new BidListDTO("&#@", "Type", 10.0);
	bidListDTOTypeNull = new BidListDTO("BidList Account", null, 10.0);
	bidListDTOTypeEmpty = new BidListDTO("BidList Account", "", 10.0);
	bidListDTOTypeNonConform = new BidListDTO("BidList Account", "&#@", 10.0);
	bidListDTObidQuantityNegative = new BidListDTO("BidList Account", "Type", -10.0);
	bidListDTObidQuantityMoreThanTenDigits = new BidListDTO("BidList Account", "Type", 1000000000000.000);
	
	
}
	
	// *******************************************************************	
		
	
	@DisplayName("DTO - "
			+ "GIVEN DTO  "
			+ "WHEN Requested DTO value"
			+ "THEN returns expected DTO")	
	@Test
	public void bidListDTO() {
		assertEquals(bidListTest1.toString(), bidListTest2.toString());
	}

	
	// *******************************************************************	
	
	@DisplayName("DTO - All inputs Valid - "
			+ "GIVEN DTO with All inputs Valid"
			+ "WHEN Requested DTO value"
			+ "THEN returns expected DTO constraint violations")
    @Test
    public void testForAllInputsValid() {
 
    	// GIVEN
        
        // WHEN
        Set<ConstraintViolation<BidListDTO>> constraintViolations =
                validator.validate(bidListTest1);
        // THEN
        assertEquals(0, constraintViolations.size());

    }

	// *******************************************************************	
	
	@DisplayName("DTO - input Account - Null"
			+ "GIVEN DTO with input Account - Null"
			+ "WHEN Requested DTO value"
			+ "THEN returns expected DTO constraint violations")
    @Test
    public void testForInputAccountNull() {
 
    	// GIVEN
        
        // WHEN
        Set<ConstraintViolation<BidListDTO>> constraintViolations =
                validator.validate(bidListDTOAccountNull);
        // THEN
        assertEquals(1, constraintViolations.size());
        assertEquals("Account is mandatory",
        constraintViolations.iterator().next().getMessage());

    }
	

	// *******************************************************************	
	
	@DisplayName("DTO - input Account - Empty"
			+ "GIVEN DTO with input Account - Empty"
			+ "WHEN Requested DTO value"
			+ "THEN returns expected DTO constraint violations")
    @Test
    public void testForInputAccountEmpty() {
 
    	// GIVEN
        
        // WHEN
        Set<ConstraintViolation<BidListDTO>> constraintViolations =
                validator.validate(bidListDTOAccountEmpty);
        // THEN
        assertEquals(2, constraintViolations.size());

    }
	
	// *******************************************************************	
	
	@DisplayName("DTO - input Account - NonConform"
			+ "GIVEN DTO with input Account - NonConform"
			+ "WHEN Requested DTO value"
			+ "THEN returns expected DTO constraint violations")
    @Test
    public void testForInputAccountNonConform() {
 
    	// GIVEN
        
        // WHEN
        Set<ConstraintViolation<BidListDTO>> constraintViolations =
                validator.validate(bidListDTOAccountNonConform);
        // THEN
        assertEquals(1, constraintViolations.size());
        assertEquals("Should be alphanumeric and minimum more than 2 characters",
        constraintViolations.iterator().next().getMessage());

    }
	

	// *******************************************************************	
	
	@DisplayName("DTO - input Type - Null"
			+ "GIVEN DTO with input Type - Null"
			+ "WHEN Requested DTO value"
			+ "THEN returns expected DTO constraint violations")
    @Test
    public void testForInputTypeNull() {
 
    	// GIVEN
        
        // WHEN
        Set<ConstraintViolation<BidListDTO>> constraintViolations =
                validator.validate(bidListDTOTypeNull);
        // THEN
        assertEquals(1, constraintViolations.size());
        assertEquals("Type is mandatory",
        constraintViolations.iterator().next().getMessage());

    }
	

	// *******************************************************************	
	
	@DisplayName("DTO - input Type - Empty"
			+ "GIVEN DTO with input Type - Empty"
			+ "WHEN Requested DTO value"
			+ "THEN returns expected DTO constraint violations")
    @Test
    public void testForInputTypeEmpty() {
 
    	// GIVEN
        
        // WHEN
        Set<ConstraintViolation<BidListDTO>> constraintViolations =
                validator.validate(bidListDTOTypeEmpty);
        // THEN
        assertEquals(2, constraintViolations.size());

    }
	
	// *******************************************************************	
	
	@DisplayName("DTO - input Type - NonConform"
			+ "GIVEN DTO with input Type - NonConform"
			+ "WHEN Requested DTO value"
			+ "THEN returns expected DTO constraint violations")
    @Test
    public void testForInputTypeNonConform() {
 
    	// GIVEN
        
        // WHEN
        Set<ConstraintViolation<BidListDTO>> constraintViolations =
                validator.validate(bidListDTOTypeNonConform);
        // THEN
        assertEquals(1, constraintViolations.size());
        assertEquals("Should be alphanumeric and minimum more than 2 characters",
        constraintViolations.iterator().next().getMessage());

    }
	
	// *******************************************************************	
	
	@DisplayName("DTO - input bidQuantityNegative"
			+ "GIVEN DTO with input bidQuantityNegative"
			+ "WHEN Requested DTO value"
			+ "THEN returns expected DTO constraint violations")
    @Test
    public void testForInputBuyQuantityNegative() {
 
    	// GIVEN
        
        // WHEN
        Set<ConstraintViolation<BidListDTO>> constraintViolations =
                validator.validate(bidListDTObidQuantityNegative);
        // THEN
        assertEquals(1, constraintViolations.size());
        assertEquals("The bidQuantity must be positive",
        constraintViolations.iterator().next().getMessage());

    }
	
					
	// *******************************************************************	
	
	@DisplayName("DTO - input bidQuantityMoreThanTenDigits"
			+ "GIVEN DTO with input bidQuantityMoreThanTenDigits"
			+ "WHEN Requested DTO value"
			+ "THEN returns expected DTO constraint violations")
    @Test
    public void testForInputBuyQuantityMoreThanTenDigits() {
 
    	// GIVEN
        
        // WHEN
        Set<ConstraintViolation<BidListDTO>> constraintViolations =
                validator.validate(bidListDTObidQuantityMoreThanTenDigits);
        // THEN
        assertEquals(1, constraintViolations.size());
        assertEquals("Invalid number input value : Maximum digits allowed"
        		+ " are 10 and with 2 decimals fractions",
        constraintViolations.iterator().next().getMessage());

    }

}
