package com.mingle.domain.entites;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CurrJoinPartyInfo {
	@Id
	@Column(name="id")
	private Long id;
	@Column(name="party_registration_id")
	private Long partyRegistrationId;	
	@Column(name="member_id")
	private String memberId;	
	@Column(name="is_party_manager")
	private boolean isPartyManager;	
	@Column(name="service_id")
	private Long serviceId;	
	@Column(name="start_date")
	private Timestamp startDate;
	@Column(name="month_count")
	private Long monthCount;
	@Column(name="calculation_date")
	private Long calculationDate;
	@Column(name="name")
	private String name;
	@Column(name="price")
	private Long price;
	@Column(name="commission")
	private Long commission;
	@Column(name="max_people_count")
	private Long maxPeopleCount;
	@Column(name="english_name")
	private String englishName;
	@Column(name="plan")
	private String plan;
	@Column(name="login_id")
	private String loginId;	
	@Column(name="login_pw")
	private String loginPw;
	@Column(name="member_cnt")
	private int memberCnt;
	@Column(name="member_nicknames")
	private String memberNicknames;
	@Column(name="manager_nickname")
	private String managerNickname;
	@Column(name="is_expired")
	private boolean isExpired;
	
	public CurrJoinPartyInfo(Long id, String memberId, boolean isPartyManager, Timestamp startDate, String name, String englishName, String plan) {
		super();
		this.id = id;
		this.memberId = memberId;
		this.isPartyManager = isPartyManager;
		this.startDate = startDate;
		this.name = name;
		this.englishName = englishName;
		this.plan = plan;
	}
	
	public CurrJoinPartyInfo(Long id, String memberId, boolean isPartyManager, Timestamp startDate, String name, String englishName, String plan, Long monthCount) {
		super();
		this.id = id;
		this.memberId = memberId;
		this.isPartyManager = isPartyManager;
		this.startDate = startDate;
		this.name = name;
		this.englishName = englishName;
		this.plan = plan;
		this.monthCount = monthCount;
	}
}
