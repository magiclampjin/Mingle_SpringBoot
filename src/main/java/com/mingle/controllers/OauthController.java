package com.mingle.controllers;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mingle.dto.MemberDTO;
import com.mingle.services.MemberService;

@Controller
@RestController
@RequestMapping("/api/oauth2/authorization")
public class OauthController {
	
	private static final Logger logger = LoggerFactory.getLogger(OauthController.class);
	
	@Autowired
	private MemberService mServ;
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<String> exceptionHandler(Exception e) {
		logger.error(e.getMessage());
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	}
	
//	// 카카오 로그인 시도
//		@PostMapping("/kakao")
//		public ResponseEntity<String> loginKakao(String code) throws IOException{
//			System.out.println("카카오 접근 완료");
//			System.out.println(code);
////			String[] kakaoAccessToken = mServ.getKaKaoAccessToken(code);
////			String access_found_in_token = kakaoAccessToken[0];
////			MemberDTO userInfo = mServ.createKakaoUser(access_found_in_token);
////			return ResponseEntity.ok(userInfo.getId());
//			return ResponseEntity.ok("test");
//		}
}
