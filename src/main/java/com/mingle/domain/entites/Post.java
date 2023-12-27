package com.mingle.domain.entites;

import java.sql.Timestamp;
import java.util.Set;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Post {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "title", nullable = false)
	private String title;

	@Column(name = "content", nullable = false)
	private String content;
	
	@Column(name = "write_date", nullable = false)
	@CreationTimestamp
	private Timestamp writeDate;
	
	@Column(name = "view_count", nullable = false)
	private Long viewCount;
	
	@Column(name = "is_notice", nullable = false)
	private Boolean isNotice;
	
	@Column(name = "is_fix", nullable = false)
	private Boolean isFix;
	
	@Column(name = "review_grade", nullable = true)
	private Long reviewGrade;
	
	@ManyToOne
	@JoinColumn(name = "member_id", referencedColumnName = "id")
	private Member member;
	
	@OneToMany
	@JoinColumn(name="post_id", referencedColumnName = "id")
	private Set<Reply> replies;
	
	@OneToMany
	@JoinColumn(name="post_id", referencedColumnName = "id")
	private Set<PostFile> files; 
	
	
	@Builder
	public Post(Long id, String title, String content, Timestamp writeDate, Long viewCount, Boolean isNotice,
			Boolean isFix, Long reviewGrade, Member member, Set<Reply> replies, Set<PostFile> files) {
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
		this.replies = replies;
		this.files = files;
	}
	
	
	

}
