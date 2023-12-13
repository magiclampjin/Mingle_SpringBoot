package com.mingle.dto;

import java.time.Instant;

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
public class ReportDTO {
	private Long id;
	private String memberReporterId;
	private String content;
	private Instant reportDate;
	private boolean isProcess;
}
