package com.mingle.domain.entites;

import java.sql.Timestamp;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "new_video")
@Setter
@Getter
@NoArgsConstructor
public class NewVideo {
	
	@Id
	@Column(name = "id")
	private String id;
	
	@Column(name = "title", nullable = false)
	private String title;
	
	@Column(name = "description", nullable = false)
	private String description;
	
	@Column(name = "url", nullable = false)
	private String url;
	
	@Column(name = "thumbnail", nullable = false)
	private String thumbnail;
	
	@Column(name = "like_count", nullable = false)
	private Long likeCount;
	
	@Column(name = "view_count", nullable = false)
	private Long viewCount;
	
	@Column(name = "recorded_date", nullable = false)
	@CreationTimestamp
	private Timestamp recordedDate;
	
	@Column(name = "ott", nullable = false)
	private String ott;
	
	@Builder
	public NewVideo(String id, String title, String description, String url, String thumbnail, Long likeCount,
			Long viewCount, Timestamp recordedDate, String ott) {
		super();
		this.id = id;
		this.title = title;
		this.description = description;
		this.url = url;
		this.thumbnail = thumbnail;
		this.likeCount = likeCount;
		this.viewCount = viewCount;
		this.recordedDate = recordedDate;
		this.ott = ott;
	}
	
}
