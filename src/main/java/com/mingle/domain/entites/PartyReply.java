package com.mingle.domain.entites;

import java.sql.Timestamp;
import java.util.Set;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "party_reply")
@Setter
@Getter
@NoArgsConstructor
@Builder
public class PartyReply {
	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "content", nullable = false)
	private String content;
	
	@Column(name = "write_date", nullable = false)
	@CreationTimestamp
	private Timestamp writeDate;
	
    @Column(name = "party_registration_id", nullable = false)
    private Long partyRegistrationId;
	
	@ManyToOne
	@JoinColumn(name = "member_id", referencedColumnName = "id", nullable = false)
	private Member member;
	
	// 부모 댓글 클래스 정의
	@ManyToOne
	@JoinColumn(name = "party_reply_parent_id", referencedColumnName = "id",  nullable = true)
	private PartyReply parentPartyReply;
	
	// 자식 댓글 클래스 정의(순환 참조는 단 한번만 허용하도록 할것 )
	@JsonIgnore
	@OneToMany(mappedBy = "parentPartyReply")
	private Set<PartyReply> childrenPartyReplies;
	
	
	@Column(name = "party_reply_adoptive_parent_id", nullable = true)
	private Long partyReplyAdoptiveParentId;


	
	
	@Builder
	public PartyReply(Long id, String content, Timestamp writeDate, Long partyRegistrationId, Member member,
			PartyReply parentPartyReply, Set<PartyReply> childrenPartyReplies, Long partyReplyAdoptiveParentId) {
		super();
		this.id = id;
		this.content = content;
		this.writeDate = writeDate;
		this.partyRegistrationId = partyRegistrationId;
		this.member = member;
		this.parentPartyReply = parentPartyReply;
		this.childrenPartyReplies = childrenPartyReplies;
		this.partyReplyAdoptiveParentId = partyReplyAdoptiveParentId;
	}
	


}
