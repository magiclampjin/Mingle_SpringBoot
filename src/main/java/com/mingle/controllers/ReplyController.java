package com.mingle.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mingle.dto.ReplyDTO;
import com.mingle.dto.ReportDTO;
import com.mingle.dto.UploadReplyDTO;
import com.mingle.services.ReplyService;
import com.mingle.services.ReportService;

@RestController
@RequestMapping("/api/reply")
public class ReplyController {
	
	private static final Logger logger = LoggerFactory.getLogger(ReplyController.class);
	
	@Autowired
	private ReplyService rServ;
	
	@Autowired
	private ReportService reportServ;
	
	@PostMapping
	public ResponseEntity<ReplyDTO> insert(UploadReplyDTO dto) {
		return ResponseEntity.ok(rServ.insert(dto));
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<ReplyDTO> updateById(@PathVariable Long id, String content) {
	    return ResponseEntity.ok(rServ.updateById(id, content));
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		rServ.deleteById(id);
		return ResponseEntity.ok().build();
	}
	
	// 댓글 신고로직
	@PostMapping("/report/{replyId}")
	public ResponseEntity<Void> insertReplyReport(@PathVariable Long replyId, ReportDTO rdto){
		reportServ.insertReplyReport(replyId, rdto);
		return ResponseEntity.ok().build();
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<String> exceptionHandler(Exception e) {
		logger.error(e.getMessage());
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	}

}
