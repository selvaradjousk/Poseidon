package com.nnk.springboot.UnitTests.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.dto.RuleNameDTO;
import com.nnk.springboot.util.RuleNameMapper;

@DisplayName("Mapper ==> RuleName - UNIT TESTS")
class RuleNameMapperTest {

	RuleNameDTO ruleNameDTO = new RuleNameDTO("Rule Name", "Description", "Json", "Template", "SQL", "SQL Part");
	RuleName ruleName = new RuleName("Rule Name", "Description", "Json", "Template", "SQL", "SQL Part");
	
	

	// *******************************************************************	
	
	@DisplayName("DTO to Entity - "
			+ "GIVEN DTO  "
			+ "WHEN Requested to DO"
			+ "THEN returns DO")	
	@Test
	void testToRuleName() {
		RuleNameMapper mapper = new RuleNameMapper();
		RuleName entity = mapper.toRuleName(ruleNameDTO);
		
		assertEquals(entity.getName(), ruleName.getName());
		assertEquals(entity.getDescription(), ruleName.getDescription());
		assertEquals(entity.getJson(), ruleName.getJson());
	}
	
	
	// *******************************************************************	
	
	@DisplayName(" DTO to Entity null DTO Exception- "
			+ "GIVEN DTO null "
			+ "WHEN Requested to DO"
			+ "THEN returns Exception")
	@Test
    public void testToEntityNullException() {
		RuleNameMapper mapper = new RuleNameMapper();
	
    assertThrows(NullPointerException.class, ()
     		  -> mapper.toRuleName(null).getName());
	}
	// *******************************************************************	
	
	@DisplayName("DO to DTO - "
			+ "GIVEN DO  "
			+ "WHEN Requested to DTO"
			+ "THEN returns DTO")	
	@Test
	void testToRuleNameDTO() {
		RuleNameMapper mapper = new RuleNameMapper();
		RuleNameDTO dto = mapper.toRuleNameDTO(ruleName);
		
		assertEquals(dto.getName(), ruleNameDTO.getName());
		assertEquals(dto.getDescription(), ruleNameDTO.getDescription());
		assertEquals(dto.getJson(), ruleNameDTO.getJson());
	}	
	
	// *******************************************************************	
	
	@DisplayName("DO to DTO null DO Exception- "
			+ "GIVEN DO null "
			+ "WHEN Requested to DTO"
			+ "THEN returns Exception")
	@Test
    public void testToDTONullException() {
	
		
		RuleNameMapper mapper = new RuleNameMapper();
	
    assertThrows(NullPointerException.class, ()
     		  -> mapper.toRuleNameDTO(null).getName());
	}
	
	
	// *******************************************************************	
}
