package com.mingle.domain.repositories;

import java.time.Instant;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mingle.domain.entites.PartyInformation;
import com.mingle.domain.entites.TodayCalculationParty;

public interface PartyInformationRepository extends JpaRepository<PartyInformation, Long> {

	List<PartyInformation> findAllByServiceIdEquals(Long id);

	@Query(value = "select * from current_party_info WHERE service_id = :serviceId and DATE_ADD(start_date, INTERVAL month_count MONTH) >= CURRENT_DATE() order by start_date, id desc", nativeQuery = true)
	List<PartyInformation> findPartyInformationByServiceIdAndCount(@Param("serviceId") Long serviceId);
	
	@Query(value = "select * from current_party_info WHERE service_id = :serviceId and start_date >= :start and start_date <= :end and DATE_ADD(start_date, INTERVAL month_count MONTH) >= CURRENT_DATE() order by start_date, id desc", nativeQuery = true)
	List<PartyInformation> findPartyInformationByServiceIdAndCountAndStartDate(@Param("serviceId") Long serviceId, @Param("start") Instant start, @Param("end") Instant end);


	@Query(value="select count(*) from party_information where service_id=:serviceId and login_id=:loginId and DATE_ADD(start_date, INTERVAL month_count MONTH) >= CURRENT_DATE()", nativeQuery = true)
	Long idDupChk(@Param("serviceId") Long serviceId, @Param("loginId") String loginId);
	
	default boolean isIdDupChk(Long serviceId, String loginId) {
		return idDupChk(serviceId, loginId)>0;
	}
	
	@Query(value = "select * from current_party_info where DATE_ADD(current_party_info.start_date, INTERVAL current_party_info.month_count MONTH) >= CURRENT_DATE()", nativeQuery = true)
	List<PartyInformation> selectAllParty();
}
