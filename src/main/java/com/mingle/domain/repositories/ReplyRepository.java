package com.mingle.domain.repositories;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mingle.domain.entites.Reply;

public interface ReplyRepository extends JpaRepository<Reply, Long>{
	
	// 최상위 댓글 가져오기
	@Query("select r from Reply r "
		      + "left join fetch r.member "
		      + "left join fetch r.parentReply "
		      + "left join fetch r.childrenReplies "
		      + "where r.parentReply is null")
	Set<Reply> findReplyHasNullParent();
	
	// id를 기반으로 댓글 찾기
	@Query("select r from Reply r "
		      + "left join fetch r.member "
		      + "left join fetch r.parentReply "
		      + "left join fetch r.childrenReplies "
		      + "where r.id = :id")
	Reply findReplyById(@Param(value = "id") Long id);
	
	
	@Modifying
	@Query("update Reply r set r.content = :content where r.id = :id")
	void updateReplyById(@Param(value = "id") Long id, @Param(value = "content") String content);
	
	@Modifying
	@Query("delete from Reply r where r.id = :id")
	void deleteReplyById(@Param(value = "id") Long id);
	
	@Modifying
	@Query("delete from Reply r where r.postId = :postId")
	void deleteReplyByPostId(@Param(value = "postId") Long postId);


}
