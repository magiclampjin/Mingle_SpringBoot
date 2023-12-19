package com.mingle.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mingle.dao.NewVideoDAO;
import com.mingle.domain.entites.NewVideo;
import com.mingle.domain.repositories.NewVideoRepository;
import com.mingle.dto.NewVideoDTO;
import com.mingle.mappers.NewVideoMapper;

@Service
public class NewVideoService {
	
	
	@Autowired
	private NewVideoDAO nvdao;
	
	@Autowired
	private NewVideoRepository nvRepo;
	
	@Autowired
	private NewVideoMapper nvMapper;
	
	public List<NewVideoDTO> selectLikestVideosDuringLatestOneMonth(){
		return nvdao.selectLikestVideosDuringLatestOneMonth();
	}
	
	public List<NewVideoDTO> selectAll(){
		return nvMapper.toDtoList(nvRepo.findAll()); 
	}

}
