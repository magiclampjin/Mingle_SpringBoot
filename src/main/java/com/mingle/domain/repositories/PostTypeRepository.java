package com.mingle.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mingle.domain.entites.PostType;

public interface PostTypeRepository extends JpaRepository<PostType, String>{

}
