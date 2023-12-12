package com.mingle.dto;

import java.time.Instant;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class MemberDTO {
	
	private String id;
	private String password;
	private String name;
	private String nickname;
	private String phone;
	private String email;
	private Instant birth;
	private String memberRecommenderId;
	private String socialTypeId;
	private Instant signup_date;
	private Boolean enabled;
	private String roleId;
	
	@Builder
	public MemberDTO(String id, String password, String name, String nickname, String phone, String email,
			Instant birth, String memberRecommenderId, String socialTypeId, Instant signup_date, Boolean enabled,
			String roleId) {
		super();
		this.id = id;
		this.password = password;
		this.name = name;
		this.nickname = nickname;
		this.phone = phone;
		this.email = email;
		this.birth = birth;
		this.memberRecommenderId = memberRecommenderId;
		this.socialTypeId = socialTypeId;
		this.signup_date = signup_date;
		this.enabled = enabled;
		this.roleId = roleId;
	}
	
	

}
