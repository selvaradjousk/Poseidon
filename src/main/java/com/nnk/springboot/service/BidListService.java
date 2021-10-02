package com.nnk.springboot.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.nnk.springboot.dto.BidListDTO;
import com.nnk.springboot.repository.BidListRepository;
import com.nnk.springboot.util.BidListMapper;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class BidListService implements IBidListService {

	private final BidListRepository bidListRepository;

    private final BidListMapper bidListMapper;

   	// *******************************************************************

    public BidListService(
    		final BidListRepository bidListRepository,
    		final BidListMapper bidListMapper) {

		this.bidListRepository = bidListRepository;
		this.bidListMapper = bidListMapper;
	}

   	// *******************************************************************



    @Override
	public List<BidListDTO> getAllBidList() {
		// TODO Auto-generated method stub
		return null;
	}

   	// *******************************************************************

}
