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
public class TodayEndParty {
	@Id
	@Column(name="pm_id")
	private Long pmId;
	
	@Column(name="party_registration_id")
	private Long partyRegistrationId;
	
	@Column(name="member_id")
	private String memberId;

	@Column(name="mingle_money")
	private Long mingleMoney;
	
	@Column(name="deposit")
	private Long deposit;
	
	@Column(name="curr_party_member_cnt")
	private Long currPartyMemberCnt;
	
	@Column(name="service_id")
	private Long serviceId;
}
