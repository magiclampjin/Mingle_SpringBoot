package com.mingle.domain.entites;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
public class PaymentAccount {
	
	@Id
	@Column(name="id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="member_id", nullable=false)
	private String memberId;
	
	@Column(name="bank_id", nullable=false)
	private String bankId;
	
	@Column(name="account_number")
	private String accountNumber;
	
	@Column(name="account_holder")
	private String accountHolder;
	
	
}
