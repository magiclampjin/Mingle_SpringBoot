package com.mingle.domain.entites;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "free_post_with_rownum")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class FreePostView {
	
	@Id
	@Column(name = "id",updatable = false, insertable = false)
	private Long id;
	
	@Column(name = "rownum",updatable = false, insertable = false)
	private Long rownum;
	
	@Column(name = "member_id",updatable = false, insertable = false)
	private String memberId;
	
	@Column(name = "member_nickname",updatable = false, insertable = false)
	private String memberNickname;
	
	@Column(name = "title",updatable = false, insertable = false)
	private String title;
	
	@Column(name = "content",updatable = false, insertable = false)
	private String content;
	
	@Column(name = "write_date",updatable = false, insertable = false)
	private Timestamp writeDate;
	
	@Column(name = "view_count",updatable = false, insertable = false)
	private Long viewCount;
	
	@Column(name = "is_notice",updatable = false, insertable = false)
	private Boolean isNotice;
	
	@Column(name = "is_fix",updatable = false, insertable = false)
	private Boolean isFix;
	
	@Column(name = "review_grade",updatable = false, insertable = false)
	private Long reviewGrade;
	
	@Column(name = "total_votes",updatable = false, insertable = false)
	private Long totalVotes;
	
	@Builder
	public FreePostView(Long id, Long rownum, String memberId, String memberNickname, String title, String content,
			Timestamp writeDate, Long viewCount, Boolean isNotice, Boolean isFix, Long reviewGrade, Long totalVotes) {
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
