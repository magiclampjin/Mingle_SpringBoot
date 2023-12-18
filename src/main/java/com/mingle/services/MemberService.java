package com.mingle.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.mingle.domain.entites.Member;
import com.mingle.domain.repositories.BankRepository;
import com.mingle.domain.repositories.MemberRepository;
import com.mingle.dto.BankDTO;
import com.mingle.dto.MemberDTO;
import com.mingle.mappers.BankMapper;
import com.mingle.mappers.MemberMapper;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpSession;

@Service
public class MemberService {
	@Autowired
	private MemberMapper mMapper;

	@Autowired
	private MemberRepository mReop;
	
	@Autowired
	private BankRepository bRepo;
	
	@Autowired
	private BankMapper bMapper;
	
	@Autowired
	private HttpSession session;
	
	@Autowired
	private JavaMailSender javaMailSender;
	private static final String senderEmail = "mingle@gmail.com";
	private int number;

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
	
	// 멤버 이메일, 휴대폰 가져오기
	public MemberDTO selectMypageInfo(String id) {
		return mMapper.toDto(mReop.selectMypageInfo(id));
	}
	
	// 이메일 인증
	// 이메일 관련 코드
	public void createNumber() {
		number = (int)(Math.random()*(90000)) + 10000;
		
		session.setAttribute("emailCode", number);
	}
	
	// 이메일 인증 생성
	public MimeMessage CreateMail(String email) {
		
		createNumber();
		
		MimeMessage message = javaMailSender.createMimeMessage();
		
		try {
			message.setFrom(senderEmail);
			message.setRecipients(MimeMessage.RecipientType.TO, email);
			message.setSubject("Mingle 이메일 인증");
			String body = "";
			body += "<h3>"+"요청하신 인증 번호입니다."+"<h3>";
			body += "<h1>"+session.getAttribute("emailCode")+"<h1>";
			body += "<h3>"+"감사합니다."+"<h3>";
			message.setText(body,"UTF-8","html");
		}catch(MessagingException e) {
			e.printStackTrace();
		}
		return message;
	}
	
	// 이메일 보냄
	public int sendMail(String email) {
		MimeMessage message = CreateMail(email);
		javaMailSender.send(message);
		
		return number;
	}
	
	// 이메일 변경
	public void updateUserEmail(String email, String username) {
		
		// 멤버 엔티티불러오기
		Member m = mReop.findAllById(username);
		m.setEmail(email);
		mReop.save(m);
	}
	
	//은행 목록 불러오기
	public List<BankDTO> selectBank(){
		
		return bMapper.toDtoList(bRepo.findAll());
	}
}
