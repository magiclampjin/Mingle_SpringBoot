package com.mingle.services;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mingle.dao.PartyDAO;
import com.mingle.domain.repositories.ServiceCategoryRepository;
import com.mingle.domain.repositories.ServiceRepository;
import com.mingle.dto.ServiceCategoryDTO;
import com.mingle.dto.ServiceDTO;
import com.mingle.mappers.ServiceCategoryMapper;
import com.mingle.mappers.ServiceMapper;

@Service
public class PartyService {
	
	@Autowired
	private ServiceCategoryRepository scRepo;
	@Autowired
	private ServiceRepository sRepo;
	
	@Autowired
	private ServiceCategoryMapper scMap;
	@Autowired
	private ServiceMapper sMap;
	
	@Autowired
	private PartyDAO pdao;
	
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
		System.out.println(dto);
		return dto;
	}
	
	// 서비스별 파티 이용자수
	public List<Map<String, Object>> selectCountUserByService() {
		return pdao.selectCountUserByService();
	}
}
