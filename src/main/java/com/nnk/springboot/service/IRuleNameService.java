package com.nnk.springboot.service;

import java.util.List;

import com.nnk.springboot.dto.RuleNameDTO;

public interface IRuleNameService {


	List<RuleNameDTO> getAllRuleName();

	RuleNameDTO getRuleNameById(final int ruleNameId);

	RuleNameDTO addRuleName(final RuleNameDTO ruleName);

	RuleNameDTO updateRuleName(
			final int ruleNameId,
			final RuleNameDTO ruleName);

	void deleteRuleName(final int ruleNameId);

}
