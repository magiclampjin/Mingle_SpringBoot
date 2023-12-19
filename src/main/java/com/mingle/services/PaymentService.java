package com.mingle.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mingle.domain.repositories.PaymentRepository;
import com.mingle.dto.PaymentDTO;
import com.mingle.mappers.PaymentMapper;

@Service
public class PaymentService {

	@Autowired
	private PaymentRepository pRepo;
	
	@Autowired
	private PaymentMapper pMapper;
	
	public List<PaymentDTO> selectById(String memberId) {
		
		return pMapper.toDtoList(pRepo.selectById(memberId));
	    
	}

}
