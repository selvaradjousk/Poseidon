package com.nnk.springboot.util;

import org.springframework.stereotype.Component;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.dto.BidListDTO;


/**
 * The Class BidListMapper.
 */
@Component
public class BidListMapper {

    /**
     * To bid list.
     *
     * @param bidListDTO the bid list DTO
     * @return the bid list
     */
    public BidList toBidList(final BidListDTO bidListDTO) {

        return new BidList(
        		bidListDTO.getAccount(),
        		bidListDTO.getType(),
        		bidListDTO.getBidQuantity());
    }

    /**
     * To bid list DTO.
     *
     * @param bidList the bid list
     * @return the bid list DTO
     */
    public BidListDTO toBidListDTO(final BidList bidList) {

        return new BidListDTO(
        		bidList.getBidListId(),
        		bidList.getAccount(),
                bidList.getType(),
                bidList.getBidQuantity());
    }
}
