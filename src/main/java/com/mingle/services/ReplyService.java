package com.mingle.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mingle.domain.entites.Reply;
import com.mingle.domain.repositories.ReplyReactionsRepository;
import com.mingle.domain.repositories.ReplyRepository;
import com.mingle.dto.ReplyDTO;
import com.mingle.mappers.ReplyMapper;
import com.mingle.mappers.ReplyReactionsMapper;

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
	
	
	// 댓글 수정
	public void updateById(Long id, ReplyDTO dto) {
		Reply reply = rRepo.findById(id).get();
		rMapper.updateEntityFromDTO(dto, reply);
		rRepo.save(reply);
	}
	
	// 댓글 삭제
	public void deleteById(Long id) {
		Reply reply = rRepo.findById(id).get();
		rRepo.delete(reply);
	}
	
	

}
