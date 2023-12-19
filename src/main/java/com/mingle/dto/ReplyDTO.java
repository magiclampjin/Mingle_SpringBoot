package com.mingle.dto;

import java.time.Instant;
import java.util.Set;

import com.mingle.domain.entites.Reply;

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
	private MemberDTO member;
	private Reply parentReply;
	private Set<Reply> childrenReplies;
	private Long replyAdoptiveParentId;
	
	@Builder
	public ReplyDTO(Long id, String content, Instant writeDate, Long postId, MemberDTO member, Reply parentReply,
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
