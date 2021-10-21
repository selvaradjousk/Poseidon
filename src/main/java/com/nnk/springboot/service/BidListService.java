package com.nnk.springboot.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.dto.BidListDTO;
import com.nnk.springboot.exception.DataNotFoundException;
import com.nnk.springboot.repository.BidListRepository;
import com.nnk.springboot.util.BidListMapper;

import lombok.extern.log4j.Log4j2;


/**
 * The Class BidListService.
 */
@Log4j2
@Service
public class BidListService implements IBidListService {

	/** The bid list repository. */
	private final BidListRepository bidListRepository;

    /** The bid list mapper. */
    private final BidListMapper bidListMapper;

   	// *******************************************************************

    /**
	    * Instantiates a new bid list service.
	    *
	    * @param bidListRepository the bid list repository
	    * @param bidListMapper the bid list mapper
	    */
	   public BidListService(
    		final BidListRepository bidListRepository,
    		final BidListMapper bidListMapper) {

		this.bidListRepository = bidListRepository;
		this.bidListMapper = bidListMapper;
	}

   	// *******************************************************************



    /**
	    * Gets the all bid list.
	    *
	    * @return the all bid list
	    */
	   @Override
    public List<BidListDTO> getAllBidList() {


        List<BidListDTO> bidListList = new ArrayList<>();

    	List<BidList> bidLists = bidListRepository
        		.findAll();

		log.info("Request: BidListService"
				+ ".bitListRepository.findAll()"
				+ " - ListSize: {} BidLists",
				bidLists.size());

        for (BidList bidList : bidLists) {
            BidListDTO bidListDTO = bidListMapper
            		.toBidListDTO(bidList);
            
            bidListList.add(bidListDTO);
        }

        log.info("Request: bidListList.add(bidListDTO)"
        		+ " after bidListMapper.toBidListDTO(user)"
				+ " - ListSize: {} bidLists",
				bidListList.size());

        return bidListList;
    }

   	// *******************************************************************

	/**
	    * Gets the bid list by id.
	    *
	    * @param bidListId the bid list id
	    * @return the bid list by id
	    */
	   @Override
    public BidListDTO getBidListById(
    		final int bidListId) {


        BidList bidListById = bidListRepository
        		.findById(bidListId)
        		.orElseThrow(() ->
                new DataNotFoundException(
                		"BIDLIST ID NOT FOUND"));

        log.info("Request: bidListRepository"
        		+ ".findById(bidListId)"
        		+ "BIDLIST ID: {} & Account: {} ",
        		bidListById.getBidListId(),
        		bidListById.getAccount());

        return bidListMapper
        		.toBidListDTO(bidListById);
    }

   	// *******************************************************************


    /**
	    * Adds the bid list.
	    *
	    * @param bidListDTO the bid list DTO
	    * @return the bid list DTO
	    */
	   public BidListDTO addBidList(
			   final BidListDTO bidListDTO) {


    	BidList bidListToAdd = bidListMapper
    			.toBidList(bidListDTO);

        log.info("Request: to ADD BIDLIST "
        		+ "BidList ID: {} & Account: {} ",
        		bidListToAdd.getBidListId(),
        		bidListToAdd.getAccount());
    	
        BidList bidListAdded = bidListRepository
        		.save(bidListToAdd);

        log.info("BIDLIST ADDED SUCCESSFULLY - "
        		+ "BidList ID: {} & Account: {} ",
        		bidListToAdd.getBidListId(),
        		bidListToAdd.getAccount());

        return bidListMapper
        		.toBidListDTO(bidListAdded);
    }

	// *******************************************************************


	/**
	 * Update bid list.
	 *
	 * @param bidListId the bid list id
	 * @param bidListDTO the bid list DTO
	 * @return the bid list DTO
	 */
	@Override
    public BidListDTO updateBidList(
    		final int bidListId,
    		final BidListDTO bidListDTO) {


        bidListRepository.findById(bidListId)
        		.orElseThrow(() ->
                new DataNotFoundException(
                		"BidList ID Not FOUND"));

        log.info("Request: to UPDATE BIDLIST "
        		+ "BidList ID: {} & Account: {} ",
        		bidListDTO.getBidListId(),
        		bidListDTO.getAccount());

        BidList bidListToUpdate = bidListMapper
        		.toBidList(bidListDTO);

        bidListToUpdate.setBidListId(bidListId);

        BidList bidListUpdated = bidListRepository
        		.save(bidListToUpdate);

        log.info("Request: UPDATED BIDLIST SUCCESSFULLY "
        		+ "Trade ID: {} & Account: {} ",
        		bidListUpdated.getBidListId(),
        		bidListUpdated.getAccount());

        return bidListMapper
        		.toBidListDTO(bidListUpdated);
    }


	// *******************************************************************

	/**
	 * Delete bid list.
	 *
	 * @param bidListId the bid list id
	 */
	@Override
    public void deleteBidList(final int bidListId) {

		log.info("Request: BidListToDelete ID => {}",
				bidListId);

        bidListRepository.findById(bidListId)
        	.orElseThrow(() ->
                new DataNotFoundException(
                		"BidListID NOT FOUND"));

		log.info("Request: BidListToDelete ID"
				+ " => {} FOUND",
				bidListId);

        bidListRepository.deleteById(bidListId);

		log.info("Request: BidListToDelete ID"
				+ " => {} DELETED",
				bidListId);

	}


	// *******************************************************************



	// *******************************************************************

}
