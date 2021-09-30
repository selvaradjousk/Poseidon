package com.nnk.springboot.util;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.dto.TradeDTO;

public class TradeMapper {

    public TradeDTO toTradeDTO(final Trade trade) {

        return new TradeDTO(
        		trade.getTradeId(),
        		trade.getAccount(),
        		trade.getType(),
        		trade.getBuyQuantity());
    }

    public Trade toTrade(final TradeDTO tradeDTO) {

        return new Trade(
        		tradeDTO.getAccount(),
        		tradeDTO.getType(),
        		tradeDTO.getBuyQuantity());
    }    
}
