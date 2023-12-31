package com.mingle.dto;

import com.mingle.domain.entites.Reply;
import com.mingle.domain.entites.Report;

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
public class ReportReplyDTO {
	private Long reportId;
	private Long replyId;
	private Report report;
	private Reply reply;
}
