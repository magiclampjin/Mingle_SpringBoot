package com.mingle.services;

import java.time.Instant;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mingle.dao.PartyDAO;
import com.mingle.domain.entites.PartyMember;
import com.mingle.domain.entites.PartyRegistration;
import com.mingle.domain.repositories.PartyInformationRepository;
import com.mingle.domain.repositories.PartyMemberRepository;
import com.mingle.domain.repositories.PartyRegistrationRepository;
import com.mingle.domain.repositories.ServiceCategoryRepository;
import com.mingle.domain.repositories.ServiceRepository;
import com.mingle.dto.PartyInformationDTO;
import com.mingle.dto.ServiceCategoryDTO;
import com.mingle.dto.ServiceDTO;
import com.mingle.mappers.PartyInformationMapper;
import com.mingle.mappers.ServiceCategoryMapper;
import com.mingle.mappers.ServiceMapper;

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
	
	// 등록된 파티 정보 불러오기
	public List<PartyInformationDTO> selectPartyList(Long id){
		return piMap.toDtoList(piRepo.findPartyInformationByServiceIdAndCount(id));
	}
	
	// 등록된 파티 정보 중 선택한 날짜에 해당하는 파티 정보 불러오기
	public List<PartyInformationDTO> selectPartyListByStartDate(Long id, Instant start, Instant end){
		return piMap.toDtoList(piRepo.findPartyInformationByServiceIdAndCountAndStartDate(id, start, end));
	}
}
