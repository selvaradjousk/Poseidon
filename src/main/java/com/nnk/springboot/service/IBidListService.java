package com.nnk.springboot.service;

import java.util.List;

import com.nnk.springboot.dto.BidListDTO;


/**
 * The Interface IBidListService.
 */
public interface IBidListService {

	/**
	 * Gets the all bid list.
	 *
	 * @return the all bid list
	 */
	List<BidListDTO> getAllBidList();

	/**
	 * Gets the bid list by id.
	 *
	 * @param bidListId the bid list id
	 * @return the bid list by id
	 */
	BidListDTO getBidListById(int bidListId);

	/**
	 * Adds the bid list.
	 *
	 * @param bidList the bid list
	 * @return the bid list DTO
	 */
	BidListDTO addBidList(BidListDTO bidList);

	/**
	 * Update bid list.
	 *
	 * @param bidListId the bid list id
	 * @param bidList the bid list
	 * @return the bid list DTO
	 */
	BidListDTO updateBidList(int bidListId, BidListDTO bidList);

	 /**
 	 * Delete bid list.
 	 *
 	 * @param bidListId the bid list id
 	 */
 	void deleteBidList(int bidListId);

}
