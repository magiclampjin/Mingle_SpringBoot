package com.mingle.services;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mingle.domain.entites.Member;
import com.mingle.domain.repositories.AdjectiveRepository;
import com.mingle.domain.repositories.AdjectiveViewRepository;
import com.mingle.domain.repositories.BankRepository;
import com.mingle.domain.repositories.MemberRepository;
import com.mingle.domain.repositories.NounRepository;
import com.mingle.domain.repositories.NounViewRepository;
import com.mingle.dto.BankDTO;
import com.mingle.dto.MemberDTO;
import com.mingle.mappers.BankMapper;
import com.mingle.mappers.MemberMapper;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpSession;
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

	// 카카오 api키
	@Value("${REST_API_KEY}")
	private String REST_API_KEY;

	// 카카오 로그인 후 리다이렉트 주소
	@Value("${REDIRECT_URL}")
	private String REDIRECT_URL;

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
		// 현재 시각을 얻어옴
		LocalDateTime now = LocalDateTime.now();
		// 시간대 변환 (UTC에서 Asia/Seoul로)
		LocalDateTime koreaTime = now.atZone(ZoneId.of("UTC")).withZoneSameInstant(ZoneId.of("Asia/Seoul"))
				.toLocalDateTime();

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

	// 은행 목록 불러오기
	public List<BankDTO> selectBank() {

		return bMapper.toDtoList(bRepo.findAll());
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
	public boolean findId(MemberDTO dto) {
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

	// 카카오 access_token 발급받기
	public String[] getKaKaoAccessToken(String code) {
		String access_Token = "";
		String refresh_Token = "";
		String reqURL = "https://kauth.kakao.com/oauth/token";
		String result = null;
		String id_token = null;
		System.out.println(REST_API_KEY);
		try {
			// url로 요청을 보내기 위한 객체 생성
			URL url = new URL(reqURL);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();

			// post 요청을 위해 기본값 이 false인 setDoOutput을 true로 변경
			conn.setRequestMethod("POST");
			conn.setDoOutput(true);

			// post 요청에 필요로 요구하는 파라미터를 스트림을 통해 전송
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
			StringBuilder sb = new StringBuilder();
			sb.append("grant_type=authorization_code"); // 승인 유형은 인증 코드
			sb.append("&client_id=" + REST_API_KEY); // REST_API_KEY 입력
			sb.append("&redirect_uri=" + REDIRECT_URL); // 인가코드 받은 redirect_uri 입력
			sb.append("&code=" + code); // 사용자의 인증 코드
			bw.write(sb.toString());
			bw.flush(); // 스트림을 통해 값 전송

			// 결과 코드가 200(성공) 일때
			int responseCode = conn.getResponseCode();
			System.out.println("responseCode : " + responseCode);
			
			if (responseCode == 200) {
				BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
				String line = "";
				result = "";
				
				while ((line = br.readLine()) != null) {
					result += line;
				}
				// bearer 토큰값만 추출 (log에 찍히는 값의 이름은 id_Token)
				System.out.println("response body : " + result);
				String[] temp = result.split(",");
				id_token = temp[3].split("\":\"")[1];
				System.out.println(id_token);

				// JSON 파싱 객체 생성
				// ObjectMapper를 사용하여 JSON 문자열을 JsonNode로 파싱
				ObjectMapper objectMapper = new ObjectMapper();
				JsonNode jsonNode = objectMapper.readTree(result);

				access_Token = jsonNode.get("access_token").toString();
				refresh_Token = jsonNode.get("refresh_token").toString();

				System.out.println("access_token : " + access_Token);
				System.out.println("refresh_token : " + refresh_Token);

				br.close();
				bw.close();
			} else {
				System.out.println("error");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		String[] arrTokens = new String[3];
		arrTokens[0] = access_Token;
		arrTokens[1] = refresh_Token;
		arrTokens[2] = id_token;

		return arrTokens;
	}

	// 카카오 유저 정보 받아오기
	public MemberDTO createKakaoUser(String token) throws IOException {
		// 유저 정보를 요청할 url
		String reqURL = "https://kapi.kakao.com/v2/user/me";

		// access_token을 통해 사용자 정보 조회
		URL url = new URL(reqURL);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();

		// post 요청을 위해 기본값 이 false인 setDoOutput을 true로 변경
		conn.setRequestMethod("POST");
		conn.setDoOutput(true);
		// 전송할 header 작성, access_token전송
		conn.setRequestProperty("Authorization", "Bearer " + token);

		// 결과 코드가 200(성공) 일때
		int responseCode = conn.getResponseCode();
		System.out.println("responseCode : " + responseCode);
		
		if(responseCode==200) {
			// 요청을 통해 얻어온 JSON 타입의 Response 메시지 읽어오기
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line = "";
			String result = "";
			
			while((line=br.readLine())!=null) {
				result += line;
			}
			
			System.out.println("response body : " + result);
			
			// JSON 파싱
			// ObjectMapper를 사용하여 JSON 문자열을 JsonNode로 파싱
			ObjectMapper objectMapper = new ObjectMapper();
			JsonNode jsonNode = objectMapper.readTree(result);
			// 카카오 로그인 결과 값 아이디
			String id = jsonNode.get("id").toString();
			// 카카오에 사용자가 등록한 이름
			String nickname = jsonNode.get("properties").get("nickname").toString();
			System.out.println("id"+id);
		
			System.out.println("nickname"+nickname);
			// DB에 카카오 로그인한 기록이 없다면 카카오톡에서 전달한 유저 정보를 바탕으로
			// 객체 생성 후 DB에 저장후 DTO로 반환
			if(!mRepo.existsById(id)) {
				System.out.println("사용자가 db에 존재하지 않음");
				Member user = new Member();
				user.setId(id);
				user.setPassword("");
				user.setName(nickname);
				user.setNickname("");
				user.setEmail("");
				user.setPhone("");
				user.setBirth(new Timestamp(0));
				user.setRoleId("ROLE_MEMBER");
				user.setSocialTypeId("Kakao");
				// 현재 시각을 얻어옴
				LocalDateTime now = LocalDateTime.now();
				// 시간대 변환 (UTC에서 Asia/Seoul로)
				LocalDateTime koreaTime = now.atZone(ZoneId.of("UTC")).withZoneSameInstant(ZoneId.of("Asia/Seoul"))
						.toLocalDateTime();
				
				user.setSignupDate(Timestamp.valueOf(koreaTime));
				user.setEnabled(true);
				Member savedUser = mRepo.save(user);
				return mMapper.toDto(savedUser);
				
			}else {
				//DB에 카카오로 로그인된 정보가 있다면 token 생성해서 리턴
				System.out.println("사용자가 db에 존재");
				Member user = mRepo.findAllById(id);
				return mMapper.toDto(user);
				
			}
		}else {
			System.out.println("error");
			return new MemberDTO();
		}
	}
}
