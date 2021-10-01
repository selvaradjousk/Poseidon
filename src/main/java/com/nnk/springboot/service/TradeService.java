package com.nnk.springboot.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.dto.TradeDTO;
import com.nnk.springboot.repository.TradeRepository;
import com.nnk.springboot.util.TradeMapper;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class TradeService implements ITradeService {

    private final TradeRepository tradeRepository;

    private final TradeMapper tradeMapper;

    // ******************************************************************

    public TradeService(
    		final TradeRepository tradeRepository,
    		final TradeMapper tradeMapper) {
        this.tradeRepository = tradeRepository;
        this.tradeMapper = tradeMapper;
    }

    // ******************************************************************

	@Override
    public List<TradeDTO> getAllTrade() {

        List<Trade> trades = tradeRepository.findAll();
        List<TradeDTO> tradeList = new ArrayList<>();

		log.info("Request: TradeService.tradeRepository.findAll()"
				+ " - ListSize: {} trades", trades.size());		

        for (Trade trade : trades) {
            TradeDTO tradeDTO = tradeMapper.toTradeDTO(trade);
            tradeList.add(tradeDTO);
        }

        log.info("Request: tradeList.add(tradeDTO)"
        		+ " after tradeMapper.toTradeDTO(user)"
				+ " - ListSize: {} trades", tradeList.size());	

        return tradeList;
    }

    // ******************************************************************

	@Override
	public TradeDTO getTradeById(int tradeId) {
		// TODO Auto-generated method stub
		return null;
	}

    // ******************************************************************

}
