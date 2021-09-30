package com.nnk.springboot.UnitTests.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.dto.TradeDTO;
import com.nnk.springboot.util.TradeMapper;

@DisplayName("Trade Mapper - UNIT TESTS")
class TradeMapperTest {

	TradeDTO tradeDTO = new TradeDTO("Trade Account", "Type", 10.0);
	Trade trade = new Trade("Trade Account", "Type", 10.0);

	// *******************************************************************	
	
	@DisplayName("DTO to Entity - "
			+ "GIVEN DTO  "
			+ "WHEN Requested to DO"
			+ "THEN returns DO")
	@Test
	void testToTrade() {
		TradeMapper mapper = new TradeMapper();
		Trade entity = mapper.toTrade(tradeDTO);
		
		assertEquals(entity.getAccount(), trade.getAccount());
		assertEquals(entity.getType(), trade.getType());
		assertEquals(entity.getBuyQuantity(), trade.getBuyQuantity());
	}
	
	
	// *******************************************************************	
	
	@DisplayName(" DTO to Entity null DTO Exception- "
			+ "GIVEN DTO null "
			+ "WHEN Requested to DO"
			+ "THEN returns Exception")
	@Test
    public void testToEntityNullException() {
		TradeMapper mapper = new TradeMapper();
	
    assertThrows(NullPointerException.class, ()
     		  -> mapper.toTrade(null).getAccount());
	}

	// *******************************************************************	
		
	@Test
	void testToTradeDTO() {
		TradeMapper mapper = new TradeMapper();
		TradeDTO dto = mapper.toTradeDTO(trade);
		
		assertEquals(dto.getAccount(), tradeDTO.getAccount());
		assertEquals(dto.getType(), tradeDTO.getType());
		assertEquals(dto.getBuyQuantity(), tradeDTO.getBuyQuantity());
	}	
	
	// *******************************************************************	
	
	@DisplayName("DO to DTO null DO Exception- "
			+ "GIVEN DO null "
			+ "WHEN Requested to DTO"
			+ "THEN returns Exception")
	@Test
    public void testToDTONullException() {
	
		
		TradeMapper mapper = new TradeMapper();
	
    assertThrows(NullPointerException.class, ()
     		  -> mapper.toTradeDTO(null).getAccount());
	}
	
	
	// *******************************************************************	
}
