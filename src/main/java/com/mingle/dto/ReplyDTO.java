package com.mingle.dto;

import java.time.Instant;
import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ReplyDTO {
	
	private Long id;
	private String content;
	private Instant writeDate;
	private Long postId;
	private Long replyParentId;
	private Long replyAdoptiveParentId;
	private MemberDTO member;
	
	@Builder
	public ReplyDTO(Long id, String content, Instant writeDate, Long postId, Long replyParentId,
			Long replyAdoptiveParentId, MemberDTO member) {
		super();
		this.id = id;
		this.content = content;
		this.writeDate = writeDate;
		this.postId = postId;
		this.replyParentId = replyParentId;
		this.replyAdoptiveParentId = replyAdoptiveParentId;
		this.member = member;
	}
	
	
	
	
	
	
	

	
	
	
}
