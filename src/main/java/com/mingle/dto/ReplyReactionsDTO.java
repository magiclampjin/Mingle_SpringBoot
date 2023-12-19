package com.mingle.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ReplyReactionsDTO {
	
	private Long id;
	private Long replyId;
	private String memberId;
	private Long vote;
	
	@Builder
	public ReplyReactionsDTO(Long id, Long replyId, String memberId, Long vote) {
		super();
		this.id = id;
		this.replyId = replyId;
		this.memberId = memberId;
		this.vote = vote;
	}
	
	
	
	

}
