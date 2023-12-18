package com.mingle.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@NoArgsConstructor
@ToString
public class PaymentAccountDTO {

	private Long id;
	private String memberId;
	private String bankId;
	private String accountNumber;
	private String accountHolder;
}
