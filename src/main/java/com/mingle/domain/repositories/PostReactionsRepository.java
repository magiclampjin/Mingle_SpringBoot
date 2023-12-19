package com.mingle.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mingle.domain.entites.PostReactions;

public interface PostReactionsRepository extends JpaRepository<PostReactions, Long>{
	
}
