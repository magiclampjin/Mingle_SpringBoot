package com.mingle.domain.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.mingle.domain.entites.PostFile;

import jakarta.transaction.Transactional;

public interface PostFileRepository extends JpaRepository<PostFile, Long>{
	
	@Query("SELECT pf.sysName FROM PostFile pf where pf.postId = :postId" )
	public List<String> selectSysNameListByPostId(Long postId);
	
    // 특정 게시글과 연관된 모든 파일 삭제
    @Transactional
    @Modifying
    @Query("DELETE FROM PostFile pf WHERE pf.postId = :postId")
    public void deleteByPostId(Long postId);

    // 특정 파일 삭제
    @Transactional
    @Modifying
    @Query("DELETE FROM PostFile pf WHERE pf.sysName = :sysName")
    public void deleteFileBySysName(String sysName);

}
