package com.nnk.springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nnk.springboot.domain.RuleName;

/**
 * The Interface RuleNameRepository.
 */
@Repository
public interface RuleNameRepository extends JpaRepository<RuleName, Integer> {
}
