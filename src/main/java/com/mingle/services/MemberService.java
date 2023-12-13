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
}
