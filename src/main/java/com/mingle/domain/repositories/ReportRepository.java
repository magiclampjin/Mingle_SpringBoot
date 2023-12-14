package com.mingle.domain.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mingle.domain.entites.Report;

public interface ReportRepository extends JpaRepository<Report, Long> {

	// 미처리 신고 리스트
	@Query("select r from Report r where isProcess = false order by reportDate desc")
	List<Report> selectAllByIsProcessFalseOrderByReportDateDesc();
	
	// 미처리 게시물 신고 리스트
	@Query("select r from Report r join ReportPost rp on r.id = rp.reportId where r.isProcess = false order by reportDate desc")
	List<Report> selectAllByReportPost();
	
	// 미처리 댓글 신고 리스트
	@Query("select r from Report r join ReportReply rr on r.id = rr.reportId where r.isProcess = false order by reportDate desc")
	List<Report> selectAllByReportReply();
	
	// 미처리 파티 신고 리스트
	@Query("select r from Report r join ReportParty rp on r.id = rp.reportId where r.isProcess = false order by reportDate desc")
	List<Report> selectAllByReportParty();
	
	// 미처리 파티 카테고리 리스트 (계정/댓글/미납)
	@Query("select r from Report r join ReportParty rp on r.id = rp.reportId where rp.partyReportCategoryId = :category and r.isProcess = false order by reportDate desc")
	List<Report> selectAllByReportPartyCategoryList(@Param("category") String category);
}
