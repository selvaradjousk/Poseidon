package com.nnk.springboot.service;


import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.dto.TradeDTO;
import com.nnk.springboot.exception.DataNotFoundException;
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
    public TradeDTO getTradeById(final int tradeId) {

        Trade tradeById = tradeRepository
        		.findById(tradeId)
        		.orElseThrow(() ->
                new DataNotFoundException("Trade ID NOT FOUND"));

        log.info("Request: tradeRepository.findById(tradeId)"
        		+ "Trade ID: {} & Account: {} ",
        		tradeById.getTradeId(), tradeById.getAccount());

        return tradeMapper
        		.toTradeDTO(tradeById);
    }

    // ******************************************************************


	@Override
    public TradeDTO addTrade(final TradeDTO tradeDTO) {

        Trade tradeToAdd = tradeMapper
        		.toTrade(tradeDTO);

        log.info("Request: to ADD TRADE "
        		+ "Trade ID: {} & Account: {} ",
        		tradeToAdd.getTradeId(), tradeToAdd.getAccount());

        Trade tradeAdded = tradeRepository
        		.save(tradeToAdd);

        log.info("TRADED ADDED SUCCESSFULLY - "
        		+ "Trade ID: {} & Account: {} ",
        		tradeToAdd.getTradeId(), tradeToAdd.getAccount());

        return tradeMapper
        		.toTradeDTO(tradeAdded);

	}

	// ******************************************************************

	@Override
    public TradeDTO updateTrade(final int tradeId, final TradeDTO tradeDTO) {

        tradeRepository.findById(tradeId)
        		.orElseThrow(() ->
                new DataNotFoundException("Trade ID not Found"));

        log.info("Request: to UPDATE TRADE FOUND "
        		+ "Trade ID: {} & Account: {} ",
        		tradeId, tradeDTO.getAccount());
        
        Trade tradeToUpdate = tradeMapper
        		.toTrade(tradeDTO);

        tradeToUpdate.setTradeId(tradeId);

        Trade tradeUpdated = tradeRepository
        		.save(tradeToUpdate);

        log.info("Request: UPDATED TRADE SUCCESSFULLY "
        		+ "Trade ID: {} & Account: {} ",
        		tradeId, tradeDTO.getAccount());

        return tradeMapper
        		.toTradeDTO(tradeUpdated);
    }


	// ******************************************************************



}
