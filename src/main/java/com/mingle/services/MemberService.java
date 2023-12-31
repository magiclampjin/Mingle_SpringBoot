package com.mingle.services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mingle.controllers.AdminController;
import com.mingle.domain.entites.Member;
import com.mingle.domain.repositories.AdjectiveRepository;
import com.mingle.domain.repositories.AdjectiveViewRepository;
import com.mingle.domain.repositories.MemberRepository;
import com.mingle.domain.repositories.NounRepository;
import com.mingle.domain.repositories.NounViewRepository;
import com.mingle.dto.MemberDTO;
import com.mingle.mappers.MemberMapper;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;

@Service
public class MemberService {
	
	private static final Logger logger = LoggerFactory.getLogger(MemberService.class);
	
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

	// 비밀번호 인코딩
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private HttpSession session;

	@Autowired
	private JavaMailSender javaMailSender;
	private static final String senderEmail = "mingle@gmail.com";
	private int number;

	// 로그인한 사용자 정보 불러오기
	public MemberDTO userBasicInfo(String id) {
		return mMapper.toDto(mRepo.userBasicInfo(id));
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
		String nick = "";
		while (duplication) {
			// db에 저장된 형용사 총 개수 불러오기
			Long adjective = aRepo.selectAdjectiveCount();
			// db에 저장된 명사 총 개수 불러오기
			Long noun = nRepo.selectNounCount();

			// 선택할 단어의 순서
			int randAdjective = (int) (Math.random() * adjective) + 1;
			int randNoun = (int) (Math.random() * noun) + 1;

			// 선택된 단어 가져오기
			String adjectiveText = avRepo.selectAdjectiveByNum(randAdjective);
			String nounText = nvRepo.selectNounByNum(randNoun);

			nick = adjectiveText + nounText;
			// 단어 중복검사
			duplication = mRepo.nickDuplicateCheck(nick);
		}
		return nick;
	}

	// 회원가입
	public Member insertMember(MemberDTO dto) {
		// 비밀번호 인코딩
		String pwEncoding = passwordEncoder.encode(dto.getPassword());
		// 현재 시각을 얻어옴 -> 가입 일자 저장
		LocalDateTime now = LocalDateTime.now();

		// birth를 -9시간으로 조정
	    Instant adjustedBirth = dto.getBirth().minusSeconds(9 * 60 * 60);	
		Member user = mMapper.toEntity(dto);
		user.setPassword(pwEncoding);
		user.setRoleId("ROLE_MEMBER");
		user.setSignupDate(Timestamp.valueOf(now));
		user.setEnabled(true);
		user.setMingleMoney((long) 0);
		// 조정된 birth를 Timestamp로 변환
	    Timestamp timestampBirth = Timestamp.from(adjustedBirth);
	    user.setBirth(timestampBirth);
	    
	    if(user.getMemberRecommenderId().equals("")) {
	    	user.setMemberRecommenderId(null);
	    }
		return mRepo.save(user);
	}

	// 멤버 이메일, 휴대폰 가져오기
	public MemberDTO selectMypageInfo(String id) {
		return mMapper.toDto(mRepo.selectMypageInfo(id));
	}

	// 이메일 인증
	// 이메일 관련 코드
	public void createNumber() {
		number = (int) (Math.random() * (90000)) + 10000;

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
			body += "<h3>" + "요청하신 인증 번호입니다." + "<h3>";
			body += "<h1>" + session.getAttribute("emailCode") + "<h1>";
			body += "<h3>" + "감사합니다." + "<h3>";
			message.setText(body, "UTF-8", "html");
		} catch (MessagingException e) {
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
		Member m = mRepo.findAllById(username);
		m.setEmail(email);
		mRepo.save(m);
	}


	// 이메일 인증코드 생성 - 아이디 찾기
	public void idVerificationCode() {
		int number = (int) (Math.random() * (90000)) + 10000;
		session.setAttribute("idVerificationCode", number);
	}

	// 이메일 인증코드 생성 - 비밀번호 찾기
	public void pwVerificationCode() {
		int number = (int) (Math.random() * (90000)) + 10000;
		session.setAttribute("pwVerificationCode", number);
	}

	// 아이디 찾기, 비밀번호 변경 본인 인증 메일 보내기
	public boolean verificationEmail(MemberDTO dto) {
		// 이름과 메일 정보가 일치하는 사용자가 있는지 검증
		boolean verification = false;
		if (dto.getId() == null) {// 아이디 찾기
			verification = mRepo.userVerification(dto);
		} else {// 비밀번호 변경
			verification = mRepo.userPWVerification(dto);
		}

		if (verification) {

			if (dto.getId() == null) {// 아이디 찾기
				idVerificationCode();// 이메일 인증코드 생성 - 아이디 찾기
			} else {// 비밀번호 변경
				pwVerificationCode();// 이메일 인증코드 생성 - 비밀번호 찾기
			}

			// 메일 전송 객체 생성
			MimeMessage message = javaMailSender.createMimeMessage();
			try {
				message.setFrom(senderEmail);// 보내는 사람 설정
				message.setRecipients(MimeMessage.RecipientType.TO, dto.getEmail());// 받는사람 설정
				if (dto.getId() == null) {// 아이디 찾기
					message.setSubject("Mingle - [아이디 찾기] 이메일 본인 인증");// 제목 설정
				} else {// 비밀번호 변경
					message.setSubject("Mingle - [비밀번호 변경] 이메일 본인 인증");// 제목 설정
				}

				String body = "";// 메일 본문 설정
				body += "<h3>" + "본인인증을 위해 요청하신 인증 번호입니다." + "<h3>";
				if (dto.getId() == null) {// 아이디 찾기
					body += "<h1>" + session.getAttribute("idVerificationCode") + "<h1>";
				} else {// 비밀번호 변경
					body += "<h1>" + session.getAttribute("pwVerificationCode") + "<h1>";
				}

				body += "<h3>" + "감사합니다." + "<h3>";
				message.setText(body, "UTF-8", "html");
			} catch (MessagingException e) {
				e.printStackTrace();
			}

			javaMailSender.send(message);
			return true;
		} else {
			return false;
		}
	}

	// 아이디 찾기
	public MemberDTO findUserId(MemberDTO dto) {
		Member m = mRepo.findByNameAndEmail(dto.getName(), dto.getEmail());
		return mMapper.toDto(m);
	}

	// 비밀번호 변경하기
	public boolean updateUserPw(MemberDTO dto) {
		// 비밀번호 인코딩
		String pwEncoding = passwordEncoder.encode(dto.getPassword());

		Member m = mRepo.findAllById(dto.getId());
		m.setPassword(pwEncoding);
		return mRepo.save(m) != null;
	}
	
	// 사용자 휴대폰번호 변경
	public void updateUserPhone(String id, String phone) {
		Member m = mRepo.findAllById(id);
		m.setPhone(phone);
		mRepo.save(m);
	}
	
	// 권한
	public boolean isAdmin(String id) {
		return mRepo.isAdmin(id);
	}

	// 로그인한 사용자의 이름 불러오기
	public String selectUserName(String userId) {
		return mRepo.selectUserName(userId);
	}

	
	// 비밀번호 일치 확인
	public boolean isEqualPw(String userId, String password) {
		
		// 데이터베이스에서 사용자의 비밀번호 가져오기
		String dbPw = mRepo.selectUserPw(userId);
		
		// 입력받은 비밀번호와 데이터베이스의 비밀번호 비교
		boolean matches = passwordEncoder.matches(password, dbPw);
		
		
		return matches;
	}
	
	// 회원 탈퇴
	public void memberOut(String userId) {
		mRepo.deleteById(userId);
	}

	
	// 로그인한 사용자의 mingle money 불러오기
	public int selectMingleMoney(String id) {
		return mRepo.selectMingleMoney(id);
	}

	// 이메일 인증코드 생성 - 회원가입
	public void signupVerificationCode() {
		int number = (int) (Math.random() * (90000)) + 10000;
		session.setAttribute("signupVerificationCode", number);
	}

	// 회원가입 본인 인증 메일 보내기
	public boolean verificationSignupEmail(String email) throws MessagingException {
		signupVerificationCode();//이메일 인증코드 생성
		// 메일 전송 객체 생성
		MimeMessage message = javaMailSender.createMimeMessage();
		message.setFrom(senderEmail);// 보내는 사람 설정
		message.setRecipients(MimeMessage.RecipientType.TO, email);// 받는사람 설정
		message.setSubject("Mingle - [회원가입] 이메일 본인 인증");// 제목 설정
		String body = "";// 메일 본문 설정
		body += "<h3>" + "본인인증을 위해 요청하신 인증 번호입니다." + "<h3>";
		body += "<h1>" + session.getAttribute("signupVerificationCode") + "<h1>";
		body += "<h3>" + "감사합니다." + "<h3>";
		message.setText(body, "UTF-8", "html");
		javaMailSender.send(message);
		return true;
	}

	// 인출하기 - 멤버의 밍글머니에서 빼기
	public void minusMoney(String userId,String money) {
		Member m = mRepo.findById(userId).get();
		Long resultMoney = m.getMingleMoney()-Long.parseLong(money);
		m.setMingleMoney(resultMoney);
		mRepo.save(m);
	}
	
	// 계정 정지
	public void updateEnabledFalse(String userId) {
		Member m = mRepo.findById(userId).get();
		m.setEnabled(false);
		mRepo.save(m);
	}
}
