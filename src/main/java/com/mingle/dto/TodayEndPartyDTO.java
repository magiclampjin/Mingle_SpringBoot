package com.mingle.dto;

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
public class TodayEndPartyDTO {
	private Long pmId;
	private Long partyRegistrationId;
	private String memberId;
	private Long mingleMoney;
	private Long deposit;
	private Long currPartyMemberCnt;
	private Long serviceId;
}
