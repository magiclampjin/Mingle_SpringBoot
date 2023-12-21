package com.mingle.auth;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import com.mingle.domain.entites.Member;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class PrincipalDetails implements UserDetails, OAuth2User {
	private Member user;
	private OAuth2UserInfo oAuth2UserInfo;

	// UserDetails : Form 로그인 시 사용
	public PrincipalDetails(Member user) {
		this.user = user;
	}

	// OAuth2User : OAuth2 로그인 시 사용
	public PrincipalDetails(Member byUsername, OAuth2UserInfo oAuth2UserInfo) {
		this.user = byUsername;
		this.oAuth2UserInfo = oAuth2UserInfo;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Collection<GrantedAuthority> collect = new ArrayList<>();
		collect.add(new GrantedAuthority() {
			@Override
			public String getAuthority() {
				return user.getRoleId();
			}
		});
		return collect;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return user.getId();
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true; // 계정 만료 여부
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true; // 계정 잠금 여부
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true; // 비밀번호 만료일
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return user.isEnabled(); // 계정 활성화 여부
	}
	
	@Override
    public Map<String, Object> getAttributes() {
        //return attributes;
        return oAuth2UserInfo.getAttributes();
    }

    /**
     * OAuth2User 구현
     * @return
     */
    @Override
    public String getName() {
        //String sub = attributes.get("sub").toString();
        //return sub;
        return oAuth2UserInfo.getProviderId();
    }
}
