package com.mingle.dto;

import com.mingle.domain.entites.Report;

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
	private Report report;
}
