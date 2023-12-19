package com.mingle.domain.repositories;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.mingle.domain.entites.Reply;

public interface ReplyRepository extends JpaRepository<Reply, Long>{
	
	@Query("select r from Reply r "
		      + "left join fetch r.member "
		      + "left join fetch r.parentReply "
		      + "left join fetch r.childrenReplies "
		      + "where r.parentReply is null")
	Set<Reply> findReplyHasNullParent();


}
