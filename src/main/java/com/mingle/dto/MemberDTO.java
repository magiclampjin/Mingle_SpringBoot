package com.mingle.dto;

import java.sql.Timestamp;
import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
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
	private Timestamp signupDate;
	private boolean enabled;
	private String roleId;
	private Long mingleMoney;

}