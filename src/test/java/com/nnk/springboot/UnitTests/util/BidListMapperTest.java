package com.nnk.springboot.UnitTests.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.dto.BidListDTO;
import com.nnk.springboot.util.BidListMapper;

@DisplayName("Mapper ==> BidList - UNIT TESTS")
class BidListMapperTest {

	
	BidList bidList = new BidList("Account Test", "Type Test", 10d);
	BidListDTO bidListDTO = new BidListDTO("Account Test", "Type Test", 10d);


	// *******************************************************************	
	
	@DisplayName("DTO to Entity - "
			+ "GIVEN DTO  "
			+ "WHEN Requested to DO"
			+ "THEN returns DO")
	@Test
	void testToBidList() {
		BidListMapper mapper = new BidListMapper();
		BidList entity = mapper.toBidList(bidListDTO);
		
		assertEquals(entity.getAccount(), bidList.getAccount());
		assertEquals(entity.getType(), bidList.getType());
		assertEquals(entity.getBidQuantity(), bidList.getBidQuantity());
	}
	
	
	// *******************************************************************	
	
	@DisplayName(" DTO to Entity null DTO Exception- "
			+ "GIVEN DTO null "
			+ "WHEN Requested to DO"
			+ "THEN returns Exception")
	@Test
    public void testToEntityNullException() {
		BidListMapper mapper = new BidListMapper();
	
    assertThrows(NullPointerException.class, ()
     		  -> mapper.toBidList(null).getAccount());
	}

	// *******************************************************************	
	
	@DisplayName("DO to DTO - "
			+ "GIVEN DO  "
			+ "WHEN Requested to DTO"
			+ "THEN returns DTO")	
	@Test
	void testToBidListDTO() {
		BidListMapper mapper = new BidListMapper();
		BidListDTO dto = mapper.toBidListDTO(bidList);
		
		assertEquals(dto.getAccount(), bidListDTO.getAccount());
		assertEquals(dto.getType(), bidListDTO.getType());
		assertEquals(dto.getBidQuantity(), bidListDTO.getBidQuantity());
	}
	
	// *******************************************************************	
	
	@DisplayName("DO to DTO null DO Exception- "
			+ "GIVEN DO null "
			+ "WHEN Requested to DTO"
			+ "THEN returns Exception")
	@Test
    public void testToDTONullException() {
	
		BidListMapper mapper = new BidListMapper();
	
    assertThrows(NullPointerException.class, ()
     		  -> mapper.toBidListDTO(null).getAccount());
	}
	
	
	// *******************************************************************	
}
