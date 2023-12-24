package com.mingle.controllers;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartFile;

import com.mingle.dto.PostDTO;
import com.mingle.dto.PostViewDTO;
import com.mingle.dto.UploadPostDTO;
import com.mingle.services.PostService;

@RestController
@RequestMapping("/api/post")
public class PostController {

	@Autowired
	private PostService pServ;

//	@GetMapping("/freeTop10")
//	public ResponseEntity<List<Map<String,Object>>> getLastestFreePosts(){
//		return ResponseEntity.ok(pServ.selectByNoticeFalseTop10());
//	}
//	@GetMapping("/noticeTop10")
//	public ResponseEntity<List<Map<String,Object>>> getLastestNoticePosts(){
//		return ResponseEntity.ok(pServ.selectByNoticeTrueTop10());
//	}

	// 최신 자유게시판 글 목록 출력(10개)
	@GetMapping("/freeTop10")
	public ResponseEntity<List<PostViewDTO>> getLastestFreePosts() {
		return ResponseEntity.ok(pServ.selectByFreeTop10());
	}

	// 자유게시판 글 목록 출력
	@GetMapping("/free")
	public ResponseEntity<List<PostViewDTO>> getFreePosts() {
		return ResponseEntity.ok(pServ.selectByFree());
	}

	// 최신 공지게시판 글 목록 출력(10개)
	@GetMapping("/noticeTop10")
	public ResponseEntity<List<PostViewDTO>> getLastestNoticePosts() {
		return ResponseEntity.ok(pServ.selectByNoticeTop10());
	}

	// 공지게시판 글 목록 출력
	@GetMapping("/notice")
	public ResponseEntity<List<PostViewDTO>> getNoticePosts() {
		return ResponseEntity.ok(pServ.selectByNotice());
	}

	// 최신 인기글 목록 출력 (10개)
	@GetMapping("/popularTop10")
	public ResponseEntity<List<PostViewDTO>> getLatestPopularPosts() {
		return ResponseEntity.ok(pServ.selectByPopularTop10());
	}

	// 인기글 목록 출력
	@GetMapping("/popular")
	public ResponseEntity<List<PostViewDTO>> getPopularPosts() {
		return ResponseEntity.ok(pServ.selectByPopular());
	}

	// 게시글 정보 출력
	@GetMapping("/{id}")
	public ResponseEntity<PostDTO> getPostInfo(@PathVariable Long id) {
		return ResponseEntity.ok(pServ.findPostById(id));
	}

	// 추천 수 출력
	@GetMapping("/likes/{id}")
	public ResponseEntity<Long> getVotesByPostId(@PathVariable Long id) {
		return ResponseEntity.ok(pServ.sumVotesByPostId(id));
	}

	// 파일 다운로드
	@GetMapping("/file/download")
	public ResponseEntity<Object> downloadFile(@RequestParam String sysName, @RequestParam String oriName)
			throws Exception {
		String realPath = pServ.getRealPath() + sysName;

		try {
			Path filePath = Paths.get(realPath);
			Resource resource = new InputStreamResource(Files.newInputStream(filePath));

			File file = new File(realPath);

			HttpHeaders headers = new HttpHeaders();
			headers.setContentDisposition(ContentDisposition.builder("attachment")
					.filename(URLEncoder.encode(file.getName(), StandardCharsets.UTF_8)).build());

			return new ResponseEntity<Object>(resource, headers, HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<Object>(null, HttpStatus.CONFLICT);
		}
	}

	// 이미지 업로드
	@PostMapping("/imageUpload")
	public ResponseEntity<String> uploadImage(@RequestParam("image") MultipartFile image)
			throws IllegalStateException, IOException {
		return ResponseEntity.ok(pServ.imageUploadFromTextEditor(image));
	}

	// 게시글 삽입
	@PostMapping
	public ResponseEntity<Void> postInsert(@ModelAttribute UploadPostDTO dto)
			throws IllegalStateException, IOException {
		System.out.println(dto);
		pServ.insert(dto);
		return ResponseEntity.ok().build();
	}

	// 추천 요청
	@PostMapping("/{postId}/like")
	public ResponseEntity<Long> likePost(@PathVariable Long postId, @RequestParam("memberId") String memberId) {
		Long likeCount = pServ.likeVote(postId, memberId);
		if (likeCount == null) {
			return ResponseEntity.badRequest().body(-1L); // 이미 투표한 경우
		}
		return ResponseEntity.ok(likeCount); // 총 추천 수 반환
	}

	// 비추천 요청
	@PostMapping("/{postId}/dislike")
	public ResponseEntity<Long> dislikePost(@PathVariable Long postId, @RequestParam("memberId") String memberId) {
		Long likeCount = pServ.dislikeVote(postId, memberId);
		if (likeCount == null) {
			return ResponseEntity.badRequest().body(-1L); // 이미 투표한 경우
		}
		return ResponseEntity.ok(likeCount); // 총 추천 수 반환
	}

	// 업데이트 로직(변경 예정)
	@PutMapping
	public ResponseEntity<Void> postUpdate(@RequestBody Long id, @RequestBody PostDTO dto) {
		pServ.updateById(id, dto);
		return ResponseEntity.ok().build();
	}

	// 게시글 입장 시 조회 수 업데이트
	@PutMapping("/updateViewCount/{id}")
	public ResponseEntity<Long> incrementViewCount(@PathVariable Long id) {
		return ResponseEntity.ok(pServ.incrementViewCount(id));
	}

	// 게시글 삭제 요청
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> postDelete(@PathVariable Long id) throws IOException {
		pServ.deleteById(id);
		return ResponseEntity.ok().build();
	}
	
	// 특정 sysName을 가진 파일 삭제 
	@DeleteMapping("/file/{sysName}")
	public ResponseEntity<Void> postFileDelete(@PathVariable String sysName) throws IOException{
		pServ.deleteFileBySysName(sysName);
		return ResponseEntity.ok().build();
	}

	// 에러 핸들러
	@ExceptionHandler(Exception.class)
	public ResponseEntity<String> handleException(Exception e, WebRequest request) {
		e.printStackTrace();
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("에러 발생: " + e.getMessage());
	}

}
