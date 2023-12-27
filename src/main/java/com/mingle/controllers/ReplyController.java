package com.mingle.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mingle.dto.ReplyDTO;
import com.mingle.dto.UploadReplyDTO;
import com.mingle.services.ReplyService;

@RestController
@RequestMapping("/api/reply")
public class ReplyController {
	
	@Autowired
	private ReplyService rServ;
	
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

}
