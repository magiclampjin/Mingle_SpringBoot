package com.mingle.services;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mingle.dao.PostDAO;

@Service
public class PostService {
	
	@Autowired
	private PostDAO pdao;
	
	public List<Map<String,Object>> selectByNoticeTrue(){
		return pdao.selectByNoticeTrue();
	}
	
	public List<Map<String,Object>> selectByNoticeTrueTop10(){
		return pdao.selectByNoticeTrueTop10();
	}
	
	public List<Map<String,Object>> selectByNoticeFalse(){
		return pdao.selectByNoticeFalse();
	}
	
	public List<Map<String,Object>> selectByNoticeFalseTop10(){
		return pdao.selectByNoticeFalseTop10();
	}

}
