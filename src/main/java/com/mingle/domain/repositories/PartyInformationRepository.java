package com.mingle.domain.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mingle.domain.entites.PartyInformation;

public interface PartyInformationRepository extends JpaRepository<PartyInformation, Long> {

	List<PartyInformation> findAllByServiceIdEquals(Long id);

//	@Query(value = "SELECT * FROM party_information WHERE id IN (SELECT pr.id FROM Party_registration pr "
//			+ "		JOIN party_information pi ON pr.id = pi.id "
//			+ "  LEFT JOIN party_member pm ON pr.id = pm.party_registration_id WHERE pi.service_id = :serviceId "
//			+ "  GROUP BY pr.id HAVING COUNT(pm.party_registration_id) < pi.people_count)", nativeQuery = true)
//	List<PartyInformation> findPartyInformationByServiceIdAndCount(@Param("serviceId") Long serviceId);
	
	@Query(value = "select * from current_party_info WHERE service_id = :serviceId ", nativeQuery = true)
	List<PartyInformation> findPartyInformationByServiceIdAndCount(@Param("serviceId") Long serviceId);

}
