package com.mingle.domain.repositories;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mingle.domain.entites.PartyReply;

public interface PartyReplyRepository extends JpaRepository<PartyReply, Long>{

	// 파티별 파티댓글 가져오기
	@Query("select pr from PartyReply pr "
		      + "left join fetch pr.member "
		      + "left join fetch pr.parentPartyReply "
		      + "left join fetch pr.childrenPartyReplies "
		      + "where pr.partyRegistrationId = :partyRegistrationId")
	Set<PartyReply> findFirstPartyReply(@Param("partyRegistrationId") Long partyRegistrationId);
	
	// id를 기반으로 파티댓글 찾기
	@Query("select pr from PartyReply pr "
		      + "left join fetch pr.member "
		      + "left join fetch pr.parentPartyReply "
		      + "left join fetch pr.childrenPartyReplies "
		      + "where pr.id = :id")
	PartyReply findPartyReplyById(@Param(value = "id") Long id);
	
	// 파티 댓글 수정
	@Modifying
	@Query("update PartyReply pr set pr.content = :content where pr.id = :id")
	void updatePartyReplyById(@Param(value = "id") Long id, @Param(value = "content") String content);
	
	// 파티 댓글 삭제
	@Modifying
	@Query("delete from PartyReply pr where pr.id = :id")
	void deletePartyReplyById(@Param(value = "id") Long id);
	
	// 파티 삭제 시 파티 댓글 리스트 삭제.
	@Modifying
	@Query("delete from PartyReply pr where pr.partyRegistrationId = :partyRegistrationId")
	void deletePartyReplyByPartyRegistrationId(@Param(value = "partyRegistrationId") Long partyRegistrationId);
	
	
	
	
	}


