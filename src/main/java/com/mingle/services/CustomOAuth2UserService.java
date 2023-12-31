package com.mingle.services;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.mingle.auth.dto.OAuthAttributes;
//import com.mingle.auth.dto.OAuthAttributes;
import com.mingle.domain.entites.Member;
import com.mingle.domain.repositories.MemberRepository;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

	private final MemberRepository mRepo;
	private final HttpSession session;
	
	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		System.out.println("스프링 oauth2");
		OAuth2UserService<OAuth2UserRequest,OAuth2User> delegate = new DefaultOAuth2UserService();
		OAuth2User oauth2User = delegate.loadUser(userRequest);
		
		String registrationId = userRequest.getClientRegistration().getRegistrationId();
		String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();
		
		OAuthAttributes attributes =  OAuthAttributes.of(registrationId, userNameAttributeName, oauth2User.getAttributes());
		
		Member user=saveOrUpdate(attributes);
		return null;
	}
	
	private Member saveOrUpdate(OAuthAttributes attributes) {
		Member user = mRepo.findByEmail(attributes.getEmail()).map(entity->entity.update(attributes.getName())).orElse(attributes.toEntity());
		return mRepo.save(user);
	}

}
