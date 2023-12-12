package com.mingle.domain.entites;

import java.sql.Timestamp;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

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

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Reply {
	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "content", nullable = false)
	private String content;
	
	@Column(name = "write_date", nullable = false)
	@CreationTimestamp
	private Timestamp writeDate;
	
	@ManyToOne
	@JoinColumn(name = "member_id", referencedColumnName = "id", nullable = false)
	private Member writerInfo;

	@ManyToOne
	@JoinColumn(name = "post_id", referencedColumnName = "id", nullable = false)
	private Post parentPost;
	
	@OneToMany(mappedBy = "parentReply")
	private List<Reply> childReplies;
	
	@ManyToOne
	@JoinColumn(name = "reply_parent_id",referencedColumnName = "id", nullable = true)
	private Reply parentReply;
	
	@ManyToOne
	@JoinColumn(name = "reply_adoptive_parent_id",referencedColumnName = "id", nullable = true)
	private Reply adoptiveParentReply;
	
	@Builder
	public Reply(Long id, String content, Timestamp writeDate, Member writerInfo, Post parentPost,
			List<Reply> childReplies, Reply parentReply, Reply adoptiveParentReply) {
		super();
		this.id = id;
		this.content = content;
		this.writeDate = writeDate;
		this.writerInfo = writerInfo;
		this.parentPost = parentPost;
		this.childReplies = childReplies;
		this.parentReply = parentReply;
		this.adoptiveParentReply = adoptiveParentReply;
	}
	
	

}