package com.nnk.springboot.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.dto.CurvePointDTO;
import com.nnk.springboot.repository.CurvePointRepository;
import com.nnk.springboot.util.CurvePointMapper;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class CurvePointService implements ICurvePointService {


	private final CurvePointRepository curvePointRepository;

    private final CurvePointMapper curvePointMapper;


    // ******************************************************************

    public CurvePointService(
    		final CurvePointRepository curvePointRepository,
    		final CurvePointMapper curvePointMapper) {

		this.curvePointRepository = curvePointRepository;
		this.curvePointMapper = curvePointMapper;
	}


    // ******************************************************************


	@Override
    public List<CurvePointDTO> getAllCurvePoint() {

        List<CurvePointDTO> curvePointList = new ArrayList<>();

        List<CurvePoint> curvePoints = curvePointRepository
        		.findAll();

		log.info("Request: CurevePointService.curevePointRepository.findAll()"
				+ " - ListSize: {} curvePoints", curvePoints.size());


        for (CurvePoint curvePoint : curvePoints) {
            CurvePointDTO curvePointDTO = curvePointMapper
            		.toCurvePointDTO(curvePoint);
            curvePointList.add(curvePointDTO);
        }

        log.info("Request: curvePointList.add(tradeDTO)"
        		+ " after curvePointMapper.toCurevePointDTO(curvePoint)"
				+ " - ListSize: {} curvePoints", curvePointList.size());

        return curvePointList;
    }

    // ******************************************************************


	@Override
	public CurvePointDTO getCurvePointById(int curvePointId) {
		// TODO Auto-generated method stub
		return null;
	}

    // ******************************************************************


	@Override
	public CurvePointDTO addCurvePoint(CurvePointDTO curvePoint) {
		// TODO Auto-generated method stub
		return null;
	}

    // ******************************************************************


	@Override
	public CurvePointDTO updateCurvePoint(
			int curvePointId, CurvePointDTO curvePoint) {
		// TODO Auto-generated method stub
		return null;
	}

    // ******************************************************************


	@Override
	public void deleteCurvePoint(int curvePointId) {
		// TODO Auto-generated method stub
		
	}

	// ******************************************************************

}
