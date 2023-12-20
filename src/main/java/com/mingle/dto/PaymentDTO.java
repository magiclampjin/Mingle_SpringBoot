package com.mingle.dto;

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
public class PaymentDTO {
	private Long partyRegistrationId;
	private String memberId;
	private Instant date;
	private Long service_id;
	private String payment_type_id;
	private Long price;
}
