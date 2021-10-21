package com.nnk.springboot.repository;

import com.nnk.springboot.domain.BidList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * The Interface BidListRepository.
 */
@Repository
public interface BidListRepository extends JpaRepository<BidList, Integer> {

}
