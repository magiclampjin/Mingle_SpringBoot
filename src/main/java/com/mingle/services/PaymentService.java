package com.mingle.services;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mingle.domain.entites.Member;
import com.mingle.domain.entites.Payment;
import com.mingle.domain.repositories.MemberRepository;
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
	
	@Autowired
	private MemberRepository mRepo;
	
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
	
	// 첫달 요금
	public void firstPayment(){
		
	}
	
	// 정산일마다 정산
	@Transactional
	public void todayPayment(){
    	// 1. 정산일이 오늘인 파티 정보를 불러온다 -> o
    	// 2. 해당 파티들의 member 목록을 불러온다 -> o
    	// 3. 파티원의 경우 요금 결제 (밍글머니 우선적용) -> o
    	// 4. 파티장에게 파티원이 내야할 밍글머니 적립 -> o 
		// 파티원이 요금을 결제하지 못했을 경우는 대비하지않음. 이럴 경우, 사이트 측에서 대처해야할 일이라고 생각함.
		
		List<TodayCalculationPartyDTO> list = tcpMap.toDtoList(tcpRepo.findTodayCalculationParty());
		for(TodayCalculationPartyDTO dto : list) {
			if(dto.isPartyManager() && dto.getCurrPartyMemberCnt()>1) {
				// 매니저이면서 파티원이 1명이라도 있으면
				System.out.println("파티장: "+dto);
				// ( 파티원이 내는 금액 - 밍글 수수료1000원 - 파티원당 밍글 수수료 ) * 인원수 (이때, 파티장까지 포함된 cnt이므로 -1)
				System.out.println("매달 받을 금액: "+((dto.getMonthFee()-1000)-dto.getCommission())*(dto.getCurrPartyMemberCnt()-1));
				
				Long saveMoney = ((dto.getMonthFee()-1000)-dto.getCommission())*(dto.getCurrPartyMemberCnt()-1);
				
				// 밍글 머니에 저장
				Member m = mRepo.findById(dto.getMemberId()).get();
				m.setMingleMoney(m.getMingleMoney()+saveMoney);
				mRepo.save(m);
				
				// 결제 내역 테이블에 저장
				PaymentDTO payment = new PaymentDTO();
				payment.setPartyRegistrationId(dto.getPartyRegistrationId());
				payment.setMemberId(dto.getMemberId());
				payment.setDate(Instant.now());
				payment.setServiceId(dto.getServiceId());
				payment.setPaymentTypeId("적립");
				payment.setPrice(saveMoney);
				payment.setUsedMingleMoney(0L);
				pRepo.save(pMapper.toEntity(payment));
				
			}else {
				// 매니저가 아니면
				System.out.println("파티원: "+dto);
				System.out.println("매달 낼 금액: "+dto.getMonthFee());
				
				// 밍글머니가 있다면 밍글머니부터 차감
				// 1. 밍글머니 >= 정산액 
				Long usedMingleMoney = 0L;
				
				if(dto.getMingleMoney() >= dto.getMonthFee()) {
					Member m = mRepo.findById(dto.getMemberId()).get();
					m.setMingleMoney(m.getMingleMoney()-dto.getMonthFee());
					mRepo.save(m);
					usedMingleMoney = dto.getMonthFee();
				}
				// 2. 밍글머니 < 정산액
				else {
					Member m = mRepo.findById(dto.getMemberId()).get();
					m.setMingleMoney(0L);
					mRepo.save(m);
					usedMingleMoney = dto.getMingleMoney();
				}
				
				// 결제 내역 테이블에 저장
				PaymentDTO payment = new PaymentDTO();
				payment.setPartyRegistrationId(dto.getPartyRegistrationId());
				payment.setMemberId(dto.getMemberId());
				payment.setDate(Instant.now());
				payment.setServiceId(dto.getServiceId());
				payment.setPaymentTypeId("결제");
				payment.setPrice(dto.getMonthFee());
				payment.setUsedMingleMoney(usedMingleMoney);
				pRepo.save(pMapper.toEntity(payment));
			}
		}
	}
}
