package com.mingle.domain.entites;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.Period;

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
public class PartyInformation {
	@Id
	@Column(name="id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column(name="service_id")
	private long serviceId;
	@Column(name="people_count")
	private long peopleCount;
	@Column(name="start_date")
	private Timestamp startDate;
	@Column(name="month_count")
	private long monthCount;
	@Column(name="calculation_date")
	private String calculationDate;
	@Column(name="login_id")
	private String loginId;
	@Column(name="login_pw")
	private String loginPw;
}
