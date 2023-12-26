package com.mingle.services;

import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mingle.domain.entites.Reply;
import com.mingle.domain.repositories.MemberRepository;
import com.mingle.domain.repositories.PostRepository;
import com.mingle.domain.repositories.ReplyReactionsRepository;
import com.mingle.domain.repositories.ReplyRepository;
import com.mingle.dto.ReplyDTO;
import com.mingle.dto.UploadReplyDTO;
import com.mingle.mappers.ReplyMapper;
import com.mingle.mappers.ReplyReactionsMapper;

import jakarta.transaction.Transactional;

@Service
public class ReplyService {
	
	@Autowired
	private ReplyRepository rRepo;
	
	@Autowired
	private ReplyMapper rMapper;
	
	@Autowired
	private ReplyReactionsRepository rrRepo;
	
	@Autowired
	private ReplyReactionsMapper rrMapper;
	
	@Autowired
	private MemberRepository mRepo;
	
	@Autowired
	private PostRepository pRepo;
	
	
	
	//댓글 삽입
	@Transactional
	public ReplyDTO insert(UploadReplyDTO dto) {
		
		Reply reply = new Reply();
		reply.setContent(dto.getContent());
		reply.setPostId(dto.getPostId());
		reply.setMember(mRepo.selectMypageInfo(dto.getMemberId()));
		if(dto.getReplyParentId()>0) {
			reply.setChildrenReplies(rRepo.findChildRepliesById(dto.getReplyParentId()));
			reply.setParentReply(rRepo.findReplyById(dto.getReplyParentId()));
		}
		if(dto.getReplyAdoptiveParentId()>0) {
			reply.setReplyAdoptiveParentId(dto.getReplyAdoptiveParentId());
		}
		
		reply.setWriteDate(Timestamp.from(dto.getWriteDate()));
		
		return rMapper.toDto(rRepo.save(reply));
	}
	
	// 댓글 수정
	public ReplyDTO updateById(Long id, ReplyDTO dto) {
		Reply reply = rRepo.findById(id).get();
		rMapper.updateEntityFromDTO(dto, reply);
		return rMapper.toDto(rRepo.save(reply));
	}
	
	// 댓글 삭제
	public void deleteById(Long id) {
		Reply reply = rRepo.findById(id).get();
		rRepo.delete(reply);
	}
	
	
	// 댓글 좋아요
	
	
	
	// 댓글 싫어요
	
	
	

}
