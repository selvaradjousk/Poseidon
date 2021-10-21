package com.nnk.springboot.util;

import org.springframework.stereotype.Component;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.dto.RatingDTO;


/**
 * The Class RatingMapper.
 */
@Component
public class RatingMapper {

    /**
     * To rating DTO.
     *
     * @param rating the rating
     * @return the rating DTO
     */
    public RatingDTO toRatingDTO(final Rating rating) {

        return new RatingDTO(
        		rating.getId(),
        		rating.getMoodysRating(),
        		rating.getSandPRating(),
                rating.getFitchRating(),
                rating.getOrderNumber());
    }


    /**
     * To rating.
     *
     * @param ratingDTO the rating DTO
     * @return the rating
     */
    public Rating toRating(final RatingDTO ratingDTO) {

        return new Rating(
        		ratingDTO.getMoodysRating(),
        		ratingDTO.getSandPRating(),
        		ratingDTO.getFitchRating(),
                ratingDTO.getOrderNumber());
    }
}
