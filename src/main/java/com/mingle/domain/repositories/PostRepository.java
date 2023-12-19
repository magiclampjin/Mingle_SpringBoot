package com.mingle.domain.repositories;

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
	
}
