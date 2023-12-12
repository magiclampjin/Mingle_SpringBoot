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
	
	public String selectUserNickName(String id) {
		String nick = mReop.selectUserNickName(id);
		// String nick = mReop.findByNicknameIs(id);
		return nick;
	}
}
