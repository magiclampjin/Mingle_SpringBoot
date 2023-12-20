package com.mingle.services;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.mingle.auth.GoogleUserInfo;
import com.mingle.auth.KakaoUserInfo;
import com.mingle.auth.OAuth2UserInfo;
import com.mingle.auth.PrincipalDetails;
import com.mingle.domain.entites.Member;
import com.mingle.domain.repositories.MemberRepository;

@Service
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {
	@Autowired
	private MemberRepository mRepo;

	// 비밀번호 인코딩
	@Autowired
	@Lazy
	private PasswordEncoder passwordEncoder;
	
	@Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		System.out.println("제발..");
		OAuth2User oAuth2User = super.loadUser(userRequest);
        
        OAuth2UserInfo oAuth2UserInfo = null;
        String provider = userRequest.getClientRegistration().getRegistrationId();   
        
        
        
        if(provider.equals("google")){
            oAuth2UserInfo = new GoogleUserInfo(oAuth2User.getAttributes());
        }
//        else if(provider.equals("naver")){
//            oAuth2UserInfo = new NaverUserInfo(oAuth2User.getAttributes());
//        }
        else if(provider.equals("kakao")){	//추가
        	System.out.println("zkzkdh");
            oAuth2UserInfo = new KakaoUserInfo(oAuth2User.getAttributes());
        }
        
        System.out.println(oAuth2UserInfo+"syo");
        String providerId = oAuth2UserInfo.getProviderId();	
        String username = provider+"_"+providerId;  			

        String uuid = UUID.randomUUID().toString().substring(0, 6);
        String password = passwordEncoder.encode("패스워드"+uuid); 

        String email = oAuth2UserInfo.getEmail();
        String role = "ROLE_MEMBER";

        Member byUsername = mRepo.findByIdAndProvider(username,provider);
        
        //DB에 없는 사용자라면 회원가입처리
        if(byUsername == null){
            byUsername = Member.oauth2Register()
                    .username(username).password(password).email(email).role(role)
                    .social(provider).provider(providerId)
                    .build();
            mRepo.save(byUsername);
        }

        return new PrincipalDetails(byUsername, oAuth2UserInfo);	
	}

}
