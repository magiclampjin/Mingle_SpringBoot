package com.mingle.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mingle.dto.PaymentAccountDTO;
import com.mingle.services.PaymentAccountService;

@RestController
@RequestMapping("/api/paymentAccount")
public class PaymentAccountController {
	
	@Autowired
	private PaymentAccountService paServ;
	
	// 계좌 등록
	@PostMapping("/accountInsert")
	public ResponseEntity<Void> insertAccountInfo(Authentication authentication, @RequestBody PaymentAccountDTO dto){
		System.out.println(dto.getBankId());
		System.out.println(dto.getAccountNumber());
		
		// 사용자 아이디 가져오기
		if (authentication != null) {
			// 로그인한 사용자의 아이디
			dto.setMemberId(authentication.getName());
			
			// 로그인한 사용자의 이름 불러오기
			dto.setAccountHolder(paServ.selectUserName(authentication.getName()));
			
			// 계좌 등록
			paServ.insertAccountInfo(dto);
		}
		
		return ResponseEntity.ok().build();
	}
	
	// 등록된 계좌 불러오기
	@GetMapping("/accountSelect")
	public ResponseEntity<PaymentAccountDTO> selectById(Authentication authentication){
		// 로그인한 사용자의 아이디로 계좌 목록 불러오기
		PaymentAccountDTO dto = paServ.selectById(authentication.getName());
		
		return ResponseEntity.ok(dto);
	}
	
	// 등록된 계좌 삭제하기
	@DeleteMapping("/accountDelete")
	public ResponseEntity<Void> deleteById(Authentication authentication){
		paServ.deleteById(authentication.getName());
		
		return ResponseEntity.ok().build();
		
	}
	
	// 등록된 계좌 수정하기
	@PutMapping("/accountUpdate")
	public ResponseEntity<Void> updateById(Authentication authentication, @RequestBody PaymentAccountDTO dto){
		paServ.updateById(authentication.getName(),dto);
		
		return ResponseEntity.ok().build();
	}
	
	@ExceptionHandler(Exception.class)
	public String exceptionHandler(Exception e) {
		e.printStackTrace();
		return "error";
	}
}
