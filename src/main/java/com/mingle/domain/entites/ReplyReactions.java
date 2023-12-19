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
@Table(name="reply_reactions")
@Setter
@Getter
@NoArgsConstructor
public class ReplyReactions {
	
	@Id
	@Column(name="id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="reply_id")
	private Long replyId;
	
	@Column(name="member_id")
	private String memberId;
	
	@Column(name="vote")
	private Long vote;
	
	
	@Builder
	public ReplyReactions(Long id, Long replyId, String memberId, Long vote) {
		super();
		this.id = id;
		this.replyId = replyId;
		this.memberId = memberId;
		this.vote = vote;
	}
	
	
	

}
