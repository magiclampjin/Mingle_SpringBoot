package com.mingle.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mingle.domain.repositories.MemberRepository;
import com.mingle.mappers.MemberMapper;

@Service
public class MemberService {
	@Autowired
	private MemberMapper mMapper;

	@Autowired
	private MemberRepository mReop;

	// 로그인한 사용자 nickName 불러오기
	public String selectUserNickName(String id) {
		return mReop.selectUserNickName(id);
	}

	// 아이디 중복검사
	public boolean idDuplicateCheck(String id) {
		boolean result = mReop.idDuplicateCheck(id);
		return result;
	}

	// 이메일 중복검사
	public boolean emailDuplicateCheck(String email) {
		boolean result = mReop.emailDuplicateCheck(email);
		return result;
	}

	// 전화번호 중복검사
	public boolean phoneDuplicateCheck(String id) {
		boolean result = mReop.phoneDuplicateCheck(id);
		return result;
	}
}
