package com.mingle.domain.entites;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReportParty {
	@Id
	@Column(name="report_id")
	Long reportId;
	
	@Column(name="party_registration_id")
	Long partyRegistratinoId;
	
	@Column(name="member_id")
	Long memberId;
	
	@Column(name="party_report_category_id")
	String partyReportCategoryId;
}
