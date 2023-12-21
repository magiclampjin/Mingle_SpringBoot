package com.mingle.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

import com.mingle.dto.BankDTO;
import com.mingle.dto.PaymentAccountDTO;
import com.mingle.services.MemberService;
import com.mingle.services.PartyService;
import com.mingle.services.PaymentAccountService;

@RestController
@RequestMapping("/api/paymentAccount")
public class PaymentAccountController {
	
	@Autowired
	private PaymentAccountService paServ;
	
	@Autowired
	private MemberService mServ;
	
	@Autowired
	private PartyService pServ;
	
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
			dto.setAccountHolder(mServ.selectUserName(authentication.getName()));
			
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
	public ResponseEntity<String> deleteById(Authentication authentication){
		
		
		// 이미 가입된 파티가 있는지 확인
		boolean result = pServ.isMemberParty(authentication.getName());
		
		if(result) {
			return ResponseEntity.ok("가입된 파티가 있으면 삭제 불가능합니다.");
		}else {
			paServ.deleteById(authentication.getName());
			return ResponseEntity.ok("삭제 완료되었습니다.");
		}
		
	}
	
	// 등록된 계좌 수정하기
	@PutMapping("/accountUpdate")
	public ResponseEntity<Void> updateById(Authentication authentication, @RequestBody PaymentAccountDTO dto){
		paServ.updateById(authentication.getName(),dto);
		
		return ResponseEntity.ok().build();
	}
	
	// 은행 목록 불러오기
	@GetMapping("/selectBankList")
	public ResponseEntity<List<BankDTO>> selectBank() {
		List<BankDTO> dto = paServ.selectBank();

		return ResponseEntity.ok(dto);

	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<String> exceptionHandler(Exception e) {
		e.printStackTrace();
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	}
}
