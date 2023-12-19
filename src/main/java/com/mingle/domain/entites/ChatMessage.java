package com.mingle.domain.entites;

import java.sql.Timestamp;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "chat_message")
@Setter
@Getter
@NoArgsConstructor
public class ChatMessage {
	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "chat_room_id")
	private String chatRoomId;
	
	@ManyToOne
	@JoinColumn(name = "member_send_id")
	private Member member;
	
	@Column(name = "chat_message")
	private String chatMessage;
	
	@Column(name = "write_date")
	@CreationTimestamp
	private Timestamp writeDate;
	
	@Builder
	public ChatMessage(Long id, String chatRoomId, Member member, String chatMessage, Timestamp writeDate) {
		super();
		this.id = id;
		this.chatRoomId = chatRoomId;
		this.member = member;
		this.chatMessage = chatMessage;
		this.writeDate = writeDate;
	}
	
	

}
