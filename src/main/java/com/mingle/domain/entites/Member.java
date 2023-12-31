package com.mingle.domain.entites;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
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
	@Column(name = "id")
	private String id;

	@Column(name = "password", nullable = false)
	private String password;

	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "nickname")
	private String nickname;

	@Column(name = "phone")
	private String phone;

	@Column(name = "email")
	private String email;

	@Column(name = "birth")
	private Timestamp birth;

	@Column(name = "member_recommender_id")
	private String memberRecommenderId;

	@Column(name = "social_type_id")
	private String socialTypeId;

	@Column(name = "signup_date")
	private Timestamp signupDate;

	@Column(name = "enabled")
	private boolean enabled;

	@Column(name = "role_id")
	private String roleId;

	@Column(name = "mingle_money")
	private Long mingleMoney;
	
	@Column(name = "provider")
	private String provider;
	
	@Builder(builderClassName = "UserDetailRegister", builderMethodName = "userDetailRegister")
    public Member(String username, String password, String email, String role) {
        this.id = username;
        this.password = password;
        this.email = email;
        this.roleId = role;
    }
	
	@Builder(builderClassName = "OAuth2Register", builderMethodName = "oauth2Register")
    public Member(String username, String password, String email, String role, String social, String provider) {
        this.id = username;
        this.password = password;
        this.email = email;
        this.roleId = role;
        this.socialTypeId=social;
        this.provider = provider;
    }
	
	///
	@Builder
	public Member(String name, String email, String roleId) {
		this.name=name;
		this.email=email;
		this.roleId=roleId;
	}
	
	public Member update(String name){
        this.name = name;

        return this;
    }

}
