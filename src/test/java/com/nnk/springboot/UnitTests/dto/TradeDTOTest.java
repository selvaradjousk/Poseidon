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

import com.nnk.springboot.dto.TradeDTO;

@DisplayName("DTO TRADE - UNIT TESTS")
class TradeDTOTest {

	ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
	Validator validator = factory.getValidator();
	
	private static TradeDTO tradeTest1, tradeTest2,
	tradeDTOAccountNull, tradeDTOAccountEmpty, tradeDTOAccountNonConform,
	tradeDTOTypeNull, tradeDTOTypeEmpty, tradeDTOTypeNonConform,
	tradeDTObuyQuantityNegative, tradeDTObuyQuantityMoreThanTenDigits;
	
	@BeforeEach
	public void setUp() {
	tradeTest1 = new TradeDTO("Trade Account", "Type", 10.0);
	tradeTest2 = new TradeDTO("Trade Account", "Type", 10.0);
	tradeDTOAccountNull = new TradeDTO(null, "Type", 10.0);
	tradeDTOAccountEmpty = new TradeDTO("", "Type", 10.0);
	tradeDTOAccountNonConform = new TradeDTO("&#@", "Type", 10.0);
	tradeDTOTypeNull = new TradeDTO("Trade Account", null, 10.0);
	tradeDTOTypeEmpty = new TradeDTO("Trade Account", "", 10.0);
	tradeDTOTypeNonConform = new TradeDTO("Trade Account", "&#@", 10.0);
	tradeDTObuyQuantityNegative = new TradeDTO("Trade Account", "Type", -10.0);
	tradeDTObuyQuantityMoreThanTenDigits = new TradeDTO("Trade Account", "Type", 1000000000000.000);
	
	
	
	}
	
	// *******************************************************************	
		
	
	@DisplayName("DTO - "
			+ "GIVEN DTO  "
			+ "WHEN Requested DTO value"
			+ "THEN returns expected DTO")	
	@Test
	public void tradeDTOTest() {
		assertEquals(tradeTest1.toString(), tradeTest2.toString());
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
        Set<ConstraintViolation<TradeDTO>> constraintViolations =
                validator.validate(tradeTest1);
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
        Set<ConstraintViolation<TradeDTO>> constraintViolations =
                validator.validate(tradeDTOAccountNull);
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
        Set<ConstraintViolation<TradeDTO>> constraintViolations =
                validator.validate(tradeDTOAccountEmpty);
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
        Set<ConstraintViolation<TradeDTO>> constraintViolations =
                validator.validate(tradeDTOAccountNonConform);
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
        Set<ConstraintViolation<TradeDTO>> constraintViolations =
                validator.validate(tradeDTOTypeNull);
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
        Set<ConstraintViolation<TradeDTO>> constraintViolations =
                validator.validate(tradeDTOTypeEmpty);
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
        Set<ConstraintViolation<TradeDTO>> constraintViolations =
                validator.validate(tradeDTOTypeNonConform);
        // THEN
        assertEquals(1, constraintViolations.size());
        assertEquals("Should be alphanumeric and minimum more than 2 characters",
        constraintViolations.iterator().next().getMessage());

    }
	
	// *******************************************************************	
	
	@DisplayName("DTO - input buyQuantityNegative"
			+ "GIVEN DTO with input buyQuantityNegative"
			+ "WHEN Requested DTO value"
			+ "THEN returns expected DTO constraint violations")
    @Test
    public void testForInputBuyQuantityNegative() {
 
    	// GIVEN
        
        // WHEN
        Set<ConstraintViolation<TradeDTO>> constraintViolations =
                validator.validate(tradeDTObuyQuantityNegative);
        // THEN
        assertEquals(1, constraintViolations.size());
        assertEquals("The value must be positive",
        constraintViolations.iterator().next().getMessage());

    }
	
					
	// *******************************************************************	
	
	@DisplayName("DTO - input buyQuantityMoreThanTenDigits"
			+ "GIVEN DTO with input buyQuantityMoreThanTenDigits"
			+ "WHEN Requested DTO value"
			+ "THEN returns expected DTO constraint violations")
    @Test
    public void testForInputBuyQuantityMoreThanTenDigits() {
 
    	// GIVEN
        
        // WHEN
        Set<ConstraintViolation<TradeDTO>> constraintViolations =
                validator.validate(tradeDTObuyQuantityMoreThanTenDigits);
        // THEN
        assertEquals(1, constraintViolations.size());
        assertEquals("Invalid number input value : Maximum digits allowed"
        		+ " are 10 and with 2 decimals fractions",
        constraintViolations.iterator().next().getMessage());

    }
	
	// *******************************************************************
}
