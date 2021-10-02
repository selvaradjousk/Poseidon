package com.nnk.springboot.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.dto.CurvePointDTO;
import com.nnk.springboot.exception.DataNotFoundException;
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
    public CurvePointDTO getCurvePointById(final int curvePointId) {


        CurvePoint curvePointById = curvePointRepository
        		.findById(curvePointId)
        		.orElseThrow(() ->
                new DataNotFoundException("ID Not Found"));

        log.info("Request: curvePointRepository.findById(curvePointId)"
        		+ "CurvePoint ID: {} & Value: {} ",
        		curvePointById.getCurveId(), curvePointById.getValue());

        return curvePointMapper
        		.toCurvePointDTO(curvePointById);
    }

    // ******************************************************************


	@Override
    public CurvePointDTO addCurvePoint(final CurvePointDTO curvePointDTO) {


        CurvePoint curvePointToAdd = curvePointMapper
        		.toCurvePoint(curvePointDTO);

        log.info("Request: to ADD CURVEPOINT "
        		+ "Curve ID: {} & Value: {} ",
        		curvePointToAdd.getCurveId(),
        		curvePointToAdd.getValue());

        curvePointToAdd.setCreationDate(LocalDateTime.now());

        CurvePoint curvePointAdded = curvePointRepository
        		.save(curvePointToAdd);

        log.info("Request: CURVEPOINT ADDED SUCESSFULLY - "
        		+ "Curve ID: {} & Value: {} ",
        		curvePointToAdd.getCurveId(),
        		curvePointToAdd.getValue());

        return curvePointMapper
        		.toCurvePointDTO(curvePointAdded);
    }

    // ******************************************************************

	@Override
    public CurvePointDTO updateCurvePoint(
    		final int curvePointId,
    		final CurvePointDTO curvePointDTO) {


        curvePointRepository.findById(curvePointId)
        	.orElseThrow(() ->
                new DataNotFoundException("ID NOT FOUND"));

        log.info("Request: to UPDATE CURVEPOINT FOUND"
        		+ "Curve ID: {} & Value: {} ",
        		curvePointDTO.getCurveId(),
        		curvePointDTO.getValue());

        CurvePoint curvePoint = curvePointMapper
        		.toCurvePoint(curvePointDTO);

        curvePoint.setId(curvePointId);

        curvePoint.setAsOfDate(LocalDateTime.now());

        CurvePoint curvePointUpdated = curvePointRepository
        		.save(curvePoint);

        log.info("Request: CURVEPOINT ADDED SUCESSFULLY - "
        		+ "Curve ID: {} & Value: {} ",
        		curvePointUpdated.getCurveId(),
        		curvePointUpdated.getValue());

        return curvePointMapper
        		.toCurvePointDTO(curvePointUpdated);
    }

    // ******************************************************************


	@Override
	public void deleteCurvePoint(int curvePointId) {
		// TODO Auto-generated method stub
		
	}

	// ******************************************************************

}
