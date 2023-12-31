package com.mingle.dto;

import java.time.Instant;
import java.util.Set;

import com.mingle.domain.entites.PartyReply;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Builder
public class PartyReplyDTO {
	private Long id;
	private String content;
	private Instant writeDate;
	private Long partyRegistrationId;
	private MemberDTO member;
	private PartyReplyDTO parentPartyReply;
	private Set<PartyReply> childrenPartyReplies;
	private Long partyReplyAdoptiveParentId;
	private Boolean isSecret;

	
	@Builder
	public PartyReplyDTO(Long id, String content, Instant writeDate, Long partyRegistrationId, MemberDTO member,
			PartyReplyDTO parentPartyReply, Set<PartyReply> childrenPartyReplies, Long partyReplyAdoptiveParentId, Boolean isSecret) {
		super();
		this.id = id;
		this.content = content;
		this.writeDate = writeDate;
		this.partyRegistrationId = partyRegistrationId;
		this.member = member;
		this.parentPartyReply = parentPartyReply;
		this.childrenPartyReplies = childrenPartyReplies;
		this.partyReplyAdoptiveParentId = partyReplyAdoptiveParentId;
		this.isSecret = isSecret;
	}
	
	
	
}
