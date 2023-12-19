package com.mingle.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PostReactionsDTO {
	
	private Long id;
	private Long postId;
	private String memberId;
	private Long vote;
	
	@Builder
	public PostReactionsDTO(Long id, Long postId, String memberId, Long vote) {
		super();
		this.id = id;
		this.postId = postId;
		this.memberId = memberId;
		this.vote = vote;
	}
	
}
