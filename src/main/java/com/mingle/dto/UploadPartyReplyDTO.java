package com.mingle.dto;

import java.time.Instant;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Builder
public class UploadPartyReplyDTO {
	
	private Long id;
	private String content;
	private Instant writeDate;
	private Long partyRegistrationId;
	private String memberId;
	private Long partyReplyParentId;
	private Long partyReplyAdoptiveParentId;
	
	@Builder
	public UploadPartyReplyDTO(Long id, String content, Instant writeDate, Long partyRegistrationId, String memberId,
			Long partyReplyParentId, Long partyReplyAdoptiveParentId) {
		super();
		this.id = id;
		this.content = content;
		this.writeDate = writeDate;
		this.partyRegistrationId = partyRegistrationId;
		this.memberId = memberId;
		this.partyReplyParentId = partyReplyParentId;
		this.partyReplyAdoptiveParentId = partyReplyAdoptiveParentId;
	}
	
	
	
	

}
