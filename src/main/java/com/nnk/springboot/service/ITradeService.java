package com.nnk.springboot.service;

import java.util.List;

import com.nnk.springboot.dto.TradeDTO;

public interface ITradeService {

	List<TradeDTO> getAllTrade();

	TradeDTO getTradeById(final int tradeId);

}
