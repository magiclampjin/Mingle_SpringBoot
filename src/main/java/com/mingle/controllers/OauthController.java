package com.mingle.controllers;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mingle.dto.MemberDTO;
import com.mingle.services.MemberService;

@Controller
@RestController
@RequestMapping("/api/oauth2/authorization")
public class OauthController {
	@Autowired
	private MemberService mServ;
	
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
