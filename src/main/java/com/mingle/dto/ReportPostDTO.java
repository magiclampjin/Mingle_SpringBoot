package com.mingle.dto;

import com.mingle.domain.entites.Post;
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
public class ReportPostDTO {
	private Long reportId;
	private Long postId;
	private ReportDTO report;
	private PostDTO post;
}
