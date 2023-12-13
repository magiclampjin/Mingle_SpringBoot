package com.mingle.dto;

import java.sql.Timestamp;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class NewVideoDTO {
	
	private String id;
	private String title;
	private String description;
	private String url;
	private String thumbnail;
	private Long likeCount;
	private Long viewCount;
	private Timestamp recordedDate;
	
	@Builder
	public NewVideoDTO(String id, String title, String description, String url, String thumbnail, Long likeCount,
			Long viewCount, Timestamp recordedDate) {
		super();
		this.id = id;
		this.title = title;
		this.description = description;
		this.url = url;
		this.thumbnail = thumbnail;
		this.likeCount = likeCount;
		this.viewCount = viewCount;
		this.recordedDate = recordedDate;
	}
	
	
	
}
