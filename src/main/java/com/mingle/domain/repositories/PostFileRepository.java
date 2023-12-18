package com.mingle.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mingle.domain.entites.PostFile;

public interface PostFileRepository extends JpaRepository<PostFile, Long>{

}
