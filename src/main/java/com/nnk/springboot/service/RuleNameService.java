package com.nnk.springboot.service;

import java.util.List;

import com.nnk.springboot.dto.RuleNameDTO;
import com.nnk.springboot.repository.RuleNameRepository;
import com.nnk.springboot.util.RuleNameMapper;

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
		// TODO Auto-generated method stub
		return null;
	}


	// *******************************************************************	

	@Override
	public RuleNameDTO getRuleNameById(int ruleNameId) {
		// TODO Auto-generated method stub
		return null;
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
