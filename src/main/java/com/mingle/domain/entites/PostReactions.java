package com.mingle.domain.entites;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="post_reactions")
@Getter
@Setter
@NoArgsConstructor
public class PostReactions {
	
	@Id
	@Column(name="id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="post_id",nullable = false)
	private Long postId;
	
	@Column(name="member_id",nullable = false)
	private String memberId;
	
	@Column(name="vote", nullable = false)
	private Long vote;
	
	@Builder
	public PostReactions(Long id, Long postId, String memberId, Long vote) {
		super();
		this.id = id;
		this.postId = postId;
		this.memberId = memberId;
		this.vote = vote;
	}
	
	
}
