package com.nnk.springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nnk.springboot.domain.Trade;

/**
 * The Interface TradeRepository.
 */
@Repository
public interface TradeRepository extends JpaRepository<Trade, Integer> {
}
