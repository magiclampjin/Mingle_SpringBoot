package com.mingle.domain.entites;

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
	
	@Column(name="password", nullable = false)
	private String password;
	
	@Column(name="name", nullable = false)
	private String name;
	
	// 리스트로 가져와야함
	@Column(name="role")
	private String role;

	@Column(name="enabled")
	private boolean enabled;
}
