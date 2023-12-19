package com.mingle.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mingle.domain.entites.PaymentId;
import com.mingle.dto.PaymentDTO;
import com.mingle.services.PaymentService;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {
	
	@Autowired
	private PaymentService pServ;
	
	// 결제 내역 목록 불러오기
	@GetMapping
	public ResponseEntity<List<PaymentDTO>> selectAll(Authentication authentication) {
	    PaymentId pId = new PaymentId();
	    pId.setMemberId(authentication.getName());
	    
	    System.out.println("pid : "+pId.getMemberId());
	    
	    List<PaymentDTO> list = pServ.selectById(pId.getMemberId());
	    
	   
	    return ResponseEntity.ok(list);
	}
	
}
