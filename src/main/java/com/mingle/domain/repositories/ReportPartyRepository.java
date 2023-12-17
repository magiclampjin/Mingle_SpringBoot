package com.mingle.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mingle.domain.entites.ReportParty;

public interface ReportPartyRepository extends JpaRepository<ReportParty, Long> {
	// 파티 신고 상세 정보
	@Query("select rp from ReportParty rp join fetch rp.report where rp.reportId = :id")
	ReportParty selectPartyById(@Param("id") Long id);
}
