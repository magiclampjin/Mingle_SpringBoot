package com.mingle.domain.repositories;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.mingle.domain.entites.FreePostView;

public interface FreePostViewRepository extends JpaRepository<FreePostView, Long>{
	// 최신 10개의 게시물을 rownum 기준으로 내림차순으로 가져오는 쿼리
    @Query("SELECT fpv FROM FreePostView fpv ORDER BY fpv.rownum DESC")
    List<FreePostView> findTop10ByOrderByRownumDescFreePostView(Pageable pageable);	
    
    @Query("SELECT fpv FROM FreePostView fpv ORDER BY fpv.rownum DESC")
    List<FreePostView> findAllFreePostView();
}
