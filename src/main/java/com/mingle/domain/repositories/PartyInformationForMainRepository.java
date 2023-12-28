package com.mingle.domain.repositories;

import java.time.Instant;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mingle.domain.entites.PartyInformation;
import com.mingle.domain.entites.PartyInformationForMain;


public interface PartyInformationForMainRepository extends JpaRepository<PartyInformationForMain, Long>{
//	@Query(value="select current_party_info.*,service.price as price, service.max_people_count, service.english_name from current_party_info join service on current_party_info.service_id=service.id  WHERE start_date >= :start and start_date <= :end order by start_date, current_party_info.id desc limit 12;" , nativeQuery = true)
	@Query(value="select current_party_info.id, current_party_info.people_count, current_party_info.service_id, current_party_info.start_date, current_party_info.month_count, current_party_info.calculation_date,service.price as price, service.max_people_count, service.english_name from current_party_info join service on current_party_info.service_id=service.id order by start_date asc limit 12", nativeQuery = true)
	List<PartyInformationForMain> findPartyInfoForMain();
}
