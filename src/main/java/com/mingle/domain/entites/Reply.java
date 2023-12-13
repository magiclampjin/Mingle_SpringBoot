package com.mingle.domain.entites;

import java.sql.Timestamp;
import java.util.List;

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
	
	@Column(name = "reply_parent_id", nullable = true)
	private Long replyParentId;
	
	@Column(name = "reply_adoptive_parent_id", nullable = true)
	private Long replyAdoptiveParentId;
	
	@Builder
	public Reply(Long id, String content, Timestamp writeDate, Long postId, Member member, Long replyParentId,
			Long replyAdoptiveParentId) {
		super();
		this.id = id;
		this.content = content;
		this.writeDate = writeDate;
		this.postId = postId;
		this.member = member;
		this.replyParentId = replyParentId;
		this.replyAdoptiveParentId = replyAdoptiveParentId;
	}
	
	
	

}