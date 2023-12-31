package com.mingle.domain.repositories;

import java.time.Instant;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mingle.domain.entites.PartyInformation;

import jakarta.transaction.Transactional;

public interface PartyInformationRepository extends JpaRepository<PartyInformation, Long> {

	List<PartyInformation> findAllByServiceIdEquals(Long id);

	// 선택한 서비스에서 가입 가능한 파티 목록
	@Query(value = "select * from current_party_info WHERE service_id = :serviceId and DATE_ADD(start_date, INTERVAL month_count MONTH) >= CURRENT_DATE() order by start_date, id desc", nativeQuery = true)
	List<PartyInformation> findPartyInformationByServiceIdAndCount(@Param("serviceId") Long serviceId);
	
	// 선택한 서비스에서 가입 가능한 파티 목록 중 시작일자로 검색한 내역
	@Query(value = "select * from current_party_info WHERE service_id = :serviceId and start_date >= :start and start_date <= :end and DATE_ADD(start_date, INTERVAL month_count MONTH) >= CURRENT_DATE() order by start_date, id desc", nativeQuery = true)
	List<PartyInformation> findPartyInformationByServiceIdAndCountAndStartDate(@Param("serviceId") Long serviceId, @Param("start") Instant start, @Param("end") Instant end);


	// 이미 가입한 서비스인지 판별
	@Query(value="select count(*) from party_information where service_id=:serviceId and login_id=:loginId and DATE_ADD(start_date, INTERVAL month_count MONTH) >= CURRENT_DATE()", nativeQuery = true)
	Long idDupChk(@Param("serviceId") Long serviceId, @Param("loginId") String loginId);
	
	default boolean isIdDupChk(Long serviceId, String loginId) {
		return idDupChk(serviceId, loginId)>0;
	}
	
	// 메인화면에서 모집 중인 보여줄 파티 개수
	@Query(value = "select * from current_party_info where DATE_ADD(current_party_info.start_date, INTERVAL current_party_info.month_count MONTH) >= CURRENT_DATE()", nativeQuery = true)
	List<PartyInformation> selectAllParty();
	
	// 3개월 경과된 파티 삭제
	@Transactional
	@Modifying
	@Query(value = "delete from party_information where DATE_ADD(start_date, INTERVAL month_count+3 MONTH) < CURRENT_DATE()", nativeQuery = true)
	int deleteEndDateAfter3Months();
	
	@Transactional
	@Modifying
	@Query(value="update party_information set login_pw=null, login_id=null where DATE_ADD(start_date, INTERVAL month_count MONTH) < CURRENT_DATE()", nativeQuery = true)
	void updateEndPartyAccount();
	
}
