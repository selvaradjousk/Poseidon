package com.nnk.springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nnk.springboot.domain.Rating;

/**
 * The Interface RatingRepository.
 */
@Repository
public interface RatingRepository extends JpaRepository<Rating, Integer> {

}
