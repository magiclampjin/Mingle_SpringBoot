package com.mingle.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mingle.domain.entites.ReportReply;

public interface ReportReplyRepository extends JpaRepository<ReportReply, Long> {

	// 댓글 신고 상세 정보
	@Query("select rr from ReportReply rr join fetch rr.report join fetch rr.reply where rr.reportId = :id")
	ReportReply selectReplyById(@Param("id") Long id);
}
