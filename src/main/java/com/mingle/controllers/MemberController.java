package com.mingle.controllers;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

	// 사용자 기본정보 불러오기 - 아이디, 닉네임, 권한 
	@GetMapping("/userBasicInfo")
	public ResponseEntity<Map<String, String>> userBasicInfo(Authentication authentication) {
		Map<String, String> userInfo = new HashMap<>();

		// 사용자 아이디 가져오기
		if (authentication != null) {
			String username = authentication.getName();
			// 로그인한 사용자 정보 불러오기
			MemberDTO dto = mServ.userBasicInfo(username);
			// 아이디, 닉네임, 권한 맵으로 생성
			userInfo.put("loginID", username);
			userInfo.put("loginNick", dto.getNickname());
			userInfo.put("loginRole", dto.getRoleId());
			
			System.out.println("userInfo : " + userInfo);
		}

		return ResponseEntity.ok(userInfo);
	}

	// 아이디 중복검사
	@PostMapping("/idDuplicateCheck")
	public ResponseEntity<Boolean> idDuplicateCheck(String id) {
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
	public ResponseEntity<Boolean> phoneDuplicateCheck(String phone) {
		System.out.println(phone);
		boolean result = mServ.phoneDuplicateCheck(phone);
		System.out.println(result);
		return ResponseEntity.ok(result);
	}

	// 닉네임 랜덤 생성
	@GetMapping("/createNickName")
	public ResponseEntity<String> createNickName() {
		String result = mServ.createNickName();
		return ResponseEntity.ok(result);
	}

	// 회원가입
	@PostMapping("/insertMember")
	public ResponseEntity<Integer> insertMember(@RequestBody MemberDTO dto) {
		System.out.println(dto.getBirth());
		Member insertResult = mServ.insertMember(dto);
		int result = insertResult != null ? 1 : 0;
		return ResponseEntity.ok(result);
	}

	// 멤버 이메일, 휴대폰 가져오기
	@GetMapping("/mypageUserInfo")
	public ResponseEntity<MemberDTO> selectMypageInfo(Authentication authentication) {
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
	public ResponseEntity<Boolean> mypageEmailAuth(@RequestParam String email) {
		System.out.println(email);

		System.out.println("Cont- 이메일 전송 완료");

		int number = mServ.sendMail(email);

		num = "" + number;

		return ResponseEntity.ok(true);

	}

	// 이메일 코드 인증
	@GetMapping("/emailChk")
	public ResponseEntity<Boolean> emailChk(Authentication authentication, @RequestParam String code,
			@RequestParam String email) {

		String emailSessionCode = session.getAttribute("emailCode") + "";

		if (code.equals(emailSessionCode)) {

			// 입력한 코드와 발송한 코드가 같으면 이메일 변경

			if (authentication != null) {
				String username = authentication.getName();

				mServ.updateUserEmail(email, username);
			}

			return ResponseEntity.ok(true);
		}
		return ResponseEntity.ok(false);

	}

	// 은행 목록 불러오기
	@GetMapping("/bankList")
	public ResponseEntity<List<BankDTO>> selectBank() {
		List<BankDTO> dto = mServ.selectBank();

		return ResponseEntity.ok(dto);

	}

	// 아이디 찾기 본인 인증 메일 보내기
	@PostMapping("/verificationEmail")
	public ResponseEntity<Boolean> findId(@RequestBody MemberDTO dto) {
		boolean result = mServ.findId(dto);
		return ResponseEntity.ok(result);
	}

	// 아이디 찾기 본인 인증 코드 확인하기
	@PostMapping("/certification/id")
	public ResponseEntity<Boolean> certification(Integer code) {
		boolean result = (code.equals(session.getAttribute("idVerificationCode")));
		session.invalidate();
		return ResponseEntity.ok(result);
	}
	
	// 아이디 찾기
	@PostMapping("/findUserId")
	public ResponseEntity<String> findUserId(@RequestBody MemberDTO dto) {
		MemberDTO result = mServ.findUserId(dto);
		String id = result.getId();
		 // 맨 뒷자리 2개를 '*'로 변경
        int length = id.length();
        String securityId = id.substring(0, length - 2) + "**";
		return ResponseEntity.ok(securityId);
	}
	
	// 비밀번호 찾기 본인 인증 코드 확인하기
	@PostMapping("/certification/pw")
	public ResponseEntity<Boolean> pwFindcertification(Integer code) {
		boolean result = (code.equals(session.getAttribute("pwVerificationCode")));
		session.invalidate();
		return ResponseEntity.ok(result);
	}
	
	// 비밀번호 변경하기
	@PostMapping("/updatePw")
	public ResponseEntity<Boolean> updatePw(@RequestBody MemberDTO dto){
		boolean result = mServ.updateUserPw(dto);
		return ResponseEntity.ok(result);
	}
	
	// 사용자 휴대폰번호 변경
	@PutMapping("/mypagePhoneUpdate")
	public ResponseEntity<Void> updatePhone(Authentication authentication, @RequestBody MemberDTO dto){
		System.out.println(dto.getPhone());
		mServ.updateUserPhone(authentication.getName(),dto.getPhone());
		return ResponseEntity.ok().build();
	}
	
	// 로그인 여부 (파티 생성 시 사용함 - 로그인한 사용자만 생성 가능하도록)
	@GetMapping("/isAuthenticated")
	public ResponseEntity<Boolean> isAuthenticated(Authentication authentication){
		if(authentication != null)
			return ResponseEntity.ok(true);
		else
			return ResponseEntity.ok(false);
	}
	
	// 관리자 여부 (관리자 페이지 접근 시 확인)
	@GetMapping("/isAdmin")
	public ResponseEntity<Boolean> isAdmin(Authentication authentication) {
		boolean isAdmin = mServ.isAdmin(authentication.getName());
		System.out.println("isAdmin? :" + isAdmin);
		if(isAdmin) {
			return ResponseEntity.ok(true);
		} else {
			return ResponseEntity.ok(false);
		}
	}
	
	// 카카오 로그인 시도
	@PostMapping("/login/oauth/kakao")
	public ResponseEntity<MemberDTO> loginKakao(String code) throws IOException{
		System.out.println("카카오 접근 완료");
		System.out.println(code);
		String[] kakaoAccessToken = mServ.getKaKaoAccessToken(code);
		String access_found_in_token = kakaoAccessToken[0];
		MemberDTO userInfo = mServ.createKakaoUser(access_found_in_token);
		return ResponseEntity.ok(userInfo);
	}
	
	// 로그인한 사용자의 mingle money 불러오기 ( 파티 가입 시 사용 - 밍글 머니 우선 적용하기 위함.)
	@GetMapping("/getMingleMoney")
	public ResponseEntity<Integer> selectMingleMoney(Authentication authentication) {
		if(authentication != null)
			return ResponseEntity.ok( mServ.selectMingleMoney(authentication.getName()));
		else 
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
	}
}
