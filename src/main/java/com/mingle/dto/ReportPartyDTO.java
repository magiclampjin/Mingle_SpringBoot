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
public class ReportPartyDTO {
	private Long reportId;
	private Long partyRegistrationId;
	private String memberId;
	private String partyReportCategoryId;
}
