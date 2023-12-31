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
	
	// 파티 신고 insert용 생성자
	public ReportPartyDTO(Long reportId, Long partyRegistrationId, String memberId, String partyReportCategoryId) {
		super();
		this.reportId = reportId;
		this.partyRegistrationId = partyRegistrationId;
		this.memberId = memberId;
		this.partyReportCategoryId = partyReportCategoryId;
	}
}
