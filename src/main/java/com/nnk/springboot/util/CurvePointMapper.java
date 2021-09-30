package com.nnk.springboot.util;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.dto.CurvePointDTO;

public class CurvePointMapper {

    public CurvePointDTO toCurvePointDTO(final CurvePoint curvePoint) {

        return new CurvePointDTO(
        		curvePoint.getId(),
        		curvePoint.getCurveId(),
                curvePoint.getTerm(),
                curvePoint.getValue());
    }


    public CurvePoint toCurvePoint(final CurvePointDTO curvePointDTO) {

        return new CurvePoint(
        		curvePointDTO.getCurveId(),
        		curvePointDTO.getTerm(),
        		curvePointDTO.getValue());
    }

}
