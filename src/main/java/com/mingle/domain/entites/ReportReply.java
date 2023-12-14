package com.mingle.domain.entites;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReportReply {
	@Id
	@Column(name="report_id")
	private Long reportId;
	
	@Column(name="reply_id")
	private Long replyId;
	
	@OneToOne
	@JoinColumn(name = "report_id", referencedColumnName = "id", insertable = false, updatable = false) // 읽기 전용
	private Report report;
	
	@OneToOne
	@JoinColumn(name = "reply_id", referencedColumnName = "id", insertable = false, updatable = false) // 읽기 전용
	private Reply reply;
}
