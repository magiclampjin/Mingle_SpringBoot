package com.mingle.domain.entites;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
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
	Long partyRegistrationId;
	
	@Column(name="member_id")
	String memberId;
	
	@Column(name="party_report_category_id")
	String partyReportCategoryId;
	
	@OneToOne
	@JoinColumn(name = "report_id", referencedColumnName = "id", insertable = false, updatable = false)
	private Report report;
}
