package com.mingle.dto;

import java.time.Instant;
import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class ReplyDTO {
	
	private Long id;
	private String content;
	private Instant writeDate;
	
	//MyBatis 전용 요소
	private Long replyParentId;
	private String memberId;
	private Long postId;
	
	//JPA 전용 요소
	private MemberDTO writerInfo;
	private PostDTO parentPost;
	private List<ReplyDTO> childReplies;
	private ReplyDTO parentReply;
	private ReplyDTO adoptiveParentReply;
	
	// MyBatis 전용 생성자
	@Builder
	public ReplyDTO(Long id, String content, Long replyParentId, String memberId, Long postId) {
		super();
		this.id = id;
		this.content = content;
		this.replyParentId = replyParentId;
		this.memberId = memberId;
		this.postId = postId;
	}
	
	//JPA 전용 생성자
	
	@Builder
	public ReplyDTO(Long id, String content, Instant writeDate, MemberDTO writerInfo, PostDTO parentPost,
			List<ReplyDTO> childReplies, ReplyDTO parentReply, ReplyDTO adoptiveParentReply) {
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
