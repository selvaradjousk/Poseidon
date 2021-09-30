package com.nnk.springboot.util;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.dto.BidListDTO;

public class BidListMapper {

    public BidList toBidList(final BidListDTO bidListDTO) {

        return new BidList(
        		bidListDTO.getAccount(),
        		bidListDTO.getType(),
        		bidListDTO.getBidQuantity());
    }

    public BidListDTO toBidListDTO(final BidList bidList) {

        return new BidListDTO(
        		bidList.getBidListId(),
        		bidList.getAccount(),
                bidList.getType(),
                bidList.getBidQuantity());
    }
}
