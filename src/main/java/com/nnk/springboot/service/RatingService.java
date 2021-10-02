package com.nnk.springboot.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.dto.RatingDTO;
import com.nnk.springboot.repository.RatingRepository;
import com.nnk.springboot.util.RatingMapper;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class RatingService implements IRatingService {


	private final RatingRepository ratingRepository;

	private final RatingMapper ratingMapper;

    // ******************************************************************


	public RatingService(
			final RatingRepository ratingRepository,
			final RatingMapper ratingMapper) {

		this.ratingRepository = ratingRepository;
		this.ratingMapper = ratingMapper;
	}

    // ******************************************************************


	@Override
    public List<RatingDTO> getAllRating() {


        List<RatingDTO> ratingList = new ArrayList<>();

		List<Rating> ratings = ratingRepository
        		.findAll();

		log.info("Request: RatingService.ratingRepository.findAll()"
				+ " - ListSize: {} ratings", ratings.size());

        for (Rating rating : ratings) {
            RatingDTO ratingDTO = ratingMapper
            		.toRatingDTO(rating);
            ratingList.add(ratingDTO);
        }

        log.info("Request: ratingList.add(ratingDTO)"
        		+ " after ratingMapper.toRatingDTO(rating)"
				+ " - ListSize: {} ratings", ratingList.size());

        return ratingList;
    }

    // ******************************************************************


	@Override
	public RatingDTO getRatingById(int ratingId) {
		// TODO Auto-generated method stub
		return null;
	}

    // ******************************************************************


	@Override
	public RatingDTO addRating(RatingDTO rating) {
		// TODO Auto-generated method stub
		return null;
	}

    // ******************************************************************


	@Override
	public RatingDTO updateRating(int ratingId, RatingDTO rating) {
		// TODO Auto-generated method stub
		return null;
	}

    // ******************************************************************


	@Override
	public void deleteRating(int ratingId) {
		// TODO Auto-generated method stub
		
	}

    // ******************************************************************


}