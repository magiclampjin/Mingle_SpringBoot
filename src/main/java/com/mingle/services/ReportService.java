package com.mingle.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.mingle.domain.entites.Report;
import com.mingle.domain.entites.ReportPost;
import com.mingle.domain.entites.ReportReply;
import com.mingle.domain.entites.Warning;
import com.mingle.domain.repositories.PostRepository;
import com.mingle.domain.repositories.ReplyRepository;
import com.mingle.domain.repositories.ReportPartyRepository;
import com.mingle.domain.repositories.ReportPostRepository;
import com.mingle.domain.repositories.ReportReplyRepository;
import com.mingle.domain.repositories.ReportRepository;
import com.mingle.domain.repositories.WarningRepository;
import com.mingle.dto.ReportDTO;
import com.mingle.dto.ReportPartyDTO;
import com.mingle.dto.ReportPostDTO;
import com.mingle.dto.ReportReplyDTO;
import com.mingle.mappers.ReportMapper;
import com.mingle.mappers.ReportPartyMapper;
import com.mingle.mappers.ReportPostMapper;
import com.mingle.mappers.ReportReplyMapper;

import jakarta.transaction.Transactional;

@Service
public class ReportService {

	// 신고
	@Autowired
	private ReportRepository rRepo;
	@Autowired
	private ReportMapper rMapper;
	
	// 게시글 신고
	@Autowired
	private ReportPostRepository rpRepo;
	@Autowired
	private ReportPostMapper rpMapper;
	
	
	// 댓글 신고
	@Autowired
	private ReportReplyRepository rrRepo;
	@Autowired
	private ReportReplyMapper rrMapper;
	
	// 파티 신고
	@Autowired
	private ReportPartyRepository rptRepo;
	@Autowired
	private ReportPartyMapper rptMapper;
	
	// 경고
	@Autowired
	private WarningRepository wRepo;
	
	// 게시글 정보
	@Autowired
	private PostRepository pRepo;
	
	// 댓글 정보
	@Autowired
	private ReplyRepository rpyRepo;
	
	// 미처리 신고 리스트
	public List<ReportDTO> findTop10ByIsProcessFalseOrderByReportDateDesc() {
		return rMapper.toDtoList(rRepo.findTop10ByIsProcessFalseOrderByReportDateDesc());
	}
	
	// 미처리 게시물 신고 리스트
	public List<ReportDTO> selectAllByReportPost() {
		return rMapper.toDtoList(rRepo.selectAllByReportPost());
	}
	
	// 미처리 댓글 신고 리스트
	public List<ReportDTO> selectAllByReportReply() {
		return rMapper.toDtoList(rRepo.selectAllByReportReply());
	}
	
	// 미처리 파티 신고 리스트
	public List<ReportDTO> selectTop10ByReportParty() {
		return rMapper.toDtoList(rRepo.selectTop10ByReportParty(PageRequest.of(0, 10)));
	}
	
	// 미처리 파티 카테고리 리스트 (계정/댓글/미납)
	public List<ReportDTO> selectAllByReportPartyCategoryList(String category) {
		return rMapper.toDtoList(rRepo.selectAllByReportPartyCategoryList(category));
	}
	
	// 게시글 신고 상세 정보
	public ReportPostDTO selectPostById(Long id) {
		return rpMapper.toDto(rpRepo.selectPostById(id));
	}
	
	// 댓글 신고 상세 정보
	public ReportReplyDTO selectReplyById(Long id) {
		return rrMapper.toDto(rrRepo.selectReplyById(id));
	}
	
	// 파티 신고 상세 정보
	public ReportPartyDTO selectPartyById(Long id) {
		return rptMapper.toDto(rptRepo.selectPartyById(id));
	}
	
	// 회원 경고
	public void insertWarningByMemberId(Warning warning) {
		wRepo.save(warning);
	}
	
	// 회원 경고 횟수
	public Long selectWarningCountByMemberId(String memberId) {
		return wRepo.selectWarningCountByMemberId(memberId);
	}
	
	// 신고 처리
	public void updateReportProcess(Long id) {
		Report report = rRepo.findAllById(id); // 해당하는 report 가져옴
		report.setIsProcess(true);
		rRepo.save(report); 
	}
	
	// 게시글 신고 처리
	@Transactional
	public void insertPostReport(ReportDTO rdto, ReportPostDTO rpdto) {
		Report report = rRepo.save(rMapper.toEntity(rdto));
		ReportPost reportPost = new ReportPost();
		reportPost.setReportId(report.getId());
		reportPost.setPostId(rpdto.getPostId());
		reportPost.setReport(rRepo.findAllById(reportPost.getReportId()));
		reportPost.setPost(pRepo.findAllById(reportPost.getPostId()));
		rpRepo.save(reportPost);
	}
	
	// 댓글 신고 처리
	@Transactional
	public void insertReplyReport(ReportDTO rdto, ReportReplyDTO rrdto) {
		Report report = rRepo.save(rMapper.toEntity(rdto));
		ReportReply reportReply = new ReportReply();
		reportReply.setReportId(report.getId());
		reportReply.setReplyId(rrdto.getReplyId());
		reportReply.setReport(rRepo.findAllById(reportReply.getReportId()));
		reportReply.setReply(rpyRepo.findReplyById(reportReply.getReplyId()));
		rrRepo.save(reportReply);
	}
}
