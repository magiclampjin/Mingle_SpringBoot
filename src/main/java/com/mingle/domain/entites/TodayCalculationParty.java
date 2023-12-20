package com.mingle.domain.entites;

import java.time.Instant;

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
public class TodayCalculationParty {
	
	@Id
	@Column(name="pm_id")
	private Long pmId;
	
	@Column(name="party_registration_id")
	private Long partyRegistrationId;
	
	@Column(name="member_id")
	private String memberId;
	
	@Column(name="is_party_manager")
	private boolean isPartyManager;
	
	@Column(name="start_date")
	private Instant startDate;
	
	@Column(name="month_count")
	private Long monthCount;
	
	@Column(name="calculation_date")
	private Long calculationDate;
	
	@Column(name="price")
	private Long price;
	
	@Column(name="commission")
	private Long commission;
	
	@Column(name="max_people_count")
	private Long maxPeopleCount;
	
	@Column(name="mingle_money")
	private Long mingleMoney;
	
	@Column(name="deposit")
	private Long deposit;
	
	@Column(name="month_fee")
	private Long monthFee;
}
