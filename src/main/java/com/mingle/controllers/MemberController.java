package com.mingle.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mingle.services.MemberService;

import jakarta.servlet.http.HttpSession;

@Controller
@RestController
@RequestMapping("/api/member")
public class MemberController {
	@Autowired
	private HttpSession session;
	
	@Autowired
	private MemberService mServ;
	
	// 사용자 기본정보 불러오기 - 아이디, 닉네임
	@GetMapping("/userBasicInfo")
	public ResponseEntity<Map<String, String>> selectUserNickName(Authentication authentication) {
		Map<String, String> userInfo = new HashMap<>();
		
		// 사용자 아이디 가져오기
		if(authentication!=null) {
			String username = authentication.getName();
			System.out.println("username:"+username);
		// 세션에 아이디 저장
	        // session.setAttribute("loginID", username);
	        // 세션에 닉네임 저장
	        String userNick = mServ.selectUserNickName(username);
	        // session.setAttribute("loginNick", userNick);
	        //아이디랑 닉네임 맵으로 생성
	        
	        userInfo.put("loginID", username);
	        userInfo.put("loginNick", userNick);
		}else {
			userInfo=null;
		}
		
        return ResponseEntity.ok(userInfo);
	}
}
