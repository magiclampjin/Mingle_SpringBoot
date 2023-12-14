package com.mingle.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mingle.dto.ReportDTO;
import com.mingle.dto.ReportPartyDTO;
import com.mingle.dto.ReportPostDTO;
import com.mingle.dto.ReportReplyDTO;
import com.mingle.services.ReportService;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

	@Autowired
	private ReportService rServ;
	
	// 미처리 신고 리스트 (전체)
	@GetMapping("/reportList")
	public ResponseEntity<List<ReportDTO>> selectAllByIsProcessFalseOrderByReportDateDesc() {
		List<ReportDTO> list = rServ.selectAllByIsProcessFalseOrderByReportDateDesc();
		return ResponseEntity.ok(list);
	}
	
	// 미처리 게시물 신고 리스트
	@GetMapping("/reportPostList")
	public ResponseEntity<List<ReportDTO>> selectAllByReportPost() {
		List<ReportDTO> list = rServ.selectAllByReportPost();
		return ResponseEntity.ok(list);
	}
	
	// 미처리 댓글 신고 리스트
	@GetMapping("/reportReplyList")
	public ResponseEntity<List<ReportDTO>> selectAllByReportReply() {
		List<ReportDTO> list = rServ.selectAllByReportReply();
		return ResponseEntity.ok(list);
	}
	
	// 미처리 파티 신고 리스트 (전체)
	@GetMapping("/reportPartyList")
	public ResponseEntity<List<ReportDTO>> selectAllByReportParty() {
		List<ReportDTO> list = rServ.selectAllByReportParty();
		return ResponseEntity.ok(list);
	}
	
	// 미처리 파티 카테고리 리스트 (계정/댓글/미납/채팅)
	@GetMapping("/reportPartyCategoryList/{category}")
	public ResponseEntity<List<ReportDTO>> selectAllByReportPartyCategoryList(@PathVariable String category) {
		List<ReportDTO> list = rServ.selectAllByReportPartyCategoryList(category);
		return ResponseEntity.ok(list);
	}
	
	// 게시물 신고 상세 정보
	@GetMapping("/reportPostDetailInfo/{id}")
	public ResponseEntity<ReportPostDTO> selectPostById(@PathVariable Long id) {
		ReportPostDTO dto = rServ.selectPostById(id);
		return ResponseEntity.ok(dto);
	}
	
	// 댓글 신고 상세 정보
	@GetMapping("/reportReplyDetailInfo/{id}")
	public ResponseEntity<ReportReplyDTO> selectReplyById(@PathVariable Long id) {
		ReportReplyDTO dto = rServ.selectReplyById(id);
		return ResponseEntity.ok(dto);
	}
	
	// 파티 신고 상세 정보
	@GetMapping("/reportPartyDetailInfo/{id}")
	public ResponseEntity<ReportPartyDTO> selectPartyById(@PathVariable Long id) {
		ReportPartyDTO dto = rServ.selectPartyById(id);
		return ResponseEntity.ok(dto);
	}
}
