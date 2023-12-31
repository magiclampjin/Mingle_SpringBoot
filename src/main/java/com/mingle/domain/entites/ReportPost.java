package com.mingle.domain.entites;

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
public class ReportPost {
	@Id
	@Column(name="report_id")
	private Long reportId;
	
	@Column(name="post_id")
	private Long postId;
	
	@OneToOne
	@JoinColumn(name = "report_id", referencedColumnName = "id", insertable = false, updatable = false) // 읽기 전용
	private Report report;
	
	@OneToOne
	@JoinColumn(name = "post_id", referencedColumnName = "id", insertable = false, updatable = false) // 읽기 전용
	private Post post;
}
