package com.mingle.domain.repositories;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mingle.domain.entites.Reply;

import jakarta.persistence.Tuple;

public interface ReplyRepository extends JpaRepository<Reply, Long>{
	
	@Query("select r from Reply r "
		      + "left join fetch r.member "
		      + "left join fetch r.parentReply "
		      + "left join fetch r.childrenReplies "
		      + "where r.parentReply is null")
	Set<Reply> findReplyHasNullParent();
	
	@Query("select r from Reply r "
		      + "left join fetch r.member "
		      + "left join fetch r.parentReply "
		      + "left join fetch r.childrenReplies "
		      + "where r.id = :id")
	Reply findReplyById(@Param(value = "id") Long id);
	
	@Query("select r, case when (SIZE(r.childrenReplies) > 0) then true else false end from Reply r "
		      + "left join fetch r.member "
		      + "left join fetch r.parentReply "
		      + "left join fetch r.childrenReplies "
		      + "where r.id = :id")
	Tuple findReplyAndHasChildrenById(@Param(value = "id") Long id);

}
