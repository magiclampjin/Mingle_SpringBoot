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
	private String member_recommeder_id;
	private String social_type_id;
	private Timestamp signup_date;
	private boolean enabled;
	private String role_id;
}
