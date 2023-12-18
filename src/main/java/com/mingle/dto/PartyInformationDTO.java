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
public class PartyInformationDTO {
	private long id;
	private long serviceId;
	private long peopleCount;
	private Instant startDate;
	private long MonthCount;
	private String content;
	private String loginId;
	private String loginPw;
}
