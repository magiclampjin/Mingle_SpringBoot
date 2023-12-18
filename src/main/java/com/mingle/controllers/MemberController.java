package com.mingle.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mingle.domain.entites.Member;
import com.mingle.dto.BankDTO;
import com.mingle.dto.MemberDTO;
import com.mingle.services.MemberService;

import jakarta.servlet.http.HttpSession;

@Controller
@RestController
@RequestMapping("/api/member")
public class MemberController {
	@Autowired
	private MemberService mServ;
	
	@Autowired
	private HttpSession session;
	
	String num = "";

	// 사용자 기본정보 불러오기 - 아이디, 닉네임
	@GetMapping("/userBasicInfo")
	public ResponseEntity<Map<String, String>> selectUserNickName(Authentication authentication) {
		Map<String, String> userInfo = new HashMap<>();

		// 사용자 아이디 가져오기
		if (authentication != null) {
			String username = authentication.getName();
			// 로그인한 사용자 nickName 불러오기
			String userNick = mServ.selectUserNickName(username);
			// 아이디랑 닉네임 맵으로 생성
			userInfo.put("loginID", username);
			userInfo.put("loginNick", userNick);
		}

		return ResponseEntity.ok(userInfo);
	}

	// 아이디 중복검사
	@PostMapping("/idDuplicateCheck")
	public ResponseEntity<Boolean> idDuplicateCheck(@RequestBody String id) {
		boolean result = mServ.idDuplicateCheck(id);
		return ResponseEntity.ok(result);
	}

	// 이메일 중복검사
	@PostMapping("/emailDuplicateCheck")
	public ResponseEntity<Boolean> emailDuplicateCheck(String email) {
		boolean result = mServ.emailDuplicateCheck(email);
		return ResponseEntity.ok(result);
	}

	// 전화번호 중복검사
	@PostMapping("/phoneDuplicateCheck")
	public ResponseEntity<Boolean> phoneDuplicateCheck(@RequestBody String phone) {
		boolean result = mServ.phoneDuplicateCheck(phone);
		return ResponseEntity.ok(result);
	}
	
	// 닉네임 랜덤 생성
	@GetMapping("/createNickName")
	public ResponseEntity<String> createNickName(){
		String result = mServ.createNickName();
		return ResponseEntity.ok(result);
	}
	
	// 회원가입
	@PostMapping("/insertMember")
	public ResponseEntity<Integer> insertMember(@RequestBody MemberDTO dto){
		System.out.println(dto.getBirth());
		Member insertResult = mServ.insertMember(dto);
		int result = insertResult!=null?1:0;
		return ResponseEntity.ok(result);
	}

	//멤버 이메일, 휴대폰 가져오기
	@GetMapping("/mypageUserInfo")
	public ResponseEntity<MemberDTO> selectMypageInfo(Authentication authentication){
		MemberDTO dto = null;
		
		// 사용자 아이디 가져오기
		if (authentication != null) {
			String username = authentication.getName();
			
			// 로그인한 사용자 nickName 불러오기
			dto = mServ.selectMypageInfo(username);
		}
		
		return ResponseEntity.ok(dto);
	}
	
	// 이메일 인증
	@GetMapping("/mypageEmailAuth")
	public ResponseEntity<Boolean> mypageEmailAuth(@RequestParam String email){
		System.out.println(email);

		System.out.println("Cont- 이메일 전송 완료");

		int number = mServ.sendMail(email);

		num = "" + number;

		return ResponseEntity.ok(true);
		
	}
	
	// 이메일 코드 인증
	@GetMapping("/emailChk")
	public ResponseEntity<Boolean> emailChk(Authentication authentication, @RequestParam String code, @RequestParam String email){
		
		String emailSessionCode = session.getAttribute("emailCode") + "";

		if (code.equals(emailSessionCode)) {
			
			// 입력한 코드와 발송한 코드가 같으면 이메일 변경
			
			if (authentication != null) {
				String username = authentication.getName();
				
				mServ.updateUserEmail(email,username);
			}
			
			
			return ResponseEntity.ok(true);
		}
		return ResponseEntity.ok(false);
		
	}
	
	// 은행 목록 불러오기
	@GetMapping("/bankList")
	public ResponseEntity<List<BankDTO>> selectBank(){
		List<BankDTO> dto = mServ.selectBank();
		
		return ResponseEntity.ok(dto);
		
	}
	
	// 사용자 휴대폰번호 변경
	@PutMapping("/mypagePhoneUpdate")
	public ResponseEntity<Void> updatePhone(Authentication authentication, @RequestBody MemberDTO dto){
		System.out.println(dto.getPhone());
		mServ.updateUserPhone(authentication.getName(),dto.getPhone());
		return ResponseEntity.ok().build();
	}
	
}
