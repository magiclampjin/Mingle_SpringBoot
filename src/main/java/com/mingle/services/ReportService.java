package com.mingle.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mingle.domain.repositories.ReportPostRepository;
import com.mingle.domain.repositories.ReportReplyRepository;
import com.mingle.domain.repositories.ReportRepository;
import com.mingle.dto.ReportDTO;
import com.mingle.dto.ReportPostDTO;
import com.mingle.dto.ReportReplyDTO;
import com.mingle.mappers.ReportMapper;
import com.mingle.mappers.ReportPostMapper;
import com.mingle.mappers.ReportReplyMapper;

@Service
public class ReportService {

	@Autowired
	private ReportRepository rRepo;
	
	@Autowired
	private ReportPostRepository rpRepo;
	
	@Autowired
	private ReportReplyRepository rrRepo;
	
	@Autowired
	private ReportMapper rMapper;
	
	@Autowired
	private ReportPostMapper rpMapper;
	
	@Autowired
	private ReportReplyMapper rrMapper;
	
	// 미처리 신고 리스트
	public List<ReportDTO> selectAllByIsProcessFalseOrderByReportDateDesc() {
		return rMapper.toDtoList(rRepo.selectAllByIsProcessFalseOrderByReportDateDesc());
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
	public List<ReportDTO> selectAllByReportParty() {
		return rMapper.toDtoList(rRepo.selectAllByReportParty());
	}
	
	// 미처리 파티 카테고리 리스트 (계정/댓글/미납)
	public List<ReportDTO> selectAllByReportPartyCategoryList(String category) {
		return rMapper.toDtoList(rRepo.selectAllByReportPartyCategoryList(category));
	}
	
	// 게시글 신고 상세 정보
	public ReportPostDTO selectPostByIdEquals(Long id) {
		return rpMapper.toDto(rpRepo.selectPostByIdEquals(id));
	}
	
	// 댓글 신고 상세 정보
	public ReportReplyDTO selectReplyByIdEquals(Long id) {
		return rrMapper.toDto(rrRepo.selectReplyByIdEquals(id));
	}
}
