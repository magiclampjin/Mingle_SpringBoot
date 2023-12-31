package com.mingle.controllers;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.mingle.domain.entites.Member;
import com.mingle.dto.MemberDTO;
import com.mingle.services.MemberService;
import com.mingle.services.PartyService;


import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import jakarta.servlet.http.HttpSession;

@Controller
@RestController
@RequestMapping("/api/member")
public class MemberController {
	
	private static final Logger logger = LoggerFactory.getLogger(MemberController.class);
	
	@Autowired
	private MemberService mServ;

	@Autowired
	private HttpSession session;

	String num = "";
	
	@Autowired
	private PartyService pServ;
	
	@Autowired
    private LogoutHandler customLogoutHandler;

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

			logger.debug("userInfo : " + userInfo);
		} else {
			logger.debug("authentication 비어있음");
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
		logger.debug(phone);
		boolean result = mServ.phoneDuplicateCheck(phone);
		logger.debug("result"+result);
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
	public ResponseEntity<Integer> mypageEmailAuth(@RequestParam String email) {
		logger.debug(email);
		boolean result = mServ.emailDuplicateCheck(email);
		if(result) {
			// 중복
			return ResponseEntity.ok(0);
		}else {
			logger.debug("Cont - 이메일 전송 완료");
			int number = mServ.sendMail(email);
			num = "" + number;

			return ResponseEntity.ok(1);
		}
		

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


	// 아이디 찾기 본인 인증 메일 보내기
	@PostMapping("/verificationEmail")
	public ResponseEntity<Boolean> verificationEmail(@RequestBody MemberDTO dto) {
		boolean result = mServ.verificationEmail(dto);
		return ResponseEntity.ok(result);
	}

	// 아이디 찾기 본인 인증 코드 확인하기
	@PostMapping("/certification/id")
	public ResponseEntity<Boolean> certification(String code) {
		boolean result = (code.equals(session.getAttribute("idVerificationCode").toString()));
//		session.invalidate();
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
		session.invalidate(); // 아이디 찾기 하고 나면 세션 비우기 ( 추후 같은 인증코드 사용 방지 및 로그인 하지 않았으므로 세션 필요없다고 판단하여 비워버림 -> 혹시 사용자 방문 기록등에서 세션을 사용하게된다면 통으로 비우지 않고 해당 키값만 비우는 걸로 변경 필요) 
		return ResponseEntity.ok(securityId);
	}

	// 비밀번호 찾기 본인 인증 코드 확인하기
	@PostMapping("/certification/pw")
	public ResponseEntity<Boolean> pwFindcertification(String code) {
		boolean result = (code.equals(session.getAttribute("pwVerificationCode").toString()));
//		session.invalidate();
		return ResponseEntity.ok(result);
	}

	// 비밀번호 변경하기
	@PutMapping("/updatePw")
	public ResponseEntity<Boolean> updatePw(@RequestBody MemberDTO dto) {
		boolean result = mServ.updateUserPw(dto);
		session.invalidate(); // 아이디 찾기 하고 나면 세션 비우기 ( 추후 같은 인증코드 사용 방지 및 로그인 하지 않았으므로 세션 필요없다고 판단하여 비워버림 -> 혹시 사용자 방문 기록등에서 세션을 사용하게된다면 통으로 비우지 않고 해당 키값만 비우는 걸로 변경 필요) 
		return ResponseEntity.ok(result);
	}

	// 사용자 휴대폰번호 변경
	@PutMapping("/mypagePhoneUpdate")
	public ResponseEntity<Integer> updatePhone(Authentication authentication, @RequestBody MemberDTO dto) {
		logger.debug(dto.getPhone());
		// 휴대폰번호 중복 확인
		boolean result = mServ.phoneDuplicateCheck(dto.getPhone());
		if(result) {
			// 중복
			return ResponseEntity.ok(0);
		}else {
			mServ.updateUserPhone(authentication.getName(), dto.getPhone());
			return ResponseEntity.ok(1);
		}
		
	}

	// 로그인 여부 (파티 생성 시 사용함 - 로그인한 사용자만 생성 가능하도록)
	@GetMapping("/isAuthenticated")
	public ResponseEntity<Boolean> isAuthenticated(Authentication authentication) {
		if (authentication != null)
			return ResponseEntity.ok(true);
		else
			return ResponseEntity.ok(false);
	}
	
	// 관리자 여부 (관리자 페이지 접근 시 확인)
	@GetMapping("/isAdmin")
	public ResponseEntity<Boolean> isAdmin(Authentication authentication) {
		boolean isAdmin = mServ.isAdmin(authentication.getName());
		logger.debug("isAdmin: "+isAdmin);
		if(isAdmin) {
			return ResponseEntity.ok(true);
		} else {
			return ResponseEntity.ok(false);
		}
	}

	@GetMapping("/oauth/loginInfo")
	@ResponseBody
	public String oauthLoginInfo(Authentication authentication,
			@AuthenticationPrincipal OAuth2User oAuth2UserPrincipal) {
		if (authentication != null) {
			OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
			Map<String, Object> attributes = oAuth2User.getAttributes();
			logger.debug(attributes.toString());
			// PrincipalOauth2UserService의 getAttributes내용과 같음

			Map<String, Object> attributes1 = oAuth2UserPrincipal.getAttributes();
			// attributes == attributes1

			return attributes.toString();
		}
		return "실패";
	}

	
	// 회원 탈퇴
	@GetMapping("/mypageMemberOut")
	public ResponseEntity<Integer> memberOut(Authentication authentication, @RequestParam String password,
			HttpServletRequest request, HttpServletResponse response){
		
		// 1 : 계정 삭제 
		// 2 : 비밀번호 일치하지 않음
		// 3 : 가입한 파티가 있음
		
		// 비밀번호 일치하는지 먼저 확인
		Boolean pwResult = mServ.isEqualPw(authentication.getName(),password);
		
		// 비밀번호가 일치
		if(pwResult) {
			
			// 이미 가입된 파티가 있는지 확인
			boolean result = pServ.isMemberParty(authentication.getName());
			
			// 가입한 파티가 있다면
			if(result) {
				// 탈퇴 불가능
				return ResponseEntity.ok(3); 
			}else {
				// 가입한 파티가 없다면 탈퇴 가능
				// 로그아웃 처리
	            customLogoutHandler.logout(request, response, authentication);
	            
				// 계정삭제
				mServ.memberOut(authentication.getName());
				return ResponseEntity.ok(1);
			}
			
		}else {
			// 비밀번호 일치하지 않음
			return ResponseEntity.ok(2);
		}
		
	}
		
	// 로그인한 사용자의 mingle money 불러오기 ( 파티 가입 시 사용 - 밍글 머니 우선 적용하기 위함.)
	@GetMapping("/getMingleMoney")
	public ResponseEntity<Integer> selectMingleMoney(Authentication authentication) {
		if (authentication != null)
			return ResponseEntity.ok(mServ.selectMingleMoney(authentication.getName()));
		else
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
	}

	// 회원가입 본인 인증 메일 보내기
	@PostMapping("/verificationSignupEmail")
	public ResponseEntity<Boolean> verificationSignupEmail(String email) throws MessagingException {
		boolean result = mServ.verificationSignupEmail(email);
		return ResponseEntity.ok(result);
	}

	// 비밀번호 찾기 본인 인증 코드 확인하기
	@PostMapping("/certification/signup")
	public ResponseEntity<Boolean> signupcertification(String code) {
		boolean result = (code.equals(session.getAttribute("signupVerificationCode").toString()));
		return ResponseEntity.ok(result);
	}
	
	// 회원가입 본인 인증 코드 제거
	@GetMapping("/removeVerificationCode")
	public ResponseEntity<Void> removeSignupVerificationCode(){
		session.invalidate();
		System.out.println(session.getAttribute("signupVerificationCode"));
		return ResponseEntity.ok().build();
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<String> exceptionHandler(Exception e) {
		logger.error(e.getMessage());
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	}
}
