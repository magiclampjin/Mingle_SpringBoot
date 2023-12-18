package com.mingle.services;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mingle.dao.PostDAO;
import com.mingle.domain.entites.Post;
import com.mingle.domain.entites.Report;
import com.mingle.domain.repositories.PostRepository;
import com.mingle.dto.PostDTO;
import com.mingle.mappers.PostMapper;

@Service
public class PostService {
	
	@Autowired
	private PostDAO pdao;
	
	@Autowired
	private PostRepository pRepo;
	
	@Autowired
	private PostMapper pMapper;
	
	public List<PostDTO> selectAll(){
		List<Post> plist = pRepo.findAll();
		return pMapper.toDtoList(plist);
	}
	
	public List<PostDTO> selectFreePosts(){
		List<Post> plist = pRepo.findAllByFreePosts();
		return pMapper.toDtoList(plist);
	}
	
	public List<PostDTO> selectNoticePosts(){
		List<Post> plist = pRepo.findAllByNoticePosts();
		return pMapper.toDtoList(plist);
	}
	
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
	
	// 고정 중인 공지글 리스트
	public List<PostDTO> selectByFixedNotice() {
		List<Post> plist = pRepo.selectByFixedNotice();
		return pMapper.toDtoList(plist);
	}
	
	// 고정 중이 아닌 공지글 리스트
	public List<PostDTO> selectByUnfixedNotice() {
		List<Post> plist = pRepo.selectByUnFixedNotice();
		return pMapper.toDtoList(plist);
	}
	
	// 공지글 고정
	public void updateNoticeFixTrue(Long id) {
		Post post = pRepo.findAllById(id);
		post.setIsFix(true);
		pRepo.save(post);
	}
	
	// 공지글 고정 해제
	public void updateNoticeFixFalse(Long id) {
		Post post = pRepo.findAllById(id);
		post.setIsFix(false);
		pRepo.save(post);
	}

}
