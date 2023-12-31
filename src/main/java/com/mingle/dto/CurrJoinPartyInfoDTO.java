package com.mingle.dto;

import java.sql.Timestamp;
import java.time.Instant;

import jakarta.persistence.Column;
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
public class CurrJoinPartyInfoDTO {

	private Long id;
	private Long partyRegistrationId;
	private String memberId;
	private boolean isPartyManager;
	private Long serviceId;
	private Timestamp startDate;
	private Long monthCount;
	private Long calculationDate;
	private String name;
	private Long price;
	private Long commission;
	private Long maxPeopleCount;
	private String englishName;
	private String plan;
	private String loginId;	
	private String loginPw;
	private int memberCnt;
	private String memberNicknames;
	private String managerNickname;
	private boolean isExpired;
	
	
	public CurrJoinPartyInfoDTO(Long id, String memberId, boolean isPartyManager, Timestamp startDate, String name, String englishName, String plan) {
		super();
		this.id = id;
		this.memberId = memberId;
		this.isPartyManager = isPartyManager;
		this.startDate = startDate;
		this.name = name;
		this.englishName = englishName;
		this.plan = plan;
	}
	
}