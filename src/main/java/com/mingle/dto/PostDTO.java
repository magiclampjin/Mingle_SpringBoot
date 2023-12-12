package com.mingle.dto;

import java.time.Instant;
import java.util.Set;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@NoArgsConstructor
@ToString
public class PostDTO {
	
	// 공통 요소
	private Long id;
	private String title;
	private String contents;
	private Instant writeDate;
	private Long viewCount;
	private Boolean isNotice;
	private Boolean isFix;
	private Long reviewGrade;
	
	//MyBatis 전용
	private String memberId;
	
	//JPA 전용
	private MemberDTO member;
	private Set<ReplyDTO> replies;
	
	//MyBatis 전용 생성자
	@Builder
	public PostDTO(Long id, String title, String contents, Instant writeDate, Long viewCount, Boolean isNotice,
			Boolean isFix, Long reviewGrade, String memberId) {
		super();
		this.id = id;
		this.title = title;
		this.contents = contents;
		this.writeDate = writeDate;
		this.viewCount = viewCount;
		this.isNotice = isNotice;
		this.isFix = isFix;
		this.reviewGrade = reviewGrade;
		this.memberId = memberId;
	}

	//JPA 전용 생성자
	@Builder
	public PostDTO(Long id, String title, String contents, Instant writeDate, Long viewCount, Boolean isNotice,
			Boolean isFix, MemberDTO member, Set<ReplyDTO> replies) {
		super();
		this.id = id;
		this.title = title;
		this.contents = contents;
		this.writeDate = writeDate;
		this.viewCount = viewCount;
		this.isNotice = isNotice;
		this.isFix = isFix;
		this.member = member;
		this.replies = replies;
	}
	
	@Builder
	public PostDTO(Long id, String title, String contents, Instant writeDate, Long viewCount, Boolean isNotice,
			Boolean isFix, Long reviewGrade, MemberDTO member, Set<ReplyDTO> replies) {
		super();
		this.id = id;
		this.title = title;
		this.contents = contents;
		this.writeDate = writeDate;
		this.viewCount = viewCount;
		this.isNotice = isNotice;
		this.isFix = isFix;
		this.reviewGrade = reviewGrade;
		this.member = member;
		this.replies = replies;
	}
	
	
	
	
	
	
	
}
