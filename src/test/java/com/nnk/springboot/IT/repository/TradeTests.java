package com.nnk.springboot.IT.repository;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.repository.TradeRepository;

//@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource({"/application-test.properties"})
public class TradeTests {

	@Autowired
	private TradeRepository tradeRepository;

	@Test
	public void tradeTest() {
		Trade trade = new Trade("Trade Account", "Type");
		Trade tradeTest2 = new Trade("Trade Account", "Type", 10.0);

		// Save
		trade = tradeRepository.save(trade);
		assertNotNull(trade.getTradeId());
		assertTrue(trade.getAccount().equals("Trade Account"));

		// Update
		trade.setAccount("Trade Account Update");
		trade = tradeRepository.save(trade);
		assertTrue(trade.getAccount().equals("Trade Account Update"));

		// Find
		List<Trade> listResult = tradeRepository.findAll();
		assertTrue(listResult.size() > 0);

		// Delete
		Integer id = trade.getTradeId();
		tradeRepository.delete(trade);
		Optional<Trade> tradeList = tradeRepository.findById(id);
		assertFalse(tradeList.isPresent());
		
		
		// Save2
		trade = tradeRepository.save(tradeTest2);
		assertNotNull(tradeTest2.getTradeId());
		assertTrue(tradeTest2.getAccount().equals("Trade Account"));

		// Update2
		tradeTest2.setAccount("Trade Account Update");
		tradeTest2 = tradeRepository.save(tradeTest2);
		assertTrue(tradeTest2.getAccount().equals("Trade Account Update"));

		// Find2
		List<Trade> listResult2 = tradeRepository.findAll();
		assertTrue(listResult2.size() > 0);

		// Delete2
		id = tradeTest2.getTradeId();
		tradeRepository.delete(tradeTest2);
		Optional<Trade> tradeList2 = tradeRepository.findById(id);
		assertFalse(tradeList2.isPresent());
	}
}
