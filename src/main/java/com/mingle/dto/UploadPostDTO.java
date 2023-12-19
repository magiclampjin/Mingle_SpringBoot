package com.mingle.dto;

import java.time.Instant;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@NoArgsConstructor
@ToString
public class UploadPostDTO {
	private Long id;
	private String title;
	private String content;
	private Instant writeDate;
	private Long viewCount;
	private Boolean isNotice;
	private Boolean isFix;
	private Long reviewGrade;
	private MemberDTO member;
	
	private List<MultipartFile> files;
	
	@Builder
	public UploadPostDTO(Long id, String title, String content, Instant writeDate, Long viewCount, Boolean isNotice,
			Boolean isFix, Long reviewGrade, MemberDTO member, List<MultipartFile> files) {
		super();
		this.id = id;
		this.title = title;
		this.content = content;
		this.writeDate = writeDate;
		this.viewCount = viewCount;
		this.isNotice = isNotice;
		this.isFix = isFix;
		this.reviewGrade = reviewGrade;
		this.member = member;
		this.files = files;
	} 
	
	
	
}
