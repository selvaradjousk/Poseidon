package com.nnk.springboot.service;

import java.util.List;

import com.nnk.springboot.dto.TradeDTO;


/**
 * The Interface ITradeService.
 */
public interface ITradeService {

	/**
	 * Gets the all trade.
	 *
	 * @return the all trade
	 */
	List<TradeDTO> getAllTrade();

	/**
	 * Gets the trade by id.
	 *
	 * @param tradeId the trade id
	 * @return the trade by id
	 */
	TradeDTO getTradeById(int tradeId);

	/**
	 * Adds the trade.
	 *
	 * @param trade the trade
	 * @return the trade DTO
	 */
	TradeDTO addTrade(TradeDTO trade);

	/**
	 * Update trade.
	 *
	 * @param tradeId the trade id
	 * @param trade the trade
	 * @return the trade DTO
	 */
	TradeDTO updateTrade(int tradeId, TradeDTO trade);

	/**
	 * Delete trade.
	 *
	 * @param tradeId the trade id
	 */
	void deleteTrade(int tradeId);

}
