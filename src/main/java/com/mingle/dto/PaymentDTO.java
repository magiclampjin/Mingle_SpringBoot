package com.mingle.dto;

import java.time.Instant;

import com.mingle.domain.entites.Service;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@NoArgsConstructor
@ToString
public class PaymentDTO {
	
	private Long partyRegistrationId;
	private String memberId;
	private Instant date;
	private Long serviceId;
	private String paymentTypeId;
	private Long price;
	private Long usedMingleMoney;
	
	private Service service;
	
}
