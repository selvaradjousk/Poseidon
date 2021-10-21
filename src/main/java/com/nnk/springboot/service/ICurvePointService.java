package com.nnk.springboot.service;

import java.util.List;

import com.nnk.springboot.dto.CurvePointDTO;


/**
 * The Interface ICurvePointService.
 */
public interface ICurvePointService {


	/**
	 * Gets the all curve point.
	 *
	 * @return the all curve point
	 */
	List<CurvePointDTO> getAllCurvePoint();

	/**
	 * Gets the curve point by id.
	 *
	 * @param curvePointId the curve point id
	 * @return the curve point by id
	 */
	CurvePointDTO getCurvePointById(int curvePointId);

	/**
	 * Adds the curve point.
	 *
	 * @param curvePoint the curve point
	 * @return the curve point DTO
	 */
	CurvePointDTO addCurvePoint(CurvePointDTO curvePoint);

	/**
	 * Update curve point.
	 *
	 * @param curvePointId the curve point id
	 * @param curvePoint the curve point
	 * @return the curve point DTO
	 */
	CurvePointDTO updateCurvePoint(
			int curvePointId,
			CurvePointDTO curvePoint);

	/**
	 * Delete curve point.
	 *
	 * @param curvePointId the curve point id
	 */
	void deleteCurvePoint(int curvePointId);

}
