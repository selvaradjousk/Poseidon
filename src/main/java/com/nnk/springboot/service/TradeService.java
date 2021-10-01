package com.nnk.springboot.service;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nnk.springboot.dto.TradeDTO;
import com.nnk.springboot.repository.TradeRepository;
import com.nnk.springboot.util.TradeMapper;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class TradeService implements ITradeService {

    private final TradeRepository tradeRepository;

    private final TradeMapper tradeMapper;

    public TradeService(
    		final TradeRepository tradeRepository,
    		final TradeMapper tradeMapper) {
        this.tradeRepository = tradeRepository;
        this.tradeMapper = tradeMapper;
    }

	@Override
	public List<TradeDTO> getAllTrade() {
		// TODO Auto-generated method stub
		return null;
	}

}
