package com.mingle.domain.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.mingle.domain.entites.CurrJoinPartyInfo;
import com.mingle.dto.CurrJoinPartyInfoDTO;

public interface CurrJoinPartyInfoRepository  extends JpaRepository<CurrJoinPartyInfo, Long>{
	
	// 가입된 파티 목록
	@Query("select new com.mingle.dto.CurrJoinPartyInfoDTO(p.id, p.memberId, p.isPartyManager, p.startDate, p.name, p.englishName, p.plan) from CurrJoinPartyInfo p where p.memberId=:memberId and p.isExpired = false order by p.startDate")
	List<CurrJoinPartyInfoDTO> selectMyPartyList(String memberId);
	
	// 가입된 파티 목록
	@Query("select new com.mingle.dto.CurrJoinPartyInfoDTO(p.id, p.memberId, p.isPartyManager, p.startDate, p.name, p.englishName, p.plan, p.monthCount) from CurrJoinPartyInfo p where p.memberId=:memberId order by p.startDate")
	List<CurrJoinPartyInfoDTO> selectMyAllPartyList(String memberId);
	
	// 특정 파티 정보 (가입된 파티)
	@Query("select p from CurrJoinPartyInfo p where p.id=:id and p.memberId=:memberId")
	CurrJoinPartyInfo selectMyPartyInfo(Long id, String memberId);
}
