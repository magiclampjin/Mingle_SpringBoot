package com.mingle.domain.repositories;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.mingle.domain.entites.PopularPostView;

public interface PopularPostViewRepository extends JpaRepository<PopularPostView, Long>{

	// 최신 10개의 게시물을 rownum 기준으로 내림차순으로 가져오는 쿼리
    @Query("SELECT ppv FROM PopularPostView ppv ORDER BY ppv.rownum DESC")
    List<PopularPostView> findTop10ByOrderByRownumDescPopularPostView(Pageable pageable);	
    
    @Query("SELECT ppv FROM PopularPostView ppv ORDER BY ppv.rownum DESC")
    List<PopularPostView> findAllFreePopulartView();
}
