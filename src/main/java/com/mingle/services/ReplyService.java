package com.mingle.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mingle.domain.repositories.ReplyReactionsRepository;
import com.mingle.domain.repositories.ReplyRepository;
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
	
	
	

}
