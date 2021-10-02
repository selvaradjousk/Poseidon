package com.nnk.springboot.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.dto.RuleNameDTO;
import com.nnk.springboot.exception.DataNotFoundException;
import com.nnk.springboot.repository.RuleNameRepository;
import com.nnk.springboot.util.RuleNameMapper;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class RuleNameService implements IRuleNameService {


    private final RuleNameRepository ruleNameRepository;

    private final RuleNameMapper ruleNameMapper;


	// *******************************************************************	

    public RuleNameService(
    		final RuleNameRepository ruleNameRepository,
            final RuleNameMapper ruleNameMapper) {
    	this.ruleNameRepository = ruleNameRepository;
    	this.ruleNameMapper = ruleNameMapper;
    }


	// *******************************************************************	

	@Override
	   	public List<RuleNameDTO> getAllRuleName() {

        List<RuleNameDTO> ruleNameList = new ArrayList<>();

		List<RuleName> ruleNames = ruleNameRepository.findAll();

		log.info("Request: RuleNameService"
				+ ".ruleNameRepository.findAll()"
				+ " - ListSize: {} ruleNames",
				ruleNames.size());


        for (RuleName ruleName : ruleNames) {
            RuleNameDTO ruleNameDTO = ruleNameMapper
            		.toRuleNameDTO(ruleName);
            ruleNameList.add(ruleNameDTO);
        }

        log.info("Request: ruleNameList.add(ruleNameDTO)"
        		+ " after ruleNameMapper.toRuleNameeDTO(ruleName)"
				+ " - ListSize: {} ruleNames", ruleNameList.size());

        return ruleNameList;
    }


	// *******************************************************************	

	@Override
    public RuleNameDTO getRuleNameById(final int ruleNameId) {


        RuleName ruleNameById = ruleNameRepository
        		.findById(ruleNameId)
        		.orElseThrow(() ->
                new DataNotFoundException("ID Not Found"));

        log.info("Request: ruleNameRepository.findById(ruleNameId)"
        		+ "Name: {} & Description: {} ",
        		ruleNameById.getName(), ruleNameById.getDescription());

        return ruleNameMapper.toRuleNameDTO(ruleNameById);
    }


	// *******************************************************************	

	@Override
    public RuleNameDTO addRuleName(final RuleNameDTO ruleNameDTO) {

        RuleName ruleNameToAdd = ruleNameMapper
        		.toRuleName(ruleNameDTO);

        log.info("Request: RuleName to ADD  "
        		+ "Name: {} & Description: {} ",
        		ruleNameToAdd.getName(), ruleNameToAdd.getDescription());

        RuleName ruleNameAdded = ruleNameRepository.save(ruleNameToAdd);

        log.info("Request: RuleName ADDED "
        		+ "Name: {} & Description: {} ",
        		ruleNameToAdd.getName(), ruleNameToAdd.getDescription());

        return ruleNameMapper
        		.toRuleNameDTO(ruleNameAdded);
    }


	// *******************************************************************	

	@Override
    public RuleNameDTO updateRuleName(
    		final int ruleNameId,
    		final RuleNameDTO ruleNameDTO) {

		ruleNameRepository.findById(ruleNameId)
        	.orElseThrow(() ->
                new DataNotFoundException("ID not FOUND"));


        log.info("Request: RuleName to UPDATE FOUND  "
        		+ "Name: {} & Description: {} ",
        		ruleNameId, ruleNameDTO.getDescription());


        RuleName ruleNameToUpdate = ruleNameMapper
        		.toRuleName(ruleNameDTO);

        ruleNameToUpdate.setId(ruleNameId);

        RuleName ruleNameUpdated = ruleNameRepository
        		.save(ruleNameToUpdate);

        log.info("Request: RuleName UPDATEED  "
        		+ "Name: {} & Description: {} ",
        		ruleNameId, ruleNameUpdated.getDescription());


        return ruleNameMapper
        		.toRuleNameDTO(ruleNameUpdated);
    }


	// *******************************************************************	

	@Override
    public void deleteRuleName(final int ruleNameId) {


        ruleNameRepository.findById(ruleNameId)
        	.orElseThrow(() ->
                new DataNotFoundException("ID Not Found"));

        ruleNameRepository.deleteById(ruleNameId);

    }


	// *******************************************************************	

}
