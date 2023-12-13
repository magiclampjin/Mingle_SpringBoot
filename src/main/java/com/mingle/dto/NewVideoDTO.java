package com.mingle.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class NewVideoDTO {

	private String title;
	private String description;
	private String videoId;
	private Long viewCount;
	private Long likeCount;
	private String url;
	private String thumbnail;
	
	
	@Builder
	public NewVideoDTO(String title, String description, String videoId, Long viewCount, Long likeCount, String url,
			String thumbnail) {
		super();
		this.title = title;
		this.description = description;
		this.videoId = videoId;
		this.viewCount = viewCount;
		this.likeCount = likeCount;
		this.url = url;
		this.thumbnail = thumbnail;
	}

	

	
}
