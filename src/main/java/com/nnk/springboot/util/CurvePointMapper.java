package com.nnk.springboot.util;

import org.springframework.stereotype.Component;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.dto.CurvePointDTO;

@Component
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
