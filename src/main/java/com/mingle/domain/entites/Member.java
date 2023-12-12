package com.mingle.domain.entites;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Member {
	@Id
	@Column(name="id")
	private String id;
	
	@Column(name="password")
	private String password;
	
	@Column(name="name")
	private String name;
	
	@Column(name="nickname")
	private String nickname;
	
	@Column(name="phone")
	private String phone;
	
	@Column(name="email")
	private String email;
	
	@Column(name="birth")
	private Timestamp birth;
	
	@Column(name="member_recommeder_id")
	private String member_recommeder_id;
	
	@Column(name="social_type_id")
	private String social_type_id;
	
	@Column(name="signup_date")
	private Timestamp signup_date;

	@Column(name="enabled")
	private boolean enabled;
	
	@Column(name="role_id")
	private String role_id;

}
