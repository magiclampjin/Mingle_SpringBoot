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
	private String memberId;
	
	private List<MultipartFile> files;
	
	
	
	@Builder
	public UploadPostDTO(Long id, String title, String content, Instant writeDate, Long viewCount, Boolean isNotice,
	        Boolean isFix, Long reviewGrade, String memberId, List<MultipartFile> files) {
	    this.id = id;
	    this.title = title;
	    this.content = content;
	    this.writeDate = writeDate != null ? writeDate : Instant.now(); // 현재 시간으로 초기화
	    this.viewCount = viewCount != null ? viewCount : 0L; // 기본값으로 0 설정
	    this.isNotice = isNotice;
	    this.isFix = isFix;
	    this.reviewGrade = reviewGrade != null ? reviewGrade : 0L; // 기본값으로 0 설정
	    this.memberId = memberId;
	    this.files = files;
	} 
 
	

}
