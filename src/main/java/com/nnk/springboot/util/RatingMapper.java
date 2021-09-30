package com.nnk.springboot.util;

import org.springframework.stereotype.Component;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.dto.RatingDTO;

@Component
public class RatingMapper {

    public RatingDTO toRatingDTO(final Rating rating) {

        return new RatingDTO(
        		rating.getId(),
        		rating.getMoodysRating(),
        		rating.getSandPRating(),
                rating.getFitchRating(),
                rating.getOrderNumber());
    }


    public Rating toRating(final RatingDTO ratingDTO) {

        return new Rating(
        		ratingDTO.getMoodysRating(),
        		ratingDTO.getSandPRating(),
        		ratingDTO.getFitchRating(),
                ratingDTO.getOrderNumber());
    }
}
