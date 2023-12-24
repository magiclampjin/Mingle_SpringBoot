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

	@Query(value = "select * from current_party_info WHERE service_id = :serviceId order by start_date, id desc", nativeQuery = true)
	List<PartyInformation> findPartyInformationByServiceIdAndCount(@Param("serviceId") Long serviceId);
	
	@Query(value = "select * from current_party_info WHERE service_id = :serviceId and start_date >= :start and start_date <= :end order by start_date, id desc", nativeQuery = true)
	List<PartyInformation> findPartyInformationByServiceIdAndCountAndStartDate(@Param("serviceId") Long serviceId, @Param("start") Instant start, @Param("end") Instant end);


	@Query("select count(*) from PartyInformation pi where pi.serviceId=:serviceId and pi.loginId=:loginId")
	Long idDupChk(@Param("serviceId") Long serviceId, @Param("loginId") String loginId);
	
	default boolean isIdDupChk(Long serviceId, String loginId) {
		return idDupChk(serviceId, loginId)>0;
	}
}
