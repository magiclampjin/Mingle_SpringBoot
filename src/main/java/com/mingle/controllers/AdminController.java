package com.mingle.controllers;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mingle.domain.entites.Warning;
import com.mingle.dto.PostDTO;
import com.mingle.dto.ReportDTO;
import com.mingle.dto.ReportPartyDTO;
import com.mingle.dto.ReportPostDTO;
import com.mingle.dto.ReportReplyDTO;
import com.mingle.services.PartyService;
import com.mingle.services.PostService;
import com.mingle.services.ReportService;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
	
	private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

	@Autowired
	private ReportService rServ;
	
	@Autowired
	private PostService pServ;
	
	@Autowired
	private PartyService ptServ;
	
	// 미처리 신고 리스트 (전체)
	@GetMapping("/reportList")
	public ResponseEntity<List<ReportDTO>> findTop10ByIsProcessFalseOrderByReportDateDesc() {
		List<ReportDTO> list = rServ.findTop10ByIsProcessFalseOrderByReportDateDesc();
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
	public ResponseEntity<List<ReportDTO>> selectTop10ByReportParty() {
		List<ReportDTO> list = rServ.selectTop10ByReportParty();
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
	
	// 회원 경고
	@PostMapping("/giveWarning")
	public ResponseEntity<Void> insertWarningByMemberId(@RequestBody Warning warning) {
		rServ.insertWarningByMemberId(warning);
		return ResponseEntity.ok().build();
	}
	
	// 회원 경고 횟수
	@GetMapping("/memberWarningCount/{memberId}")
	public ResponseEntity<Long> selectWarningCountByMemberId(@PathVariable String memberId) {
		Long warningCount = rServ.selectWarningCountByMemberId(memberId);
		logger.debug("warningCount: " + warningCount);
		return ResponseEntity.ok(warningCount);
	}
	
	// 신고 처리
	@PutMapping("/reportProcess/{id}")
	public ResponseEntity<Void> updateReportProcess(@PathVariable Long id) {
		rServ.updateReportProcess(id);
		return ResponseEntity.ok().build();
	}	
	
// ----------------------------------------------------------------
	
	// 공지 게시판 리스트
	@GetMapping("/noticeBoardList")
	public ResponseEntity<List<PostDTO>> selectNoticePosts() {
		List<PostDTO> list = pServ.selectNoticePosts();
		return ResponseEntity.ok(list);
	}
	
	// 고정 중인 공지글 리스트
	@GetMapping("/fixedNoticeBoardList")
	public ResponseEntity<List<PostDTO>> selectByFixedNotice() {
		List<PostDTO> list = pServ.selectByFixedNotice();
		return ResponseEntity.ok(list);
	}
	
	// 고정 중이 아닌 공지글 리스트
	@GetMapping("/unfixedMoticeBoardList")
	public ResponseEntity<List<PostDTO>> selectByUnFixedNotice() {
		List<PostDTO> list = pServ.selectByUnfixedNotice();
		return ResponseEntity.ok(list);
	}
	
	// 공지글 고정
	@PutMapping("/fixNoticeBoard/{id}")
	public ResponseEntity<Void> updateNoticeFixTrue(@PathVariable Long id) {
		pServ.updateNoticeFixTrue(id);
		return ResponseEntity.ok().build();
	}
	
	// 공지글 고정 해제
	@PutMapping("/unfixNoticeBoard/{id}")
	public ResponseEntity<Void> updateNoticeFixFalse(@PathVariable Long id) {
		pServ.updateNoticeFixFalse(id);
		return ResponseEntity.ok().build();
	}
	
// ----------------------------------------------------------------
	
	// 서비스별 파티 이용자수
	@GetMapping("/serviceUserCount")
	public ResponseEntity<List<Map<String,Object>>> selectCountUserByService() {
		List<Map<String,Object>> list = ptServ.selectCountUserByService();
		return ResponseEntity.ok(list);
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<String> exceptionHandler(Exception e) {
		logger.error(e.getMessage());
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	}
}
