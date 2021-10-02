package com.nnk.springboot.service;

import java.util.List;

import com.nnk.springboot.dto.CurvePointDTO;

public interface ICurvePointService {


	List<CurvePointDTO> getAllCurvePoint();

	CurvePointDTO getCurvePointById(final int curvePointId);

	CurvePointDTO addCurvePoint(final CurvePointDTO curvePoint);

	CurvePointDTO updateCurvePoint(
			final int curvePointId,
			final CurvePointDTO curvePoint);

	void deleteCurvePoint(final int curvePointId);

}
