package com.nnk.springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nnk.springboot.domain.CurvePoint;

/**
 * The Interface CurvePointRepository.
 */
@Repository
public interface CurvePointRepository extends JpaRepository<CurvePoint, Integer> {

}
