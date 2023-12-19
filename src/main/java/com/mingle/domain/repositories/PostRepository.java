package com.mingle.domain.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mingle.domain.entites.Post;

public interface PostRepository extends JpaRepository<Post, Long>{
	
	@Query("select p from Post p "
		      + "left join fetch p.member "
		      + "left join fetch p.files "
		      + "left join fetch p.replies r where r.parentReply is null and p.id = :id")
	Post findPostById(@Param("id") Long id);
	

	@Query("select p from Post p left join fetch p.member left join fetch p.replies left join fetch p.files where p.isNotice = true")
	List<Post> findAllByNoticePosts();
	
	// 고정 중인 공지글 오름차순으로 출력
	@Query("select p from Post p where isNotice = true and isFix = true order by writeDate desc")
	List<Post> selectByFixedNotice();
	
	// 고정 중이 아닌 공지글 오름차순으로 출력
	@Query("select p from Post p where isNotice = true and isFix = false order by writeDate desc")
	List<Post> selectByUnFixedNotice();
	
	// 아이디에 해당하는 Post
	Post findAllById(@Param("id") Long id);
}
