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
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.mingle.domain.entites.Member;
import com.mingle.domain.repositories.AdjectiveRepository;
import com.mingle.domain.repositories.AdjectiveViewRepository;
import com.mingle.domain.repositories.MemberRepository;
import com.mingle.domain.repositories.NounRepository;
import com.mingle.domain.repositories.NounViewRepository;
import com.mingle.dto.MemberDTO;
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
	
	// 비밀번호 인코딩
	@Autowired
	private PasswordEncoder passwordEncoder;
	
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
//	public String selectUserNickName(String id) {
//		return mRepo.selectUserNickName(id);
//	}
	public MemberDTO selectUserNickName(String id) {
		return mMapper.toDto(mRepo.selectUserNickName(id));
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
		System.out.println(dto.toString());
		// 비밀번호 인코딩
		String pwEncoding = passwordEncoder.encode(dto.getPassword());
		System.out.println(pwEncoding);
		
		// 생년월일 매핑
		String birthString = dto.getBirth().toString();
		System.out.println(birthString);
		
		// 현재 시각을 얻어옴
        LocalDateTime now = LocalDateTime.now();

        // 시간대 변환 (UTC에서 Asia/Seoul로)
        LocalDateTime koreaTime = now.atZone(ZoneId.of("UTC")).withZoneSameInstant(ZoneId.of("Asia/Seoul")).toLocalDateTime();
        
		Member user = mMapper.toEntity(dto);
		user.setPassword(pwEncoding);
		user.setRoleId("ROLE_MEMBER");
		user.setSignupDate(Timestamp.valueOf(koreaTime));
		user.setEnabled(true);
		return mRepo.save(user);
	}
	
	// 멤버 이메일, 휴대폰 가져오기
	public MemberDTO selectMypageInfo(String id) {
		return mMapper.toDto(mRepo.selectMypageInfo(id));
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
		Member m = mRepo.findAllById(username);
		m.setEmail(email);
		mRepo.save(m);
	}
	
	//은행 목록 불러오기
	public List<BankDTO> selectBank(){
		
		return bMapper.toDtoList(bRepo.findAll());
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
}
