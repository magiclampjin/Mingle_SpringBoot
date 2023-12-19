package com.mingle.dto;

import java.sql.Timestamp;

import com.mingle.domain.entites.Member;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class ChatMessageDTO {
	
	private Long id;
	private String chatRoomId;
	private Member member;
	private String chatMessage;
	private Timestamp writeDate;
	
	@Builder
	public ChatMessageDTO(Long id, String chatRoomId, Member member, String chatMessage, Timestamp writeDate) {
		super();
		this.id = id;
		this.chatRoomId = chatRoomId;
		this.member = member;
		this.chatMessage = chatMessage;
		this.writeDate = writeDate;
	}
	
	
}
