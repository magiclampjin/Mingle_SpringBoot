package com.mingle.dto;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

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
	private Boolean isProcess;
	
	
	// reportDate를 yyyy-MM-dd 형식의 문자열로 반환하는 Getter 메서드
    public String getFormattedReportDate() {
        if (reportDate == null) {
            return null;
        }

        LocalDateTime localDateTime = LocalDateTime.ofInstant(reportDate, ZoneId.systemDefault());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        return localDateTime.format(formatter);
    }
}
