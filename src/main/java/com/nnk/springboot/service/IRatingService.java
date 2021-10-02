package com.nnk.springboot.service;

import java.util.List;

import com.nnk.springboot.dto.RatingDTO;

public interface IRatingService {


	List<RatingDTO> getAllRating();

	RatingDTO getRatingById(final int ratingId);

	RatingDTO addRating(final RatingDTO rating);

	RatingDTO updateRating(
			final int ratingId,
			final RatingDTO rating);

	void deleteRating(final int ratingId);
}
