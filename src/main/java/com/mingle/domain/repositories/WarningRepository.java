package com.mingle.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mingle.domain.entites.Warning;

public interface WarningRepository extends JpaRepository<Warning, String> {
	
	// 회원 경고 횟수
	@Query("select count(w) from Warning w where w.memberId = :memberId")
	long selectWarningCountByMemberId(@Param("memberId") String memberId);
}
