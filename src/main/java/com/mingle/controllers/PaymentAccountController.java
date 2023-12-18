package com.mingle.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mingle.dto.PaymentAccountDTO;
import com.mingle.services.PaymentAccountService;

@RestController
@RequestMapping("/api/paymentAccount")
public class PaymentAccountController {
	
	@Autowired
	private PaymentAccountService paService;
	
	// 계좌 등록
	@PostMapping("/accountInsert")
	public ResponseEntity<Void> insertAccountInfo(@RequestBody PaymentAccountDTO dto){
		System.out.println(dto.getBankId());
		System.out.println(dto.getAccountNumber());
		return ResponseEntity.ok().build();
	}
	

}
