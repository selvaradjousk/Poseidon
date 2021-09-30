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

import com.nnk.springboot.dto.UserDTO;

@DisplayName("DTO USER - UNIT TESTS")
	class UserDTOTest {
	
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();
		
		private static UserDTO userDTO1, userDTO2, userDTOUserAllOk,
		userDTOUserNameNull, userDTOPasswordNull, userDTOFullNameNull, userDTORoleNull, userDTOAllNull,
		userDTOPasswordNoNumberValue, userDTOPasswordNoSpecialCharacter, userDTOPasswordNoSpecialCharacterAndNumericValue,
		userDTOPasswordNoAlphabet, userDTOPasswordLessThan8Charracters, userDTOPasswordMoreThan125Charracters,
		userDTOUserNameEmpty, userDTOFullNameEmpty, userDTOPasswordEmpty, userDTORoleEmpty, userDTOAllEmpty,
		userDTOPasswordNoUpperCase;
		
		@BeforeEach
		public void setUp() {
		userDTO1 = new UserDTO("Username", "Password", "Fullname", "Role");
		userDTO2 = new UserDTO("Username", "Password", "Fullname", "Role");
		userDTOUserAllOk = new UserDTO("UserName", "Password123&~#@=*-+€^$£µ%", "Fullname", "Role");
		userDTOUserNameNull = new UserDTO(null, "Password123&~#@=*-+€^$£µ%", "Fullname", "Role");
		userDTOPasswordNull = new UserDTO("Username", null, "Fullname", "Role");
		userDTOFullNameNull = new UserDTO("Username", "Password123&~#@=*-+€^$£µ%", null, "Role");
		userDTORoleNull = new UserDTO("Username", "Password123&~#@=*-+€^$£µ%", "Fullname", null);
		userDTOAllNull = new UserDTO(null, null, null, null);
		userDTOPasswordNoNumberValue = new UserDTO("Username", "Password&~#@=*-+€^$£µ%", "Fullname", "Role");
		userDTOPasswordNoSpecialCharacter = new UserDTO("Username", "Password1", "Fullname", "Role");
		userDTOPasswordNoAlphabet = new UserDTO("Username", "&~#@=*-+€^$£µ%123", "Fullname", "Role");
		userDTOPasswordNoUpperCase = new UserDTO("Username", "pass&~#@=*-+€^$£µ%123", "Fullname", "Role");
		userDTOPasswordNoSpecialCharacterAndNumericValue   = new UserDTO("Username", "Password", "Fullname", "Role");
		userDTOPasswordNoNumberValue = new UserDTO("Username", "Password&~#@=*-+€^$£µ%", "Fullname", "Role");
		userDTOPasswordLessThan8Charracters = new UserDTO("Username", "Pass1&", "Fullname", "Role");
		userDTOPasswordMoreThan125Charracters = new UserDTO(
				"Username",
				"Pass1&aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
				+ "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
				+ "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
				+ "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
				+ "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa",
				"Fullname",
				"Role");
		userDTOUserNameEmpty = new UserDTO("", "Password123&~#@=*-+€^$£µ%", "Fullname", "Role");
		userDTOPasswordEmpty = new UserDTO("UserName", "", "Fullname", "Role");
		userDTOFullNameEmpty = new UserDTO("UserName", "Password123&~#@=*-+€^$£µ%", "", "Role");
		userDTORoleEmpty = new UserDTO("UserName", "Password123&~#@=*-+€^$£µ%", "Fullname", "");
		userDTOAllEmpty = new UserDTO("", "", "", "");

	}
	// *******************************************************************	
		
	
	@DisplayName("DTO - "
			+ "GIVEN DTO  "
			+ "WHEN Requested DTO value"
			+ "THEN returns expected DTO")
	@Test
	public void userDTOTest() {
	assertEquals(userDTO1.getUsername(), userDTO2.getUsername());
	assertEquals(userDTO1.toString(), userDTO2.toString());
	}
	
	// *******************************************************************	
	
		@DisplayName("DTO - All inputs Valid - "
				+ "GIVEN DTO with All inputs Valid"
				+ "WHEN Requested DTO value"
				+ "THEN returns expected No DTO constraint violations")
	    @Test
	    public void testForAllInputsValid() {
	 
	    	// GIVEN
	        
	        // WHEN
	        Set<ConstraintViolation<UserDTO>> constraintViolations =
	                validator.validate(userDTOUserAllOk);
	        // THEN
	        assertEquals(0, constraintViolations.size());

	    }
		
		

	// *******************************************************************	
	
	@DisplayName("DTO - UserName Null - "
			+ "GIVEN DTO with username value null"
			+ "WHEN Requested DTO value"
			+ "THEN returns expected DTO constraint violations")
    @Test
    public void testForUserNameNull() {
 
    	// GIVEN
        
        // WHEN
        Set<ConstraintViolation<UserDTO>> constraintViolations =
                validator.validate(userDTOUserNameNull);
        // THEN
        assertEquals(1, constraintViolations.size());
        assertEquals("Username is mandatory",
        constraintViolations.iterator().next().getMessage());
    }
	
	

	// *******************************************************************	
	
	@DisplayName("DTO - FullName Null - "
			+ "GIVEN DTO with fullname value null"
			+ "WHEN Requested DTO value"
			+ "THEN returns expected DTO constraint violations")
    @Test
    public void testForFullNameNull() {
 
    	// GIVEN
        
        // WHEN
        Set<ConstraintViolation<UserDTO>> constraintViolations =
                validator.validate(userDTOFullNameNull);
        // THEN
        assertEquals(1, constraintViolations.size());
        assertEquals("FullName is mandatory",
        constraintViolations.iterator().next().getMessage());
    }
	
	

	// *******************************************************************	
	
	@DisplayName("DTO - Password Null - "
			+ "GIVEN DTO with Password value null"
			+ "WHEN Requested DTO value"
			+ "THEN returns expected DTO constraint violations")
    @Test
    public void testForPasswordNull() {
 
    	// GIVEN
        
        // WHEN
        Set<ConstraintViolation<UserDTO>> constraintViolations =
                validator.validate(userDTOPasswordNull);
        // THEN
        assertEquals(1, constraintViolations.size());
        assertEquals("Password is mandatory",
        constraintViolations.iterator().next().getMessage());
    }
	
	

	// *******************************************************************	
	
	@DisplayName("DTO - Role Null - "
			+ "GIVEN DTO with role value null"
			+ "WHEN Requested DTO value"
			+ "THEN returns expected DTO constraint violations")
    @Test
    public void testForRoleNull() {
 
    	// GIVEN
        
        // WHEN
        Set<ConstraintViolation<UserDTO>> constraintViolations =
                validator.validate(userDTORoleNull);
        // THEN
        assertEquals(1, constraintViolations.size());
        assertEquals("Role is mandatory",
        constraintViolations.iterator().next().getMessage());
    }
	

	// *******************************************************************	
	
	@DisplayName("DTO - All Null - "
			+ "GIVEN DTO with all values null"
			+ "WHEN Requested DTO value"
			+ "THEN returns expected DTO constraint violations")
    @Test
    public void testForNull() {
 
    	// GIVEN
        
        // WHEN
        Set<ConstraintViolation<UserDTO>> constraintViolations =
                validator.validate(userDTOAllNull);
        // THEN
        assertEquals(4, constraintViolations.size());
    }
	

	// *******************************************************************	

	
	
	@DisplayName("DTO - Password Without Numeric Value - "
			+ "GIVEN DTO with Password Without Numeric Value"
			+ "WHEN Requested DTO value"
			+ "THEN returns expected DTO constraint violations")
    @Test
    public void testForPasswordWithoutNumericValue() {
 
    	// GIVEN
        
        // WHEN
        Set<ConstraintViolation<UserDTO>> constraintViolations =
                validator.validate(userDTOPasswordNoNumberValue);
        // THEN
        assertEquals(1, constraintViolations.size());
        assertEquals("The password must contain at least 8 characters that includes any one uppercase letter, any one number and any one symbol ( & ~ # @ = * - + € ^ $ £ µ % )",
                constraintViolations.iterator().next().getMessage());
    }


	// *******************************************************************	
	
	
	
	@DisplayName("DTO - Password Without SpecialCharacter - "
			+ "GIVEN DTO with Password Without SpecialCharacter"
			+ "WHEN Requested DTO value"
			+ "THEN returns expected DTO constraint violations")
    @Test
    public void testForPasswordWithoutSpecialCharacter() {
 
    	// GIVEN
        
        // WHEN
        Set<ConstraintViolation<UserDTO>> constraintViolations =
                validator.validate(userDTOPasswordNoSpecialCharacter);
        // THEN
        assertEquals(1, constraintViolations.size());
        assertEquals("The password must contain at least 8 characters that includes any one uppercase letter, any one number and any one symbol ( & ~ # @ = * - + € ^ $ £ µ % )",
                constraintViolations.iterator().next().getMessage());
    }


	// *******************************************************************	
	
	
	
	@DisplayName("DTO - Password Without SpecialCharacter & Numeric value - "
			+ "GIVEN DTO with Password Without SpecialCharacter & Numeric value"
			+ "WHEN Requested DTO value"
			+ "THEN returns expected DTO constraint violations")
    @Test
    public void testForPasswordWithoutSpecialCharacterAndNumericValue() {
 
    	// GIVEN
        
        // WHEN
        Set<ConstraintViolation<UserDTO>> constraintViolations =
                validator.validate(userDTOPasswordNoSpecialCharacterAndNumericValue);
        // THEN
        assertEquals(1, constraintViolations.size());
        assertEquals("The password must contain at least 8 characters that includes any one uppercase letter, any one number and any one symbol ( & ~ # @ = * - + € ^ $ £ µ % )",
                constraintViolations.iterator().next().getMessage());
    }


	// *******************************************************************	
	
	
	
	@DisplayName("DTO - Password Without Alphabets - "
			+ "GIVEN DTO with Password Without Alphabets"
			+ "WHEN Requested DTO value"
			+ "THEN returns expected DTO constraint violations")
    @Test
    public void testForPasswordWithoutAlphabet() {
 
    	// GIVEN
        
        // WHEN
        Set<ConstraintViolation<UserDTO>> constraintViolations =
                validator.validate(userDTOPasswordNoAlphabet);
        // THEN
        assertEquals(1, constraintViolations.size());
        assertEquals("The password must contain at least 8 characters that includes any one uppercase letter, any one number and any one symbol ( & ~ # @ = * - + € ^ $ £ µ % )",
                constraintViolations.iterator().next().getMessage());
    }


	// *******************************************************************	
	
	
	@DisplayName("DTO - Password Less than 8 characters - "
			+ "GIVEN DTO with Password Less than 8 characters "
			+ "WHEN Requested DTO value"
			+ "THEN returns expected DTO constraint violations")
    @Test
    public void testForPasswordLessThan8Characters() {
 
    	// GIVEN
        
        // WHEN
        Set<ConstraintViolation<UserDTO>> constraintViolations =
                validator.validate(userDTOPasswordLessThan8Charracters);
        // THEN
        assertEquals(1, constraintViolations.size());
        assertEquals("The password must contain at least 8 characters that includes any one uppercase letter, any one number and any one symbol ( & ~ # @ = * - + € ^ $ £ µ % )",
                constraintViolations.iterator().next().getMessage());
    }


	// *******************************************************************	
	
	
	@DisplayName("DTO - Password More than 125 characters - "
			+ "GIVEN DTO with Password More than 125 characters "
			+ "WHEN Requested DTO value"
			+ "THEN returns expected DTO constraint violations")
    @Test
    public void testForPasswordMoreThan125Characters() {
 
    	// GIVEN
        
        // WHEN
        Set<ConstraintViolation<UserDTO>> constraintViolations =
                validator.validate(userDTOPasswordMoreThan125Charracters);
        // THEN
        assertEquals(1, constraintViolations.size());
        assertEquals("The maximum length for password should be 125 characters",
                constraintViolations.iterator().next().getMessage());
    }


	// *******************************************************************	

	
	
	@DisplayName("DTO - Password No UpperCase - "
			+ "GIVEN DTO with Password NoUpperCase characters "
			+ "WHEN Requested DTO value"
			+ "THEN returns expected DTO constraint violations")
    @Test
    public void testForPasswordNoUpperCase() {
 
    	// GIVEN
        
        // WHEN
        Set<ConstraintViolation<UserDTO>> constraintViolations =
                validator.validate(userDTOPasswordNoUpperCase);
        // THEN
        assertEquals(1, constraintViolations.size());
        assertEquals("The password must contain at least 8 characters that includes any one uppercase letter, any one number and any one symbol ( & ~ # @ = * - + € ^ $ £ µ % )",
                constraintViolations.iterator().next().getMessage());
    }


	// *******************************************************************	
		
	
	@DisplayName("DTO - input UserName Empty - "
			+ "GIVEN DTO with  UserName Empty "
			+ "WHEN Requested DTO value"
			+ "THEN returns expected DTO constraint violations")
    @Test
    public void testForUserNameEmpty() {
 
    	// GIVEN
        
        // WHEN
        Set<ConstraintViolation<UserDTO>> constraintViolations =
                validator.validate(userDTOUserNameEmpty);
        // THEN
        assertEquals(2, constraintViolations.size());
     // 2 constraints - (mandatory and minimumm two characters)
    }


	// *******************************************************************	

	
	
	@DisplayName("DTO - input FullName Empty - "
			+ "GIVEN DTO with FullName Empty "
			+ "WHEN Requested DTO value"
			+ "THEN returns expected DTO constraint violations")
    @Test
    public void testForFullNameEmpty() {
 
    	// GIVEN
        
        // WHEN
        Set<ConstraintViolation<UserDTO>> constraintViolations =
                validator.validate(userDTOFullNameEmpty);
        // THEN
        assertEquals(2, constraintViolations.size());
        // 2 constraints - (mandatory and minimumm two characters)
    }


	// *******************************************************************	

	
	@DisplayName("DTO - input Password Empty - "
			+ "GIVEN DTO with Password Empty "
			+ "WHEN Requested DTO value"
			+ "THEN returns expected DTO constraint violations")
    @Test
    public void testForPasswordEmpty() {
 
    	// GIVEN
        
        // WHEN
        Set<ConstraintViolation<UserDTO>> constraintViolations =
                validator.validate(userDTOPasswordEmpty);
        // THEN
        assertEquals(2, constraintViolations.size());
     // 2 constraints - (mandatory and minimum 8 characters including one capital letter, one number and one special character)
    }


	// *******************************************************************	

	
	@DisplayName("DTO - input Role Empty - "
			+ "GIVEN DTO with Role Empty "
			+ "WHEN Requested DTO value"
			+ "THEN returns expected DTO constraint violations")
    @Test
    public void testForRoleEmpty() {
 
    	// GIVEN
        
        // WHEN
        Set<ConstraintViolation<UserDTO>> constraintViolations =
                validator.validate(userDTORoleEmpty);
        // THEN
        assertEquals(2, constraintViolations.size());
     // 2 constraints - (mandatory and minimum two characters)
    }


	// *******************************************************************	

	
	@DisplayName("DTO - input All Empty - "
			+ "GIVEN DTO with All Empty "
			+ "WHEN Requested DTO value"
			+ "THEN returns expected DTO constraint violations")
    @Test
    public void testForAllInputsEmpty() {
 
    	// GIVEN
        
        // WHEN
        Set<ConstraintViolation<UserDTO>> constraintViolations =
                validator.validate(userDTOAllEmpty);
        // THEN
        assertEquals(8, constraintViolations.size());
     // 8 constraints - (mandatory(4) and minimumm two characters(4))

    }


	// *******************************************************************	

}
