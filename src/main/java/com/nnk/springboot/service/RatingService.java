package com.nnk.springboot.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.dto.RatingDTO;
import com.nnk.springboot.exception.DataNotFoundException;
import com.nnk.springboot.repository.RatingRepository;
import com.nnk.springboot.util.RatingMapper;

import lombok.extern.log4j.Log4j2;


/**
 * The Class RatingService.
 */
@Log4j2
@Service
public class RatingService implements IRatingService {


	/** The rating repository. */
	private final RatingRepository ratingRepository;

	/** The rating mapper. */
	private final RatingMapper ratingMapper;

    // ******************************************************************


	/**
     * Instantiates a new rating service.
     *
     * @param ratingRepository the rating repository
     * @param ratingMapper the rating mapper
     */
    public RatingService(
			final RatingRepository ratingRepository,
			final RatingMapper ratingMapper) {

		this.ratingRepository = ratingRepository;
		this.ratingMapper = ratingMapper;
	}

    // ******************************************************************


	/**
     * Gets the all rating.
     *
     * @return the all rating
     */
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


	/**
     * Gets the rating by id.
     *
     * @param ratingId the rating id
     * @return the rating by id
     */
    @Override
    public RatingDTO getRatingById(final int ratingId) {


        Rating ratingById = ratingRepository
        		.findById(ratingId)
        		.orElseThrow(() ->
                new DataNotFoundException("No ID FOUND"));

        log.info("Request: ratingRepository.findById(ratingId)"
        		+ "rating FitchRating: {} & MoodysRating: {} ",
        		ratingById.getFitchRating(),
        		ratingById.getMoodysRating());

        return ratingMapper
        		.toRatingDTO(ratingById);
    }

    // ******************************************************************


	/**
     * Adds the rating.
     *
     * @param ratingDTO the rating DTO
     * @return the rating DTO
     */
    @Override
    public RatingDTO addRating(final RatingDTO ratingDTO) {


        Rating ratingToAdd = ratingMapper
        		.toRating(ratingDTO);

        log.info("Request: Rating to ADD"
        		+ "rating FitchRating: {} & MoodysRating: {} ",
        		ratingToAdd.getFitchRating(),
        		ratingToAdd.getMoodysRating());

        Rating ratingAdded = ratingRepository
        		.save(ratingToAdd);


        log.info("Request: Rating ADDED SUCCESFULLY"
        		+ "rating FitchRating: {} & MoodysRating: {} ",
        		ratingToAdd.getFitchRating(),
        		ratingToAdd.getMoodysRating());

        return ratingMapper
        		.toRatingDTO(ratingAdded);
    }

    // ******************************************************************


	/**
     * Update rating.
     *
     * @param ratingId the rating id
     * @param ratingDTO the rating DTO
     * @return the rating DTO
     */
    @Override
    public RatingDTO updateRating(
    		final int ratingId,
    		final RatingDTO ratingDTO) {


        log.info("Request: Rating ID requested for UPDATE"
        		+ "Rating ID: {}",
        		ratingId);

		ratingRepository.findById(ratingId)
        	.orElseThrow(() ->
                new DataNotFoundException("ID NOT FOUND"));

        log.info("Request: to UPDATE RATING FOUND"
        		+ "Rating ID: {} & MoodysRating: {} ",
        		ratingDTO.getId(),
        		ratingDTO.getMoodysRating());

        Rating ratingToUpdate = ratingMapper
        		.toRating(ratingDTO);

        ratingToUpdate.setId(ratingId);

        Rating ratingUpdated = ratingRepository
        		.save(ratingToUpdate);

        log.info("Request: RATING UPDATED SUCCESSFULLY"
        		+ "Rating ID: {} & MoodysRating: {} ",
        		ratingDTO.getId(),
        		ratingDTO.getMoodysRating());

        return ratingMapper.toRatingDTO(ratingUpdated);
    }

    // ******************************************************************


	/**
     * Delete rating.
     *
     * @param ratingId the rating id
     */
    @Override
    public void deleteRating(final int ratingId) {


        log.info("Request: to DELETE RATING"
        		+ "Rating ID: {}",
        		ratingId);

        ratingRepository.findById(ratingId)
        	.orElseThrow(() ->
                new DataNotFoundException("ID NOT FOUND"));


        log.info("Request: RATING to delete FOUND"
        		+ "Rating ID: {}",
        		ratingId);

        ratingRepository.deleteById(ratingId);


        log.info("Request: RATING deleted SUCCESSFULLY"
        		+ "Rating ID: {}",
        		ratingId);

	}

    // ******************************************************************


}
