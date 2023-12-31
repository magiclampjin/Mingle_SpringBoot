package com.mingle.services;

import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mingle.domain.entites.Reply;
import com.mingle.domain.repositories.MemberRepository;
import com.mingle.domain.repositories.ReplyRepository;
import com.mingle.dto.ReplyDTO;
import com.mingle.dto.UploadReplyDTO;
import com.mingle.mappers.ReplyMapper;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class ReplyService {
	
	@Autowired
	private ReplyRepository rRepo;
	
	@Autowired
	private ReplyMapper rMapper;
	
	@Autowired
	private MemberRepository mRepo;
	
	
	
	//댓글 삽입
	@Transactional
	public ReplyDTO insert(UploadReplyDTO dto) {
		
		Reply reply = new Reply();
		reply.setContent(dto.getContent());
		reply.setPostId(dto.getPostId());
		reply.setMember(mRepo.selectMypageInfo(dto.getMemberId()));
		if(dto.getReplyParentId()>0) {
			reply.setParentReply(rRepo.findReplyById(dto.getReplyParentId()));
		}
//		else {
//			reply.setChildrenReplies(rRepo.findChildRepliesById(dto.getReplyParentId()));
//		}
		if(dto.getReplyAdoptiveParentId()>0) {
			reply.setReplyAdoptiveParentId(dto.getReplyAdoptiveParentId());
		}
		
		reply.setWriteDate(Timestamp.from(dto.getWriteDate()));
		
		return rMapper.toDto(rRepo.save(reply));
	}
	
	// 댓글 수정
	@Transactional
	public ReplyDTO updateById(Long id, String content) {
	    Reply reply = rRepo.findById(id)
	                      .orElseThrow(() -> new EntityNotFoundException("Reply not found with id: " + id));
	    reply.setContent(content);
	    return rMapper.toDto(rRepo.save(reply));
	}
	
	// 댓글 삭제
	public void deleteById(Long id) {
		Reply reply = rRepo.findById(id).get();
		rRepo.delete(reply);
	}
	
	

	
	
	

}
