package com.mingle.domain.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import com.mingle.domain.entites.Post;

public interface PostRepository extends JpaRepository<Post, Long>{
	
	@Query("select p from Post p left join fetch p.replies")
	List<Post> findAllByFetchJoin();
}
