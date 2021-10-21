package com.nnk.springboot.util;

import org.springframework.stereotype.Component;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.dto.RuleNameDTO;


/**
 * The Class RuleNameMapper.
 */
@Component
public class RuleNameMapper {

    /**
     * To rule name DTO.
     *
     * @param ruleName the rule name
     * @return the rule name DTO
     */
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

    /**
     * To rule name.
     *
     * @param ruleNameDTO the rule name DTO
     * @return the rule name
     */
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
