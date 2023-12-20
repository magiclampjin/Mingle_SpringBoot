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
public class TodayCalculationPartyDTO {
	private Long pmId;
	private Long partyRegistrationId;
	private String memberId;
	private boolean isPartyManager;
	private Instant startDate;
	private Long monthCount;
	private Long calculationDate;
	private Long price;
	private Long commission;
	private Long maxPeopleCount;
	private Long mingleMoney;
	private Long deposit;
	private Long monthFee;
}
