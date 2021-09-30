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

import com.nnk.springboot.dto.RatingDTO;

@DisplayName("DTO RATING - UNIT TESTS")
class RatingDTOTest {

	ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
	Validator validator = factory.getValidator();
	
	private static RatingDTO rating1, rating2,
	ratingDTOMoodyRatingNull, ratingDTOMoodyRatingEmpty, ratingDTOMoodyRatingNonConform,
	ratingDTOSandPRatingNull, ratingDTOSandPRatingEmpty, ratingDTOSandPRatingNonConform,
	ratingDTOFitchRatingNull, ratingDTOFitchRatingEmpty, ratingDTOFitchRatingNonConform,
	ratingDTOOrderNumberNegative;
	
	
	@BeforeEach
	public void setUp() {
	rating1 = new RatingDTO("Moodys Rating", "Sand PRating", "Fitch Rating", 10);
	rating2 = new RatingDTO("Moodys Rating", "Sand PRating", "Fitch Rating", 10);
	ratingDTOMoodyRatingNull = new RatingDTO(null, "Sand PRating", "Fitch Rating", 10);
	ratingDTOMoodyRatingEmpty = new RatingDTO("", "Sand PRating", "Fitch Rating", 10);
	ratingDTOMoodyRatingNonConform = new RatingDTO("&#@!", "Sand PRating", "Fitch Rating", 10);
	ratingDTOSandPRatingNull = new RatingDTO("Moodys Rating", null, "Fitch Rating", 10);
	ratingDTOSandPRatingEmpty = new RatingDTO("Moodys Rating", "", "Fitch Rating", 10);
	ratingDTOSandPRatingNonConform = new RatingDTO("Moodys Rating", "&#@!", "Fitch Rating", 10);
	ratingDTOFitchRatingNull = new RatingDTO("Moodys Rating", "Sand PRating", null, 10);
	ratingDTOFitchRatingEmpty = new RatingDTO("Moodys Rating", "Sand PRating", "", 10);
	ratingDTOFitchRatingNonConform = new RatingDTO("Moodys Rating", "Sand PRating", "&#@!", 10);
	ratingDTOOrderNumberNegative = new RatingDTO("Moodys Rating", "Sand PRating", "Fitch Rating", -10);
	}
	
	
	// *******************************************************************	
		
	
	@DisplayName("DTO - "
			+ "GIVEN DTO  "
			+ "WHEN Requested DTO value"
			+ "THEN returns expected DTO")	
	@Test
	void testRatingDTO() {
		assertEquals(rating1.toString(), rating2.toString());
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
        Set<ConstraintViolation<RatingDTO>> constraintViolations =
                validator.validate(rating1);
        // THEN
        assertEquals(0, constraintViolations.size());

    }

	// *******************************************************************	
	
	@DisplayName("DTO - input moodysRating - Null"
			+ "GIVEN DTO with input moodysRating - Null"
			+ "WHEN Requested DTO value"
			+ "THEN returns expected DTO constraint violations")
    @Test
    public void testForInputmoodysRatingNull() {
 
    	// GIVEN
        
        // WHEN
        Set<ConstraintViolation<RatingDTO>> constraintViolations =
                validator.validate(ratingDTOMoodyRatingNull);
        // THEN
        assertEquals(1, constraintViolations.size());
        assertEquals("Moodys Rating is mandatory",
        constraintViolations.iterator().next().getMessage());

    }
	

	// *******************************************************************	
	
	@DisplayName("DTO - input moodysRating - Empty"
			+ "GIVEN DTO with input moodysRating - Empty"
			+ "WHEN Requested DTO value"
			+ "THEN returns expected DTO constraint violations")
    @Test
    public void testForInputmoodysRatingEmpty() {
 
    	// GIVEN
        
        // WHEN
        Set<ConstraintViolation<RatingDTO>> constraintViolations =
                validator.validate(ratingDTOMoodyRatingEmpty);
        // THEN
        assertEquals(2, constraintViolations.size());

    }
	
	// *******************************************************************	
	
	@DisplayName("DTO - input moodysRating - NonConform"
			+ "GIVEN DTO with input moodysRating - NonConform"
			+ "WHEN Requested DTO value"
			+ "THEN returns expected DTO constraint violations")
    @Test
    public void testForInputmoodysRatingNonConform() {
 
    	// GIVEN
        
        // WHEN
        Set<ConstraintViolation<RatingDTO>> constraintViolations =
                validator.validate(ratingDTOMoodyRatingNonConform);
        // THEN
        assertEquals(1, constraintViolations.size());
        assertEquals("Should be alphanumeric and minimum more than 2 characters",
        constraintViolations.iterator().next().getMessage());

    }
	
	

	// *******************************************************************	
	
	@DisplayName("DTO - input sandPRating - Null"
			+ "GIVEN DTO with input sandPRating - Null"
			+ "WHEN Requested DTO value"
			+ "THEN returns expected DTO constraint violations")
    @Test
    public void testForInputsandPRatingNull() {
 
    	// GIVEN
        
        // WHEN
        Set<ConstraintViolation<RatingDTO>> constraintViolations =
                validator.validate(ratingDTOSandPRatingNull);
        // THEN
        assertEquals(1, constraintViolations.size());
        assertEquals("SandPRating is mandatory",
        constraintViolations.iterator().next().getMessage());

    }
	

	// *******************************************************************	
	
	@DisplayName("DTO - input sandPRating - Empty"
			+ "GIVEN DTO with input sandPRating - Empty"
			+ "WHEN Requested DTO value"
			+ "THEN returns expected DTO constraint violations")
    @Test
    public void testForInputsandPRatingEmpty() {
 
    	// GIVEN
        
        // WHEN
        Set<ConstraintViolation<RatingDTO>> constraintViolations =
                validator.validate(ratingDTOSandPRatingEmpty);
        // THEN
        assertEquals(2, constraintViolations.size());

    }
	
	// *******************************************************************	
	
	@DisplayName("DTO - input sandPRating - NonConform"
			+ "GIVEN DTO with input sandPRating - NonConform"
			+ "WHEN Requested DTO value"
			+ "THEN returns expected DTO constraint violations")
    @Test
    public void testForInputsandPRatingNonConform() {
 
    	// GIVEN
        
        // WHEN
        Set<ConstraintViolation<RatingDTO>> constraintViolations =
                validator.validate(ratingDTOSandPRatingNonConform);
        // THEN
        assertEquals(1, constraintViolations.size());
        assertEquals("Should be alphanumeric and minimum more than 2 characters",
        constraintViolations.iterator().next().getMessage());

    }
	
	

	// *******************************************************************	
	
	@DisplayName("DTO - input fitchRating - Null"
			+ "GIVEN DTO with input fitchRating - Null"
			+ "WHEN Requested DTO value"
			+ "THEN returns expected DTO constraint violations")
    @Test
    public void testForInputfitchRatingNull() {
 
    	// GIVEN
        
        // WHEN
        Set<ConstraintViolation<RatingDTO>> constraintViolations =
                validator.validate(ratingDTOFitchRatingNull);
        // THEN
        assertEquals(1, constraintViolations.size());
        assertEquals("FitchRating is mandatory",
        constraintViolations.iterator().next().getMessage());

    }
	

	// *******************************************************************	
	
	@DisplayName("DTO - input fitchRating - Empty"
			+ "GIVEN DTO with input fitchRating - Empty"
			+ "WHEN Requested DTO value"
			+ "THEN returns expected DTO constraint violations")
    @Test
    public void testForInputfitchRatingEmpty() {
 
    	// GIVEN
        
        // WHEN
        Set<ConstraintViolation<RatingDTO>> constraintViolations =
                validator.validate(ratingDTOFitchRatingEmpty);
        // THEN
        assertEquals(2, constraintViolations.size());

    }
	
	// *******************************************************************	
	
	@DisplayName("DTO - input fitchRating - NonConform"
			+ "GIVEN DTO with input fitchRating - NonConform"
			+ "WHEN Requested DTO value"
			+ "THEN returns expected DTO constraint violations")
    @Test
    public void testForInputfitchRatingNonConform() {
 
    	// GIVEN
        
        // WHEN
        Set<ConstraintViolation<RatingDTO>> constraintViolations =
                validator.validate(ratingDTOFitchRatingNonConform);
        // THEN
        assertEquals(1, constraintViolations.size());
        assertEquals("Should be alphanumeric and minimum more than 2 characters",
        constraintViolations.iterator().next().getMessage());

    }
	
	// *******************************************************************	
	
	@DisplayName("DTO - input OrderNumberNegative"
			+ "GIVEN DTO with input OrderNumberNegative"
			+ "WHEN Requested DTO value"
			+ "THEN returns expected DTO constraint violations")
    @Test
    public void testForInputOrderNumberNegative() {
 
    	// GIVEN
        
        // WHEN
        Set<ConstraintViolation<RatingDTO>> constraintViolations =
                validator.validate(ratingDTOOrderNumberNegative);
        // THEN
        assertEquals(1, constraintViolations.size());
        assertEquals("The value must be positive",
        constraintViolations.iterator().next().getMessage());

    }
	// *******************************************************************

}
