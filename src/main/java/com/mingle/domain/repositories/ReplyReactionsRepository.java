package com.mingle.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mingle.domain.entites.ReplyReactions;

public interface ReplyReactionsRepository extends JpaRepository<ReplyReactions, Long>{
	
	
	@Query("select case when count(rr) > 0 then true else false end from ReplyReactions rr where rr.replyId = :replyId and rr.memberId = :memberId")
	public Boolean isVoted(@Param("replyId") Long replyId, @Param("memberId") String memberId);
	
	

}
