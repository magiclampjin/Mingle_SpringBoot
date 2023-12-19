package com.mingle.dto;

import java.time.Instant;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class PostViewDTO {
	
	private Long id;
	private Long rownum;
	private String memberId;
	private String memberNickname;
	private String title;
	private String content;
	private Instant writeDate;
	private Long viewCount;
	private Boolean isNotice;
	private Boolean isFix;
	private Long reviewGrade;
	private Long totalVotes;
	
	@Builder
	public PostViewDTO(Long id, Long rownum, String memberId, String memberNickname, String title, String content,
			Instant writeDate, Long viewCount, Boolean isNotice, Boolean isFix, Long reviewGrade, Long totalVotes) {
		super();
		this.id = id;
		this.rownum = rownum;
		this.memberId = memberId;
		this.memberNickname = memberNickname;
		this.title = title;
		this.content = content;
		this.writeDate = writeDate;
		this.viewCount = viewCount;
		this.isNotice = isNotice;
		this.isFix = isFix;
		this.reviewGrade = reviewGrade;
		this.totalVotes = totalVotes;
	}
	
	

}
