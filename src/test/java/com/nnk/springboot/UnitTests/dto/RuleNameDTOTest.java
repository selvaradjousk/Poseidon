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

import com.nnk.springboot.dto.RuleNameDTO;

@DisplayName("DTO RULE NAME - UNIT TESTS")
class RuleNameDTOTest {

	
	ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
	Validator validator = factory.getValidator();	

	private static RuleNameDTO ruleNameTest1, ruleNameTest2,
	ruleNameDTONameNull, ruleNameDTONameEmpty, ruleNameDTONameNonConform,
	ruleNameDTODescriptionNull, ruleNameDTODescriptionEmpty, ruleNameDTODescriptionNonConform;
	
	@BeforeEach
	public void setUp() {
	ruleNameTest1 = new RuleNameDTO("Rule Name", "Description", "Json", "Template", "SQL", "SQL Part");
	ruleNameTest2 = new RuleNameDTO("Rule Name", "Description", "Json", "Template", "SQL", "SQL Part");
	ruleNameDTONameNull = new RuleNameDTO(null, "Description", "Json", "Template", "SQL", "SQL Part");
	ruleNameDTONameEmpty = new RuleNameDTO("", "Description", "Json", "Template", "SQL", "SQL Part");
	ruleNameDTONameNonConform = new RuleNameDTO("&#@", "Description", "Json", "Template", "SQL", "SQL Part");
	ruleNameDTODescriptionNull = new RuleNameDTO("Rule Name", null, "Json", "Template", "SQL", "SQL Part");
	ruleNameDTODescriptionEmpty = new RuleNameDTO("Rule Name", "", "Json", "Template", "SQL", "SQL Part");
	ruleNameDTODescriptionNonConform = new RuleNameDTO("Name", "&#@", "Json", "Template", "SQL", "SQL Part");
	
	
	
	}
	
	// *******************************************************************	
		
	
	@DisplayName("DTO - "
			+ "GIVEN DTO  "
			+ "WHEN Requested DTO value"
			+ "THEN returns expected DTO")	
	@Test
	public void ruleNameDTOTest() {
		assertEquals(ruleNameTest1.toString(), ruleNameTest2.toString());
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
        Set<ConstraintViolation<RuleNameDTO>> constraintViolations =
                validator.validate(ruleNameTest1);
        // THEN
        assertEquals(0, constraintViolations.size());

    }

	// *******************************************************************	
	
	@DisplayName("DTO - input Name - Null"
			+ "GIVEN DTO with input Name - Null"
			+ "WHEN Requested DTO value"
			+ "THEN returns expected DTO constraint violations")
    @Test
    public void testForInputNameNull() {
 
    	// GIVEN
        
        // WHEN
        Set<ConstraintViolation<RuleNameDTO>> constraintViolations =
                validator.validate(ruleNameDTONameNull);
        // THEN
        assertEquals(1, constraintViolations.size());
        assertEquals("Name is mandatory",
        constraintViolations.iterator().next().getMessage());

    }
	

	// *******************************************************************	
	
	@DisplayName("DTO - input Name - Empty"
			+ "GIVEN DTO with input Name - Empty"
			+ "WHEN Requested DTO value"
			+ "THEN returns expected DTO constraint violations")
    @Test
    public void testForInputNameEmpty() {
 
    	// GIVEN
        
        // WHEN
        Set<ConstraintViolation<RuleNameDTO>> constraintViolations =
                validator.validate(ruleNameDTONameEmpty);
        // THEN
        assertEquals(2, constraintViolations.size());

    }
	
	// *******************************************************************	
	
	@DisplayName("DTO - input Name - NonConform"
			+ "GIVEN DTO with input Name - NonConform"
			+ "WHEN Requested DTO value"
			+ "THEN returns expected DTO constraint violations")
    @Test
    public void testForInputNameNonConform() {
 
    	// GIVEN
        
        // WHEN
        Set<ConstraintViolation<RuleNameDTO>> constraintViolations =
                validator.validate(ruleNameDTONameNonConform);
        // THEN
        assertEquals(1, constraintViolations.size());
        assertEquals("Should be alphanumeric and minimum more than 2 characters",
        constraintViolations.iterator().next().getMessage());

    }
	

	// *******************************************************************	
	
	@DisplayName("DTO - input Description - Null"
			+ "GIVEN DTO with input Description - Null"
			+ "WHEN Requested DTO value"
			+ "THEN returns expected DTO constraint violations")
    @Test
    public void testForInputDescriptionNull() {
 
    	// GIVEN
        
        // WHEN
        Set<ConstraintViolation<RuleNameDTO>> constraintViolations =
                validator.validate(ruleNameDTODescriptionNull);
        // THEN
        assertEquals(1, constraintViolations.size());
        assertEquals("Description is mandatory",
        constraintViolations.iterator().next().getMessage());

    }
	

	// *******************************************************************	
	
	@DisplayName("DTO - input Description - Empty"
			+ "GIVEN DTO with input Description - Empty"
			+ "WHEN Requested DTO value"
			+ "THEN returns expected DTO constraint violations")
    @Test
    public void testForInputDescriptionEmpty() {
 
    	// GIVEN
        
        // WHEN
        Set<ConstraintViolation<RuleNameDTO>> constraintViolations =
                validator.validate(ruleNameDTODescriptionEmpty);
        // THEN
        assertEquals(2, constraintViolations.size());

    }
	
	// *******************************************************************	
	
	@DisplayName("DTO - input Description - NonConform"
			+ "GIVEN DTO with input Description - NonConform"
			+ "WHEN Requested DTO value"
			+ "THEN returns expected DTO constraint violations")
    @Test
    public void testForInputDescriptionNonConform() {
 
    	// GIVEN
        
        // WHEN
        Set<ConstraintViolation<RuleNameDTO>> constraintViolations =
                validator.validate(ruleNameDTODescriptionNonConform);
        // THEN
        assertEquals(1, constraintViolations.size());
        assertEquals("Should be alphanumeric and minimum more than 2 characters",
        constraintViolations.iterator().next().getMessage());

    }
	

}
