package com.mingle.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mingle.domain.repositories.AdjectiveRepository;
import com.mingle.domain.repositories.AdjectiveViewRepository;
import com.mingle.domain.repositories.MemberRepository;
import com.mingle.domain.repositories.NounRepository;
import com.mingle.domain.repositories.NounViewRepository;
import com.mingle.mappers.MemberMapper;

import jakarta.transaction.Transactional;

@Service
public class MemberService {
	@Autowired
	private MemberMapper mMapper;

	@Autowired
	private MemberRepository mRepo;
	
	// 닉네임 형용사 관련
	@Autowired
	private AdjectiveRepository aRepo;
	
	// 닉네임 형용사 뷰
	@Autowired
	private AdjectiveViewRepository avRepo;
	
	// 닉네임 명사 관련
	@Autowired
	private NounRepository nRepo;
	
	// 닉네임 명사 뷰
	@Autowired
	private NounViewRepository nvRepo;

	// 로그인한 사용자 nickName 불러오기
	public String selectUserNickName(String id) {
		return mRepo.selectUserNickName(id);
	}

	// 아이디 중복검사
	public boolean idDuplicateCheck(String id) {
		boolean result = mRepo.idDuplicateCheck(id);
		return result;
	}

	// 이메일 중복검사
	public boolean emailDuplicateCheck(String email) {
		boolean result = mRepo.emailDuplicateCheck(email);
		return result;
	}

	// 전화번호 중복검사
	public boolean phoneDuplicateCheck(String id) {
		boolean result = mRepo.phoneDuplicateCheck(id);
		return result;
	}
	
	// 닉네임 랜덤 생성
	@Transactional
	public String createNickName() {
		boolean duplication = true;
		String nick="";
		while(duplication) {
			// db에 저장된 형용사 총 개수 불러오기
			Long adjective = aRepo.selectAdjectiveCount();
			// db에 저장된 명사 총 개수 불러오기
			Long noun = nRepo.selectNounCount();
			System.out.println(noun);
			
			// 선택할 단어의 순서
			int randAdjective = (int)(Math.random() * adjective) + 1;
			int randNoun = (int)(Math.random() * noun) + 1;
			System.out.println(randNoun);
			String adjectiveText = avRepo.selectAdjectiveByNum(randAdjective);
			String nounText = nvRepo.selectNounByNum(randNoun);
			System.out.println(nounText);
			
			nick = adjectiveText+nounText;
			duplication= mRepo.nickDuplicateCheck(nick);
		}
		return nick;
	}
}
