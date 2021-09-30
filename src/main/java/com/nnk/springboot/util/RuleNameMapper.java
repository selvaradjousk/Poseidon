package com.nnk.springboot.util;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.dto.RuleNameDTO;

public class RuleNameMapper {

    public RuleNameDTO toRuleNameDTO(final RuleName ruleName) {

        return new RuleNameDTO(
        		ruleName.getId(),
        		ruleName.getName(),
        		ruleName.getDescription(),
        		ruleName.getJson(),
                ruleName.getTemplate(),
                ruleName.getSqlStr(),
                ruleName.getSqlPart());
    }

    public RuleName toRuleName(final RuleNameDTO ruleNameDTO) {

        return new RuleName(
        		ruleNameDTO.getName(),
        		ruleNameDTO.getDescription(),
        		ruleNameDTO.getJson(),
                ruleNameDTO.getTemplate(),
                ruleNameDTO.getSqlStr(),
                ruleNameDTO.getSqlPart());
    }

}
