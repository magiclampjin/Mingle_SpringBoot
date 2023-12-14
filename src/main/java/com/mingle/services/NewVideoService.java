package com.mingle.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mingle.dao.NewVideoDAO;
import com.mingle.dto.NewVideoDTO;

@Service
public class NewVideoService {
	
	
	@Autowired
	private NewVideoDAO nvdao;
	
	public List<NewVideoDTO> selectLikestVideosDuringLatestOneMonth(){
		return nvdao.selectLikestVideosDuringLatestOneMonth();
	}

}
