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
public class PartyInformationForMainDTO {
	private long id;
	private long serviceId;
	private long peopleCount;
	private Instant startDate;
	private long monthCount;
	private long calculationDate;
//	private String loginId;
//	private String loginPw;
	private Long price;
	private Long maxPeopleCount;
	private String englishName;
}
