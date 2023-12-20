package com.mingle.services;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.mingle.dao.PostDAO;
import com.mingle.domain.entites.Member;
import com.mingle.domain.entites.Post;
import com.mingle.domain.entites.PostFile;
import com.mingle.domain.repositories.FreePostViewRepository;
import com.mingle.domain.repositories.MemberRepository;
import com.mingle.domain.repositories.NoticePostViewRepository;
import com.mingle.domain.repositories.PopularPostViewRepository;
import com.mingle.domain.repositories.PostFileRepository;
import com.mingle.domain.repositories.PostRepository;
import com.mingle.dto.PostDTO;
import com.mingle.dto.PostFileDTO;
import com.mingle.dto.PostViewDTO;
import com.mingle.dto.UploadPostDTO;
import com.mingle.mappers.FreePostViewMapper;
import com.mingle.mappers.MemberMapper;
import com.mingle.mappers.NoticePostViewMapper;
import com.mingle.mappers.PopularPostViewMapper;
import com.mingle.mappers.PostFileMapper;
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
	
	@Autowired
	private FreePostViewRepository fpvRepo;
	
	@Autowired
	private FreePostViewMapper fpvMapper;
	
	@Autowired
	private NoticePostViewRepository npvRepo;
	
	@Autowired
	private NoticePostViewMapper npvMapper;
	
	@Autowired
	private PopularPostViewRepository ppvRepo;
	
	@Autowired
	private PopularPostViewMapper ppvMapper;
	
	@Autowired
	private PostFileRepository pfRepo;
	
	@Autowired
	private PostFileMapper pfMapper;
	
	@Autowired
	private MemberRepository mRepo;
	
	@Autowired
	private MemberMapper mMapper;
	
//---------------------------------------------------------------------------------	
	// 공지 게시판 글 불러오기
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
	
//---------------------------------------------------------------------------------
	
	
	public List<PostDTO> selectAll(){
		List<Post> plist = pRepo.findAll();
		return pMapper.toDtoList(plist);
	}
	
	// 공지 게시판 최신 글 10개 불러오기 (JPA)
	public List<PostViewDTO> selectByNoticeTop10(){
		Pageable topten = PageRequest.of(0, 10);
		return npvMapper.toDtoList(npvRepo.findTop10ByOrderByRownumDescNoticePostView(topten));
	}
	
	// 공지 게시판 최신 순으로 전부 가져오기(JPA)
	public List<PostViewDTO> selectByNotice(){
		return npvMapper.toDtoList(npvRepo.findAllNoticePostView());
	}
	
	// 자유 게시판 최신 글 10개 불러오기 (JPA)
	public List<PostViewDTO> selectByFreeTop10(){
		Pageable topten = PageRequest.of(0, 10);
		return fpvMapper.toDtoList(fpvRepo.findTop10ByOrderByRownumDescFreePostView(topten));
	}
	
	// 자유 게시판 최신 순으로 전부 가져오기(JPA)
	public List<PostViewDTO> selectByFree(){
		return fpvMapper.toDtoList(fpvRepo.findAllFreePostView());
	}
	
	// 인기글 최신 글 10개 불러오기 (JPA)
	public List<PostViewDTO> selectByPopularTop10(){
		Pageable topten = PageRequest.of(0, 10);
		return ppvMapper.toDtoList(ppvRepo.findTop10ByOrderByRownumDescPopularPostView(topten));
	}
	
	// 인기글 최신 순으로 전부 가져오기(JPA)
	public List<PostViewDTO> selectByPopular(){
		return ppvMapper.toDtoList(ppvRepo.findAllFreePopulartView());
	}
	

	// 게시글 id에 따른 게시글 정보 출력
	public PostDTO findPostById(Long id) {
		return pMapper.toDto(pRepo.findPostById(id));
	}
	
	// 게시글 등록
	@Transactional
	public void insert(UploadPostDTO dto) throws IllegalStateException, IOException {
		Member member = mRepo.selectMypageInfo(dto.getMemberId());
		System.out.println(dto.getMemberId()+" 접속 아이디");
		Post post = new Post();
		post.setMember(member);
		System.out.println(member + "0000000000000");
		post.setTitle(dto.getTitle());
		post.setContent(dto.getContent());
		post.setWriteDate(Timestamp.from(dto.getWriteDate()));
		post.setIsFix(dto.getIsFix());
		post.setIsNotice(dto.getIsNotice());
		post.setReviewGrade(dto.getReviewGrade());
		post.setViewCount(dto.getViewCount());
		
		post.setFiles(new HashSet<>());
		
		Long parentSeq = pRepo.save(post).getId();
		
		Set<PostFile> entityFiles = post.getFiles();
		List<MultipartFile> multiList = dto.getFiles();
		
		if(multiList != null && !multiList.isEmpty())  {
			String upload = "C:/Mingle/uploads/";
			File uploadPath = new File(upload);
			if(!uploadPath.exists()) {
				uploadPath.mkdirs();
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
	
	//텍스트 에디터로부터 파일을 받아 업로드 한 뒤 url 반환
	public String imageUploadFromTextEditor(MultipartFile image) throws IllegalStateException, IOException {
		String upload = "C:/Mingle/uploads/";
		File uploadPath = new File(upload);
		if(!uploadPath.exists()) {
			uploadPath.mkdirs();
		}
		String oriName = image.getOriginalFilename();
		String sysName = UUID.randomUUID() + oriName;
		
		image.transferTo(new File(uploadPath,sysName));
		
		pfRepo.save(pfMapper.toEntity(new PostFileDTO(null,oriName,sysName,0L)));
		
		return "/uploads/" + sysName;
	}
	
	
	
	// 게시글 정보 업데이트
	public void updateById(Long id, PostDTO dto) {
		Post post = pRepo.findById(id).get();
		pMapper.updateEntityFromDTO(dto,post);
		pRepo.save(post);
	}
	
	// 게시글 삭제
	public void deleteById(Long id) {
		Post post = pRepo.findById(id).get();
		pRepo.delete(post);
	}
	
	// 모든 공지 게시글 가져오기
	// 공지 게시글 불러오기 ( 관리자 대시보드 )
	public List<PostDTO> selectNoticePosts() {
		List<Post> plist = pRepo.findAllByNoticePosts();
		return pMapper.toDtoList(plist);
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
