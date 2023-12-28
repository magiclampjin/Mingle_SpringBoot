package com.mingle.domain.entites;

import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
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
public class PartyInformationForMain {
	@Id
	@Column(name="id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column(name="service_id")
	private long serviceId;
	@Column(name="people_count")
	private long peopleCount;
	@Column(name="start_date")
	private Instant startDate;
	@Column(name="month_count")
	private long monthCount;
	@Column(name="calculation_date")
	private String calculationDate;
//	@Column(name="login_id")
//	private String loginId;
//	@Column(name="login_pw")
//	private String loginPw;
	@Column(name="price")
	private Long price;
	@Column(name="max_people_count")
	private Long maxPeopleCount;
	@Column(name="english_name")
	private String englishName;
}
