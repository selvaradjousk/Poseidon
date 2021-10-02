package com.nnk.springboot.service;

import java.util.List;

import com.nnk.springboot.dto.BidListDTO;

public interface IBidListService {

	List<BidListDTO> getAllBidList();

	BidListDTO getBidListById(final int bidListId);

	BidListDTO addBidList(final BidListDTO bidList);

	BidListDTO updateBidList(final int bidListId, final BidListDTO bidList);

}
