package com.nnk.springboot.UnitTests.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.dto.CurvePointDTO;
import com.nnk.springboot.util.CurvePointMapper;

@DisplayName("CurvePoint Mapper - UNIT TESTS")
class CurvePointMapperTest {

	CurvePointDTO curvePointDTO = new CurvePointDTO(10, 10d, 30d);
	CurvePoint curvePoint = new CurvePoint(10, 10d, 30d);

	// *******************************************************************	
	
	@DisplayName("DTO to Entity - "
			+ "GIVEN DTO  "
			+ "WHEN Requested to DO"
			+ "THEN returns DO")	
	@Test
	void testToCurvePoint() {
		CurvePointMapper mapper = new CurvePointMapper();
		CurvePoint entity = mapper.toCurvePoint(curvePointDTO);
		
		assertEquals(entity.getCurveId(), curvePoint.getCurveId());
		assertEquals(entity.getTerm(), curvePoint.getTerm());
		assertEquals(entity.getValue(), curvePoint.getValue());
	}

	
	
	// *******************************************************************	
	
	@DisplayName(" DTO to Entity null DTO Exception- "
			+ "GIVEN DTO null "
			+ "WHEN Requested to DO"
			+ "THEN returns Exception")
	@Test
    public void testToEntityNullException() {
		CurvePointMapper mapper = new CurvePointMapper();
	
    assertThrows(NullPointerException.class, ()
     		  -> mapper.toCurvePoint(null).getCurveId());
	}

	// *******************************************************************	
	
	@DisplayName("DO to DTO - "
			+ "GIVEN DO  "
			+ "WHEN Requested to DTO"
			+ "THEN returns DTO")	
	@Test
	void testToCurvePointDTO() {
		CurvePointMapper mapper = new CurvePointMapper();
		CurvePointDTO dto = mapper.toCurvePointDTO(curvePoint);
		
		assertEquals(dto.getCurveId(), curvePointDTO.getCurveId());
		assertEquals(dto.getTerm(), curvePointDTO.getTerm());
		assertEquals(dto.getValue(), curvePointDTO.getValue());
	}
	
	// *******************************************************************	
	
	@DisplayName("DO to DTO null DO Exception- "
			+ "GIVEN DO null "
			+ "WHEN Requested to DTO"
			+ "THEN returns Exception")
	@Test
    public void testToDTONullException() {
	
		CurvePointMapper mapper = new CurvePointMapper();
	
    assertThrows(NullPointerException.class, ()
     		  -> mapper.toCurvePointDTO(null).getCurveId());
	}
	
	
	// *******************************************************************	
}
