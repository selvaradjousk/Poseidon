package com.nnk.springboot.util;

import org.springframework.stereotype.Component;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.dto.CurvePointDTO;


/**
 * The Class CurvePointMapper.
 */
@Component
public class CurvePointMapper {

    /**
     * To curve point DTO.
     *
     * @param curvePoint the curve point
     * @return the curve point DTO
     */
    public CurvePointDTO toCurvePointDTO(final CurvePoint curvePoint) {

        return new CurvePointDTO(
        		curvePoint.getId(),
        		curvePoint.getCurveId(),
                curvePoint.getTerm(),
                curvePoint.getValue());
    }


    /**
     * To curve point.
     *
     * @param curvePointDTO the curve point DTO
     * @return the curve point
     */
    public CurvePoint toCurvePoint(final CurvePointDTO curvePointDTO) {

        return new CurvePoint(
        		curvePointDTO.getCurveId(),
        		curvePointDTO.getTerm(),
        		curvePointDTO.getValue());
    }

}
