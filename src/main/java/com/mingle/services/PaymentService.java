package com.mingle.services;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoField;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.mingle.domain.entites.Payment;
import com.mingle.domain.repositories.PaymentRepository;
import com.mingle.domain.repositories.TodayCalculationPartyRepository;
import com.mingle.dto.PaymentDTO;
import com.mingle.dto.TodayCalculationPartyDTO;
import com.mingle.mappers.PaymentMapper;
import com.mingle.mappers.TodayCalculationPartyMapper;
import com.mingle.specification.PaymentSpecification;

@Service
public class PaymentService {

	@Autowired
	private PaymentRepository pRepo;
	
	@Autowired
	private PaymentMapper pMapper;
	
	
	@Autowired
	private TodayCalculationPartyRepository tcpRepo;
	@Autowired
	private TodayCalculationPartyMapper tcpMap;
	
	// 결제 내역 목록 불러오기
//	public List<PaymentDTO> selectById(String memberId) {
//		
//		return pMapper.toDtoList(pRepo.selectById(memberId));
//	    
//	}
	
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
	
	/*
	 
	 // 다음 정산일까지 남은 날짜 계산
	LocalDateTime currentDateTime = LocalDateTime.now();
	LocalDateTime now = currentDateTime.withHour(0).withMinute(0).withSecond(0).withNano(0);
	LocalDateTime cal = now.with(ChronoField.DAY_OF_MONTH,dto.getCalculationDate());
	Instant nowDate =  now.toInstant(ZoneOffset.UTC);
	Instant calDate =  cal.toInstant(ZoneOffset.UTC);
	
	System.out.println("오늘: "+nowDate);
	System.out.println("이번달 정산일: "+calDate);
	
	// 정산일이 오늘보다 이전인 경우 => 다음 달 정산일이 필요함
	if(calDate.isBefore(nowDate)) {
		
	} 
	 
	 */
	
	// 정산일마다 정산
	public void todayPayment(){
		List<TodayCalculationPartyDTO> list = tcpMap.toDtoList(tcpRepo.findTodayCalculationParty());
		for(TodayCalculationPartyDTO dto : list) {
			System.out.println(dto);
			if(dto.isPartyManager()) {
				// 매니저이면서 파티원이 1명이라도 있으면
				
			//}else {
				// 매니저가 아니면
				
				
			}
		}
		
		
	}
}
