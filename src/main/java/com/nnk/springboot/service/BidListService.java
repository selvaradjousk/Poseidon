package com.nnk.springboot.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.nnk.springboot.domain.BidList;
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


        List<BidListDTO> bidListList = new ArrayList<>();

    	List<BidList> bidLists = bidListRepository
        		.findAll();
        
		log.info("Request: BidListService.bitListRepository.findAll()"
				+ " - ListSize: {} BidLists",bidLists.size());

        for (BidList bidList : bidLists) {
            BidListDTO bidListDTO = bidListMapper
            		.toBidListDTO(bidList);
            
            bidListList.add(bidListDTO);
        }

        log.info("Request: bidListList.add(bidListDTO)"
        		+ " after bidListMapper.toBidListDTO(user)"
				+ " - ListSize: {} bidLists", bidListList.size());

        return bidListList;
    }

   	// *******************************************************************

}
