package com.mingle.services;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.mingle.dao.PostDAO;
import com.mingle.domain.entites.Post;
import com.mingle.domain.entites.PostFile;
import com.mingle.domain.entites.Report;
import com.mingle.domain.repositories.PostRepository;
import com.mingle.dto.PostDTO;
import com.mingle.dto.UploadPostDTO;
import com.mingle.mappers.PostMapper;

import jakarta.transaction.Transactional;

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
	
	// 공지 게시판 글 불러오기 (전체)
	public List<Map<String,Object>> selectByNoticeTrue(){
		return pdao.selectByNoticeTrue();
	}
	
	// 공지 게시판 글 불러오기 (top 10)
	public List<Map<String,Object>> selectByNoticeTrueTop10(){
		return pdao.selectByNoticeTrueTop10();
	}
	
	// 공지 게시글 불러오기 ( 관리자 대시보드 )
	public List<PostDTO> selectNoticePosts(){
		List<Post> plist = pRepo.findAllByNoticePosts();
		return pMapper.toDtoList(plist);
	}
	
	public List<Map<String,Object>> selectByNoticeFalse(){
		return pdao.selectByNoticeFalse();
	}
	
	public List<Map<String,Object>> selectByNoticeFalseTop10(){
		return pdao.selectByNoticeFalseTop10();
	}
	
	// 게시글 id에 따른 게시글 정보 출력
	public PostDTO findPostById(Long id) {
		return pMapper.toDto(pRepo.findPostById(id));
	}
	
	// 게시글 등록
	@Transactional
	public void insert(UploadPostDTO dto) throws IllegalStateException, IOException {
		Post post = pMapper.toEntity(dto);
		
		post.setViewCount(0L);
		post.setFiles(new HashSet<>());
		
		Long parentSeq = pRepo.save(post).getId();
		
		Set<PostFile> entityFiles = post.getFiles();
		List<MultipartFile> multiList = dto.getFiles();
		
		if(multiList.size() != 0) {
			String upload = "/uploads/";
			File uploadPath = new File(upload);
			if(!uploadPath.exists()) {
				uploadPath.mkdir();
			}
			for(MultipartFile f : multiList) {
				String oriName = f.getOriginalFilename();
				String sysName = UUID.randomUUID()+"_"+oriName;
				
				f.transferTo(new File(uploadPath,sysName));
				
				entityFiles.add(new PostFile(null,oriName,sysName,parentSeq));
			}
		}
		
		pRepo.save(post);
	}
	
	// 게시글 정보 업데이트
	public void updateById(Long id, PostDTO dto) {
		Post post = pRepo.findById(id).get();
		pMapper.updateEntityFromDTO(dto,post);
		pRepo.save(post);
	}
	
	// 게시글 삭제
	public void deleteById(Long id, PostDTO dto) {
		Post post = pRepo.findById(id).get();
		pRepo.delete(post);
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
