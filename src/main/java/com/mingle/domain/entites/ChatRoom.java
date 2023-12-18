package com.mingle.domain.entites;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "chat_room")
@Setter
@Getter
@NoArgsConstructor
@ToString	
public class ChatRoom {
	
	@Id
	@Column(name = "id")
	private String id;
	
	@Column(name = "party_information_id")
	private Long partyInformationId;
	
	@Builder
	public ChatRoom(String id, Long partyInformationId) {
		super();
		this.id = id;
		this.partyInformationId = partyInformationId;
	}

}
