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


/**
 * The Class RuleNameService.
 */
@Log4j2
@Service
public class RuleNameService implements IRuleNameService {


    /** The rule name repository. */
    private final RuleNameRepository ruleNameRepository;

    /** The rule name mapper. */
    private final RuleNameMapper ruleNameMapper;


	// *******************************************************************

    /**
	 * Instantiates a new rule name service.
	 *
	 * @param ruleNameRepository the rule name repository
	 * @param ruleNameMapper the rule name mapper
	 */
	public RuleNameService(
    		final RuleNameRepository ruleNameRepository,
            final RuleNameMapper ruleNameMapper) {
    	this.ruleNameRepository = ruleNameRepository;
    	this.ruleNameMapper = ruleNameMapper;
    }


	// *******************************************************************

	/**
	 * Gets the all rule name.
	 *
	 * @return the all rule name
	 */
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
        		+ " after ruleNameMapper"
        		+ ".toRuleNameeDTO(ruleName)"
				+ " - ListSize: {} ruleNames",
				ruleNameList.size());

        return ruleNameList;
    }


	// *******************************************************************

	/**
	 * Gets the rule name by id.
	 *
	 * @param ruleNameId the rule name id
	 * @return the rule name by id
	 */
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

	/**
	 * Adds the rule name.
	 *
	 * @param ruleNameDTO the rule name DTO
	 * @return the rule name DTO
	 */
	@Override
    public RuleNameDTO addRuleName(final RuleNameDTO ruleNameDTO) {

        RuleName ruleNameToAdd = ruleNameMapper
        		.toRuleName(ruleNameDTO);

        log.info("Request: RuleName to ADD  "
        		+ "Name: {} & Description: {} ",
        		ruleNameToAdd.getName(), ruleNameToAdd
        		.getDescription());

        RuleName ruleNameAdded = ruleNameRepository
        		.save(ruleNameToAdd);

        log.info("Request: RuleName ADDED "
        		+ "Name: {} & Description: {} ",
        		ruleNameToAdd.getName(), ruleNameToAdd
        		.getDescription());

        return ruleNameMapper
        		.toRuleNameDTO(ruleNameAdded);
    }


	// *******************************************************************

	/**
	 * Update rule name.
	 *
	 * @param ruleNameId the rule name id
	 * @param ruleNameDTO the rule name DTO
	 * @return the rule name DTO
	 */
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

        log.info("Request: RuleName UPDATED SUCCESSFULLY "
        		+ "Name: {} & Description: {} ",
        		ruleNameId, ruleNameUpdated.getDescription());


        return ruleNameMapper
        		.toRuleNameDTO(ruleNameUpdated);
    }


	// *******************************************************************

	/**
	 * Delete rule name.
	 *
	 * @param ruleNameId the rule name id
	 */
	@Override
    public void deleteRuleName(final int ruleNameId) {


        ruleNameRepository.findById(ruleNameId)
        	.orElseThrow(() ->
                new DataNotFoundException("ID Not Found"));

        log.info("Request: RuleName to Delete FOUND  "
        		+ "Rule Name Id: {}",
        		ruleNameId);

        ruleNameRepository.deleteById(ruleNameId);

        log.info("Request: RuleName Deleted SUCCESSFULLY  ");

    }


	// *******************************************************************

}
