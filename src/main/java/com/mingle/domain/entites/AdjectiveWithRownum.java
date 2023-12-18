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
public class AdjectiveWithRownum {
	@Column(name="num")
	private Long num;
	
	@Id
	@Column(name="id")
	private Long id;
	
	@Column(name="adjective")
	private String adjective;
}
