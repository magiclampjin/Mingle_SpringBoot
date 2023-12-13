package com.mingle.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mingle.domain.entites.ReportPost;

public interface ReportPostRepository extends JpaRepository<ReportPost, Long> {
	
	// 게시물 신고 상세 정보
	@Query("select rp from ReportPost rp join fetch rp.report join fetch rp.post where rp.reportId = :id")
	ReportPost selectPostByIdEquals(@Param("id") Long id);
}
