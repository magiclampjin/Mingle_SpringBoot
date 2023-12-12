package com.mingle.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mingle.services.PostService;

@RestController
@RequestMapping("/api/post")
public class PostController {
	
	@Autowired
	private PostService pServ;
	
	@GetMapping("/freeTop10")
	public ResponseEntity<List<Map<String,Object>>> getLastestFreePosts(){
		return ResponseEntity.ok(pServ.selectByNoticeFalseTop10());
	}
	
	@GetMapping("/noticeTop10")
	public ResponseEntity<List<Map<String,Object>>> getLastestNoticePosts(){
		return ResponseEntity.ok(pServ.selectByNoticeTrueTop10());
	}
	
	@GetMapping("/free")
	public ResponseEntity<List<Map<String,Object>>> getFreePosts(){
		return ResponseEntity.ok(pServ.selectByNoticeFalse());
	}
	
	@GetMapping("/notice")
	public ResponseEntity<List<Map<String,Object>>> getNoticePosts(){
		return ResponseEntity.ok(pServ.selectByNoticeTrue());
	}

}
