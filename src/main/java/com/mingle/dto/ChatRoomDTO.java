package com.mingle.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@NoArgsConstructor
@ToString
public class ChatRoomDTO {
	
	private String id;
	private Long partyInformationId;
	
	@Builder
	public ChatRoomDTO(String id, Long partyInformationId) {
		super();
		this.id = id;
		this.partyInformationId = partyInformationId;
	}
	
	

}
