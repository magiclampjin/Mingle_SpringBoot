package com.mingle.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mingle.domain.entites.NewVideo;

public interface NewVideoRepository extends JpaRepository<NewVideo, String>{

}
