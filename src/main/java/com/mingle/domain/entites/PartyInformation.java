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
public class PartyInformation {
	@Id
	@Column(name="id")
	private long id;
	@Column(name="service_id")
	private long serviceId;
	@Column(name="people_count")
	private long peopleCount;
	@Column(name="start_date")
	private Instant startDate;
	@Column(name="Month_count")
	private long MonthCount;
	@Column(name="content")
	private String content;
	@Column(name="login_id")
	private String loginId;
	@Column(name="login_pw")
	private String loginPw;
}
