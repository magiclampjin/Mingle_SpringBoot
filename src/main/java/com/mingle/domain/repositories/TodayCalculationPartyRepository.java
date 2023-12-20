package com.mingle.domain.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.mingle.domain.entites.TodayCalculationParty;

public interface TodayCalculationPartyRepository extends JpaRepository<TodayCalculationParty, Long>{

	@Query(value = "select * from today_calculation_party;", nativeQuery = true)
	List<TodayCalculationParty> findTodayCalculationParty();
}
