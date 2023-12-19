package com.mingle.services;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.mingle.domain.entites.Payment;
import com.mingle.domain.repositories.PaymentRepository;
import com.mingle.dto.PaymentDTO;
import com.mingle.mappers.PaymentMapper;
import com.mingle.specification.PaymentSpecification;

@Service
public class PaymentService {

	@Autowired
	private PaymentRepository pRepo;
	
	@Autowired
	private PaymentMapper pMapper;
	
	// 결제 내역 목록 불러오기
	public List<PaymentDTO> selectById(String memberId) {
		
		return pMapper.toDtoList(pRepo.selectById(memberId));
	    
	}
	
	public List<PaymentDTO> findSearch(String member_id,String service, String type, Timestamp start, Timestamp end){
	
		// 내 아이디는 무조건 기본으로 where로 검색되어야 함
		Specification<Payment> pSpec = Specification.where(PaymentSpecification.findByMemberId(member_id));
		
		// 각각이 null이 아니면 검색
		if(service!=null) {
			pSpec = pSpec.and(PaymentSpecification.findByServiceId(service));
		}
		if(type!=null) {
			pSpec = pSpec.and(PaymentSpecification.findByPaymentTypeId(type));
		}
		if(start != null && end != null) {
			pSpec = pSpec.and(PaymentSpecification.findByDate(start, end));
		}
		
		  return pMapper.toDtoList(pRepo.findAll(pSpec));
		    
	}

}
