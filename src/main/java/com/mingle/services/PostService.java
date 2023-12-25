package com.mingle.services;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.mingle.dao.PostDAO;
import com.mingle.domain.entites.Member;
import com.mingle.domain.entites.Post;
import com.mingle.domain.entites.PostFile;
import com.mingle.domain.entites.PostReactions;
import com.mingle.domain.repositories.FreePostViewRepository;
import com.mingle.domain.repositories.MemberRepository;
import com.mingle.domain.repositories.NoticePostViewRepository;
import com.mingle.domain.repositories.PopularPostViewRepository;
import com.mingle.domain.repositories.PostFileRepository;
import com.mingle.domain.repositories.PostReactionsRepository;
import com.mingle.domain.repositories.PostRepository;
import com.mingle.dto.PostDTO;
import com.mingle.dto.PostFileDTO;
import com.mingle.dto.PostViewDTO;
import com.mingle.dto.UploadPostDTO;
import com.mingle.mappers.FreePostViewMapper;
import com.mingle.mappers.NoticePostViewMapper;
import com.mingle.mappers.PopularPostViewMapper;
import com.mingle.mappers.PostFileMapper;
import com.mingle.mappers.PostMapper;

import jakarta.persistence.EntityNotFoundException;
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
	private PostReactionsRepository prRepo;
    
	
	
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
	public Long insert(UploadPostDTO dto) throws IllegalStateException, IOException {
		Member member = mRepo.selectMypageInfo(dto.getMemberId());
		Post post = new Post();
		post.setMember(member);
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
			String upload = this.getRealPath();
			File uploadPath = new File(upload);
			if(!uploadPath.exists()) {
				uploadPath.mkdirs();
			}
			for(MultipartFile f : multiList) {
				String oriName = f.getOriginalFilename();
				String sysName = UUID.randomUUID()+"_"+oriName;
				
				f.transferTo(new File(uploadPath,sysName));
				pfRepo.save(pfMapper.toEntity(new PostFileDTO(null,oriName,sysName,parentSeq)));
				
				entityFiles.add(new PostFile(null,oriName,sysName,parentSeq));
			}
		}
		
		return pRepo.save(post).getId();
	}
	
	//텍스트 에디터로부터 파일을 받아 업로드 한 뒤 url 반환
	public String imageUploadFromTextEditor(MultipartFile image) throws IllegalStateException, IOException {
		String realPath = this.getRealPath();
		File uploadPath = new File(realPath);
		if(!uploadPath.exists()) {
			uploadPath.mkdirs();
		}
		String oriName = image.getOriginalFilename();
		String sysName = UUID.randomUUID() + oriName;
		
		image.transferTo(new File(uploadPath,sysName));
		
		pfRepo.save(pfMapper.toEntity(new PostFileDTO(null,oriName,sysName,0L)));
		
		return "/uploads/" + sysName;
	}
	
	// 게시글 업데이트
	@Transactional
	public void updatePost(Long postId, UploadPostDTO dto) throws IllegalStateException, IOException {
	    Post post = pRepo.findById(postId)
	                    .orElseThrow(() -> new EntityNotFoundException("Post not found"));

	    // Post 정보 업데이트
	    post.setTitle(dto.getTitle());
	    post.setContent(dto.getContent());
	    // 기타 필요한 필드 업데이트

	    // 기존 파일 목록 가져오기
	    Set<String> existingOriNames = post.getFiles().stream()
	                                        .map(PostFile::getOriName)
	                                        .collect(Collectors.toSet());

	    List<MultipartFile> newFiles = dto.getFiles();
	    if (newFiles != null && !newFiles.isEmpty()) {
	        String uploadDir = this.getRealPath();
	        File uploadPath = new File(uploadDir);
	        if (!uploadPath.exists()) {
	            uploadPath.mkdirs();
	        }

	        for (MultipartFile file : newFiles) {
	            String oriName = file.getOriginalFilename();
	            String sysName = UUID.randomUUID() + "_" + oriName;

	            // 중복 파일 체크
	            if (!existingOriNames.contains(oriName)) {
	                file.transferTo(new File(uploadPath, oriName));
	                PostFile newFile = new PostFile(null, oriName, sysName, post.getId());
	                post.getFiles().add(newFile);
	            }
	        }
	    }

	    // 변경된 Post 객체 저장
	    pRepo.save(post);
	}

	
	// 게시글 접속 시 게시글 수 증가.
	@Transactional
	public Long incrementViewCount(Long id) {
		pRepo.incrementViewCount(id);
		return pRepo.selectViewcountByPostId(id);
	}
	
	// 게시글의 좋아요 수 반환
	public Long sumVotesByPostId(Long id) {
	    Long voteCount = prRepo.sumVotesByPostId(id);
	    return (voteCount != null) ? voteCount : 0; // null인 경우 0으로 반환
	}
	
	// 게시글 좋아요 누를 시 투표 이력이 있는 지 검사 후, 없다면 좋아요 투표 진행.
	@Transactional
	public Long likeVote(Long postId, String memberId) {
	    if (prRepo.isVoted(postId, memberId)) {
	        return null; // 이미 투표한 경우
	    } else {
	        PostReactions postReaction = PostReactions.builder()
	                .postId(postId)
	                .memberId(memberId)
	                .vote(1L) // 좋아요는 '1'로 설정
	                .build();

	        prRepo.save(postReaction);
	        
	        return prRepo.sumVotesByPostId(postId); // 총 좋아요 수 반환
	    }
	}

	
	// 게시글 싫어요 누를 시 투표 이력이 있는 지 검사 후, 없다면 좋아요 투표 진행.
	@Transactional
	public Long dislikeVote(Long postId, String memberId) {
	    if (prRepo.isVoted(postId, memberId)) {
	        return null;
	    } else {
	        PostReactions postReaction = PostReactions.builder()
	                .postId(postId)
	                .memberId(memberId)
	                .vote(-1L) // 싫어요는 '-1'로 설정
	                .build();

	        prRepo.save(postReaction);
	        
	        return prRepo.sumVotesByPostId(postId);
	    }
	}
	
	
	// 게시글 삭제
	@Transactional
	public void deleteById(Long id) throws IOException {
		List<String> fileList = pfRepo.selectSysNameListByPostId(id);
		this.deleteServerFileList(fileList);
		pfRepo.deleteByPostId(id); // 게시글 삭제 시 관련 파일정보를 DB에서 삭제.
		Post post = pRepo.findById(id).get();
		pRepo.delete(post);
	}
	
	// 특정 파일 삭제
	public void deleteFileBySysName(String sysName) throws IOException {
		this.deleteServerFile(sysName);
		pfRepo.deleteFileBySysName(sysName);
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
	
	// 서버 파일 리스트 삭제 함수
	protected void deleteServerFileList(List<String> fileList) throws IOException{
		String realPath = this.getRealPath();
		File uploadPath = new File(realPath);
		if(!uploadPath.exists()) {uploadPath.mkdirs();}

		if(fileList != null) {
			for(String delFile : fileList) {
				if(delFile != null) {
					Path path = Paths.get(uploadPath + "/" + delFile);
					java.nio.file.Files.deleteIfExists(path);
				}
			}
		}
	}
	
	// 서버 파일 삭제 함수
	protected void deleteServerFile(String sysName) throws IOException {
		String realPath = this.getRealPath();
		File uploadPath = new File(realPath);
		if(!uploadPath.exists()) {uploadPath.mkdirs();}
		
		if(sysName != null) {
			Path path = Paths.get(uploadPath + "/" + sysName);
			java.nio.file.Files.deleteIfExists(path);
		}
		
	}
	
	public String getRealPath() {
		return "C:/Mingle/uploads/";
	}
	
	

	

}
