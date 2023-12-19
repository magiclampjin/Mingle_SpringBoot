package com.mingle.domain.entites;

import java.sql.Timestamp;
import java.util.Set;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
	
	@Column(name = "post_id", nullable = false)
	private Long postId;
	
	@ManyToOne
	@JoinColumn(name = "member_id", referencedColumnName = "id", nullable = false)
	private Member member;
	
	// 부모 댓글 클래스 정의
	@ManyToOne
	@JoinColumn(name = "reply_parent_id", referencedColumnName = "id",  nullable = true)
	private Reply parentReply;
	
	// 자식 댓글 클래스 정의
	@JsonIgnore
	@OneToMany(mappedBy = "parentReply")
	private Set<Reply> childrenReplies;
	
	
	@Column(name = "reply_adoptive_parent_id", nullable = true)
	private Long replyAdoptiveParentId;

	
	@Builder
	public Reply(Long id, String content, Timestamp writeDate, Long postId, Member member, Reply parentReply,
			Set<Reply> childrenReplies, Long replyAdoptiveParentId) {
		super();
		this.id = id;
		this.content = content;
		this.writeDate = writeDate;
		this.postId = postId;
		this.member = member;
		this.parentReply = parentReply;
		this.childrenReplies = childrenReplies;
		this.replyAdoptiveParentId = replyAdoptiveParentId;
	}

	
	
	
	
	
	

}