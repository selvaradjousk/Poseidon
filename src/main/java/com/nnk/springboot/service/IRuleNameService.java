package com.nnk.springboot.service;

import java.util.List;

import com.nnk.springboot.dto.RuleNameDTO;


/**
 * The Interface IRuleNameService.
 */
public interface IRuleNameService {


	/**
	 * Gets the all rule name.
	 *
	 * @return the all rule name
	 */
	List<RuleNameDTO> getAllRuleName();

	/**
	 * Gets the rule name by id.
	 *
	 * @param ruleNameId the rule name id
	 * @return the rule name by id
	 */
	RuleNameDTO getRuleNameById(int ruleNameId);

	/**
	 * Adds the rule name.
	 *
	 * @param ruleName the rule name
	 * @return the rule name DTO
	 */
	RuleNameDTO addRuleName(RuleNameDTO ruleName);

	/**
	 * Update rule name.
	 *
	 * @param ruleNameId the rule name id
	 * @param ruleName the rule name
	 * @return the rule name DTO
	 */
	RuleNameDTO updateRuleName(
			int ruleNameId,
			RuleNameDTO ruleName);

	/**
	 * Delete rule name.
	 *
	 * @param ruleNameId the rule name id
	 */
	void deleteRuleName(int ruleNameId);

}
