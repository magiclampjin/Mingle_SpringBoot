package com.mingle.domain.entites;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
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
public class Report {
	@Id
	@Column(name="id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(name="member_reporter_id")
	private String memberReporterId;
	
	@Column(name="content")
	private String content;
	
	@Column(name="report_date")
	private Timestamp reportDate;
	
	@Column(name="is_process")
	private boolean isProcess;

	// reportDate를 yyyy-MM-dd 형식의 문자열로 반환하는 Getter 메서드
	public String getFormattedReportDate() {
	    if (reportDate == null) {
	        return null;
	    }

	    LocalDateTime localDateTime = reportDate.toLocalDateTime();
	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	    return localDateTime.format(formatter);
	}

}
