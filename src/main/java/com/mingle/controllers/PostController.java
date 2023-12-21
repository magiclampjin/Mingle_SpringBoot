package com.mingle.controllers;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

	@GetMapping("/freeTop10")
	public ResponseEntity<List<PostViewDTO>> getLastestFreePosts() {
		return ResponseEntity.ok(pServ.selectByFreeTop10());
	}
	
	@GetMapping("/free")
	public ResponseEntity<List<PostViewDTO>> getFreePosts() {
		return ResponseEntity.ok(pServ.selectByFree());
	}

	@GetMapping("/noticeTop10")
	public ResponseEntity<List<PostViewDTO>> getLastestNoticePosts() {
		return ResponseEntity.ok(pServ.selectByNoticeTop10());
	}

	@GetMapping("/notice")
	public ResponseEntity<List<PostViewDTO>> getNoticePosts() {
		return ResponseEntity.ok(pServ.selectByNotice());
	}
	
	@GetMapping("/popularTop10")
	public ResponseEntity<List<PostViewDTO>> getLatestPopularPosts(){
		return ResponseEntity.ok(pServ.selectByPopularTop10());
	}
	
	@GetMapping("/popular")
	public ResponseEntity<List<PostViewDTO>> getPopularPosts(){
		return ResponseEntity.ok(pServ.selectByPopular());
	}

	@GetMapping("/{id}")
	public ResponseEntity<PostDTO> getPostInfo(@PathVariable Long id) {
		return ResponseEntity.ok(pServ.findPostById(id));
	}
	
	@PostMapping("/imageUpload")
	public ResponseEntity<String> uploadImage(@RequestParam("image") MultipartFile image) throws IllegalStateException, IOException{
		return ResponseEntity.ok(pServ.imageUploadFromTextEditor(image));
	}

	@PostMapping
	public ResponseEntity<Void> postInsert(@ModelAttribute UploadPostDTO dto) throws IllegalStateException, IOException {
		System.out.println(dto);
		pServ.insert(dto);
		return ResponseEntity.ok().build();
	}

	@PutMapping
	public ResponseEntity<Void> postUpdate(@RequestBody Long id, @RequestBody PostDTO dto) {
		pServ.updateById(id, dto);
		return ResponseEntity.ok().build();
	}

	@DeleteMapping
	public ResponseEntity<Void> postDelete(@RequestBody Long id) {
		pServ.deleteById(id);
		return ResponseEntity.ok().build();
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<String> handleException(Exception e, WebRequest request) {
		e.printStackTrace();
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + e.getMessage());
	}

}
