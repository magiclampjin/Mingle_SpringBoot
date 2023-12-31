package com.mingle.domain.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.mingle.domain.entites.TodayCalculationParty;
import com.mingle.dto.TodayCalculationPartyDTO;

public interface TodayCalculationPartyRepository extends JpaRepository<TodayCalculationParty, Long>{

	// 정산일마다 정산
	// 정산일이 오늘인 파티 (시작일이 오늘인 파티 포함)
	@Query(value = "select * from today_calculation_party;", nativeQuery = true)
	List<TodayCalculationParty> findTodayCalculationParty();
	
	// 시작일 이후 정산일 이전 정산
	// 오늘이 시작일이면서, 정산일이 오늘이 아닌 파티
	@Query(value="select * from first_calculation_party;",  nativeQuery = true)
	List<TodayCalculationParty> findFirstCalculationParty();
	
}
