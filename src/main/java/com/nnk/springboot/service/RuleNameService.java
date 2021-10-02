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


        RuleName ruleName = ruleNameRepository
        		.findById(ruleNameId)
        		.orElseThrow(() ->
                new DataNotFoundException("ID Not Found"));

        return ruleNameMapper.toRuleNameDTO(ruleName);
    }


	// *******************************************************************	

	@Override
	public RuleNameDTO addRuleName(RuleNameDTO ruleName) {
		// TODO Auto-generated method stub
		return null;
	}


	// *******************************************************************	

	@Override
	public RuleNameDTO updateRuleName(
			int ruleNameId,
			RuleNameDTO ruleName) {
		// TODO Auto-generated method stub
		return null;
	}


	// *******************************************************************	

	@Override
	public void deleteRuleName(int ruleNameId) {
		// TODO Auto-generated method stub
		
	}


	// *******************************************************************	

}
