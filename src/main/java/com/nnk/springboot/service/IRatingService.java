package com.nnk.springboot.service;

import java.util.List;

import com.nnk.springboot.dto.RatingDTO;


/**
 * The Interface IRatingService.
 */
public interface IRatingService {


	/**
	 * Gets the all rating.
	 *
	 * @return the all rating
	 */
	List<RatingDTO> getAllRating();

	/**
	 * Gets the rating by id.
	 *
	 * @param ratingId the rating id
	 * @return the rating by id
	 */
	RatingDTO getRatingById(int ratingId);

	/**
	 * Adds the rating.
	 *
	 * @param rating the rating
	 * @return the rating DTO
	 */
	RatingDTO addRating(RatingDTO rating);

	/**
	 * Update rating.
	 *
	 * @param ratingId the rating id
	 * @param rating the rating
	 * @return the rating DTO
	 */
	RatingDTO updateRating(
			int ratingId,
			RatingDTO rating);

	/**
	 * Delete rating.
	 *
	 * @param ratingId the rating id
	 */
	void deleteRating(int ratingId);
}
