package com.mingle.services;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mingle.dao.PartyDAO;
import com.mingle.domain.entites.Member;
import com.mingle.domain.entites.PartyMember;
import com.mingle.domain.entites.PartyRegistration;
import com.mingle.domain.entites.PartyReply;
import com.mingle.domain.repositories.CurrJoinPartyInfoRepository;
import com.mingle.domain.repositories.MemberRepository;
import com.mingle.domain.repositories.PartyInformationForMainRepository;
import com.mingle.domain.repositories.PartyInformationRepository;
import com.mingle.domain.repositories.PartyMemberRepository;
import com.mingle.domain.repositories.PartyRegistrationRepository;
import com.mingle.domain.repositories.PartyReplyRepository;
import com.mingle.domain.repositories.PaymentRepository;
import com.mingle.domain.repositories.ServiceCategoryRepository;
import com.mingle.domain.repositories.ServiceRepository;
import com.mingle.dto.CurrJoinPartyInfoDTO;
import com.mingle.dto.PartyInformationDTO;
import com.mingle.dto.PartyInformationForMainDTO;
import com.mingle.dto.PartyReplyDTO;
import com.mingle.dto.PaymentDTO;
import com.mingle.dto.ServiceCategoryDTO;
import com.mingle.dto.ServiceDTO;
import com.mingle.dto.UploadPartyReplyDTO;
import com.mingle.mappers.CurrJoinPartyInfoMapper;
import com.mingle.mappers.PartyInformationForMainMapper;
import com.mingle.mappers.PartyInformationMapper;
import com.mingle.mappers.PartyReplyMapper;
import com.mingle.mappers.PaymentMapper;
import com.mingle.mappers.ServiceCategoryMapper;
import com.mingle.mappers.ServiceMapper;

import jakarta.persistence.EntityNotFoundException;

@Service
public class PartyService {
	
	// 서비스 카테고리
	@Autowired
	private ServiceCategoryRepository scRepo;
	@Autowired
	private ServiceCategoryMapper scMap;
	
	// 서비스
	@Autowired
	private ServiceRepository sRepo;
	@Autowired
	private ServiceMapper sMap;

	// 파티 정보
	@Autowired
	private PartyInformationRepository piRepo;
	@Autowired
	private PartyInformationMapper piMap;
	@Autowired
	private PartyDAO pdao;
	
	// 파티 등록
	@Autowired
	private PartyRegistrationRepository prRepo;
	
	// 파티장 등록
	@Autowired
	private PartyMemberRepository pmRepo;
	
	// 파티 정보 메인 페이지
	@Autowired
	private PartyInformationForMainRepository pimRepo;
	@Autowired
	private PartyInformationForMainMapper pimMap;
	
	// 첫 달 결제 정보 저장을 위한 paymentRepo, mapper
	@Autowired
	private PaymentRepository payRepo;
	@Autowired
	private PaymentMapper payMap;
	
	// 밍글머니 계산을 위한 MemberRepo
	@Autowired
	private MemberRepository mRepo;
	
	// 내 파티 정보
	@Autowired
	private CurrJoinPartyInfoRepository jpRepo;
	@Autowired
	private CurrJoinPartyInfoMapper jpMap;
	
	@Autowired
	private PartyReplyRepository ptrRepo;
	@Autowired
	private PartyReplyMapper ptrMapper;
	
	// 제공하는 서비스 카테고리명 불러오기
	public List<ServiceCategoryDTO> selectCategoryAll() {
		return scMap.toDtoList(scRepo.findAll());
	}
	
	
	// 카테고리별 서비스 정보 불러오기
	public List<ServiceDTO> selectServiceByCategoryId(String id) {
		if(id.equals("전체")) {
			return sMap.toDtoList(sRepo.findAll());
		}else {
			return sMap.toDtoList(sRepo.findByServiceCategoryId(id));
		}
	}
	
	
	// 가입된 파티 서비스 목록 불러오기
	public List<Integer> selectServiceByIsJoin(String service_category_id, String member_id){
		if(service_category_id.equals("전체")) {
			return sRepo.selectByAllAndIsJoin(member_id);
		}else {
			return sRepo.selectByServiceCategoryIdAndIsJoin(service_category_id, member_id);
		}
	}
	
	// 특정 서비스 정보 불러오기
	public ServiceDTO selectServiceByServiceId(Long id) {
		ServiceDTO dto = sMap.toDto(sRepo.findById(id).get());
		return dto;
	}
	
	// 서비스별 파티 이용자수
	public List<Map<String, Object>> selectCountUserByService() {
		return pdao.selectCountUserByService();
	}

	// 파티 정보 저장
	@Transactional
	public void inertParty(PartyInformationDTO partyData, String member_id){
		// 파티 정보 저장
		long id = piRepo.save(piMap.toEntity(partyData)).getId();
		
		// 파티 등록
		PartyRegistration pre = new PartyRegistration(id);
		prRepo.save(pre);
		
		// 파티장 등록
		PartyMember pme = new PartyMember(0L, id, member_id, true);
		pmRepo.save(pme);
	}
	
	// 파티 가입 & 첫 달 결제 내역 저장 & 밍글 머니 사용
	@Transactional
	public void insertJoinParty(Long party_registration_id, String member_id, PaymentDTO paymentData){
		// 파티 가입
		PartyMember pme = new PartyMember(0L, party_registration_id, member_id, false);
		pmRepo.save(pme);
		
		// 파티 시작일이 지난 경우
		if(paymentData != null) {
			Long managerReceiveMoney = paymentData.getPartyRegistrationId();
			
			// 첫 달 결제 내역 저장
			paymentData.setPartyRegistrationId(party_registration_id);
			paymentData.setMemberId(member_id);
			paymentData.setPaymentTypeId("결제");
			payRepo.save(payMap.toEntity(paymentData));
			
			// 밍글 머니 사용했을 경우 업데이트
			if(paymentData.getUsedMingleMoney()!=0) {
				Member m = mRepo.findById(member_id).get();
				m.setMingleMoney(m.getMingleMoney()-paymentData.getUsedMingleMoney());
				mRepo.save(m);
			}
			
			// 파티장에게 밍글 머니에 바로 적립
			Member manager = mRepo.findById(pmRepo.selectMemberIdBypartyRegistrationIdAndIsPartyManagerTrue(party_registration_id)).get();
			// 가격 ( 클라이언트 -> 서버로 데이터 보낼 때 party_registration_id 속성을 pathVariable에 담아 보냄에 따라, 
			// long형인 party_registration_id 속성이 비어있는 채로 객체 전송 -> 이 자리에 다른 long형 데이터를 담아 보내서 꺼내쓰고, 
			// 실제 party_registration_id 값은 pathVariable 로 받아 setter로 주입함.
			manager.setMingleMoney(manager.getMingleMoney()+managerReceiveMoney);
			mRepo.save(manager);
		}
	}
	
	// 등록된 파티 정보 불러오기
	public List<PartyInformationDTO> selectPartyList(Long id){
		return piMap.toDtoList(piRepo.findPartyInformationByServiceIdAndCount(id));
	}
	
	// 등록된 파티 정보 중 선택한 날짜에 해당하는 파티 정보 불러오기
	public List<PartyInformationDTO> selectPartyListByStartDate(Long id, Instant start, Instant end){
		return piMap.toDtoList(piRepo.findPartyInformationByServiceIdAndCountAndStartDate(id, start, end));
	}
	
	// 서비스 명 리스트 불러오기
	public List<ServiceDTO> getServiceNameList(){
		return sMap.toDtoList(sRepo.findAll());
	}
	
	// 사용자가 이미 가입된 파티가 있는지 확인
	public boolean isMemberParty(String userId) {	
		return pmRepo.isAlreadyMember(userId);
	}
	

	// 입력한 아이디가 중복된 아이디인지 확인
	public boolean isIdDupChk(Long serviceId, String loginId) {
		return piRepo.isIdDupChk(serviceId, loginId);
	}
	
	// 가입한 파티 목록 불러오기
	public List<CurrJoinPartyInfoDTO> selectMyPartyList(String loginId){
		List<CurrJoinPartyInfoDTO> list = jpRepo.selectMyPartyList(loginId);
		return list;
	}
	
	// 가입한 파티 목록 불러오기 (나의 파티용, 종료된 파티 포함)
	public List<CurrJoinPartyInfoDTO> selectMyAllPartyList(String loginId){
		List<CurrJoinPartyInfoDTO> list = jpRepo.selectMyAllPartyList(loginId);
		return list;
	}
	
	// 특정 파티 정보 불러오기
	public CurrJoinPartyInfoDTO selectMyPartyInfo(Long id, String memberId){
		CurrJoinPartyInfoDTO info = jpMap.toDto(jpRepo.selectMyPartyInfo(id, memberId));
		
		// 아직 파티 시작 전이면 아이디, 비밀번호 정보 비활성화
		// 현재 날짜와 시간을 얻기
        LocalDateTime midnight = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0).withNano(0);
        Instant instant = midnight.toInstant(ZoneOffset.UTC);
        // 파티 시작 전이면
        if (info.getStartDate().toInstant().isAfter(instant)) {
        	info.setLoginId(null);
        	info.setLoginPw(null);
        }
        return info;
	}

	// 메인페이지에 출력할 파티 정보 불러오기
	public List<PartyInformationForMainDTO> selectPartyListForMain(){	
		return pimMap.toDtoList(pimRepo.findPartyInfoForMain());
	}
	
	// 메인페이지 모집중인 파티 개수
	public int selectAllPartyCountForMain() {
		return piRepo.selectAllParty().size();
	}
	
	
	// 파티 댓글 작성
	@Transactional
	public PartyReplyDTO insertPartyReply(UploadPartyReplyDTO dto) {
		
		PartyReply partyReply = new PartyReply();
		partyReply.setContent(dto.getContent());
		partyReply.setPartyRegistrationId(dto.getPartyRegistrationId());
		partyReply.setMember(mRepo.selectMypageInfo(dto.getMemberId()));
		if(dto.getPartyReplyParentId()>0) {
			partyReply.setParentPartyReply(ptrRepo.findPartyReplyById(dto.getPartyReplyParentId()));
		}
		if(dto.getPartyReplyAdoptiveParentId()>0) {
			partyReply.setPartyReplyAdoptiveParentId(dto.getPartyReplyAdoptiveParentId());
		}
		partyReply.setWriteDate(Timestamp.from(dto.getWriteDate()));
		
		partyReply.setIsSecret(dto.getIsSecret());
		
		return ptrMapper.toDto(ptrRepo.save(partyReply));
	}
	
	// 파티아이디에 따른 파티댓글 출력
	public Set<PartyReplyDTO> selectPartyReplyById(Long partyRegistrationId){
		return ptrMapper.toDtoSet(ptrRepo.findFirstPartyReply(partyRegistrationId));
	}
	
	// 파티 댓글 수정
	@Transactional
	public PartyReplyDTO updatePartyReplyById(Long id, String content, Boolean isSecret) {
		PartyReply partyReply = ptrRepo.findById(id).orElseThrow(() -> new EntityNotFoundException());
		
		partyReply.setContent(content);
		partyReply.setIsSecret(isSecret);
		return ptrMapper.toDto(ptrRepo.save(partyReply));
	}
	
	
	// 파티 댓글 삭제
	public void deletePartyReplyById(Long id) {
		PartyReply partyReply = ptrRepo.findById(id).get();
		ptrRepo.delete(partyReply);
	}
	
	
	// 파티 삭제하기
	public int deleteById(Long id) {
		int memberCnt = pmRepo.selectCntById(id);
		if(memberCnt==1){
			piRepo.delete(piRepo.findById(id).get());
			return 1;
		}else {
			return 0;
		}
	}
}
