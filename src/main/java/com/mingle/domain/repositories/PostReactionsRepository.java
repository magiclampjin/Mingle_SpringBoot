package com.mingle.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mingle.domain.entites.PostReactions;

public interface PostReactionsRepository extends JpaRepository<PostReactions, Long>{
	
	
	@Query("select case when count(pr) > 0 then true else false end from PostReactions pr where pr.postId = :postId and pr.memberId = :memberId")
	public Boolean isVoted(@Param("postId") Long postId, @Param("memberId") String memberId);
	
    @Query("SELECT SUM(pr.vote) FROM PostReactions pr WHERE pr.postId = :postId")
    public Long sumVotesByPostId(@Param("postId") Long postId);
	
	
	
}
