package com.nnk.springboot.util;

import org.springframework.stereotype.Component;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.dto.TradeDTO;


/**
 * The Class TradeMapper.
 */
@Component
public class TradeMapper {

    /**
     * To trade DTO.
     *
     * @param trade the trade
     * @return the trade DTO
     */
    public TradeDTO toTradeDTO(final Trade trade) {

        return new TradeDTO(
        		trade.getTradeId(),
        		trade.getAccount(),
        		trade.getType(),
        		trade.getBuyQuantity());
    }

    /**
     * To trade.
     *
     * @param tradeDTO the trade DTO
     * @return the trade
     */
    public Trade toTrade(final TradeDTO tradeDTO) {

        return new Trade(
        		tradeDTO.getAccount(),
        		tradeDTO.getType(),
        		tradeDTO.getBuyQuantity());
    }
}
